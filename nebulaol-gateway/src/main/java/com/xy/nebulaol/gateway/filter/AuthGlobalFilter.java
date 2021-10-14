package com.xy.nebulaol.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.xy.nebulao.commons.utils.SignUtil;
import com.xy.nebulaol.common.domain.constant.CommonConstant;
import com.xy.nebulaol.common.domain.constant.RedisConstant;
import com.xy.nebulaol.common.domain.vo.req.BaseAdminReq;
import com.xy.nebulaol.common.domain.vo.req.BaseUserReq;
import com.xy.nebulaol.common.domain.vo.resp.BaseResponse;
import com.xy.nebulaol.common.domain.vo.resp.ErrorType;
import com.xy.nebulaol.gateway.params.ParamsUtil;
import com.xy.nebulaol.gateway.properties.CustomGatewayProperties;
import com.xy.nebulaol.user.dto.resp.UserDetailsRespDto;
import com.xy.sdx.redis.service.RedisService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author zangliulu
 * @Title: 权限验证
 * @Package
 * @Description:
 * @date 2021/6/23 18:10
 */
@Component
@Slf4j
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    private static final PathMatcher pathMatcher = new AntPathMatcher(System.getProperty("file.separator"));

    @Autowired
    private CustomGatewayProperties gatewayProperties;

    @Autowired
    private RedisService redisService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String method = request.getMethodValue();
        Map<String, Object> params = ParamsUtil.getAllParams(exchange);
        //访问ip
        String ip = Objects.requireNonNull(request.getRemoteAddress()).getAddress().getHostAddress();
        String rawPath = request.getURI().getRawPath();
        String appCode = request.getHeaders().getFirst(CommonConstant.APPCODE);
        String traceId = request.getHeaders().getFirst(CommonConstant.TRACEID);
        String ts = request.getHeaders().getFirst(CommonConstant.TS);
        String sign = request.getHeaders().getFirst(CommonConstant.SIGN);
        //token
        String token = request.getHeaders().getFirst(CommonConstant.TOKEN);
        //验证签名
        if (gatewayProperties.getSign().isEnable() && !ignoreSignPath(request.getURI().getPath())) {
            //签名参数不完整
            if (StringUtils.isEmpty(appCode) || StringUtils.isEmpty(traceId) || StringUtils.isEmpty(ts) || StringUtils.isEmpty(sign)) {
                log.error("签名参数为空: ip:{}, url:{}, appCode:{}, ts:{}, sign:{}", ip, rawPath, appCode, ts, sign);
                return responseError(exchange, BaseResponse.error(ErrorType.ERROR_SIGNATURE_PARAM));
            }
            //时间差
            if (Math.abs(System.currentTimeMillis() - Long.parseLong(ts)) > gatewayProperties.getSign().getTimeout()) {
                log.error("签名时间戳错误: ip:{}, url:{}, appCode:{}, ts:{}, sign:{}", ip, rawPath, appCode, ts, sign);
                return responseError(exchange, BaseResponse.error(ErrorType.ERROR_SIGNATURE_TS));
            }
            params.put(CommonConstant.APPCODE, appCode);
            params.put(CommonConstant.TOKEN, token);
            //params.put(CommonConstant.CURRENT_ROLE, currentRole);
            //params.put(CommonConstant.CURRENT_BRAND, currentBrand);
            params.put(CommonConstant.TRACEID, traceId);
            params.put(CommonConstant.TS, ts);
            //校验sign
            String ourSign = SignUtil.buildSign(params, gatewayProperties.getSign().getKey().get(appCode));
            if (!sign.equals(ourSign)) {
                log.error("签名错误: ip:{}, url:{}, appCode:{}, ts:{}, sign:{}, ourSign:{}", ip, rawPath, appCode, ts, sign, ourSign);
                return responseError(exchange, BaseResponse.error(ErrorType.ERROR_SIGNATURE));
            }
        }
        boolean isConsumer = CommonConstant.APPCODE_SDX_CONSUMER_SET.contains(appCode);
        boolean isManage = CommonConstant.APPCODE_SDX_MANAGE_SET.contains(appCode);
        BaseUserReq baseUserReq = null;
        ServerHttpRequest newRequest = exchange.getRequest();
        //验证权限
        if (gatewayProperties.getAuth().isEnable() && !ignoreAuthPath(request.getURI().getPath())) {
            //无效token
            if (StringUtils.isEmpty(token)) {
                log.error("token为空: ip:{}, url:{}, appCode:{}, traceId:{}, ts:{}", ip, rawPath, appCode, traceId, ts);
                return responseError(exchange, BaseResponse.error(ErrorType.ERROR_TOKEN));
            }
            if (isConsumer) {
                baseUserReq = getUser(appCode, token);
                if (baseUserReq == null) {
                    log.error("无效的token: ip:{}, url:{}, appCode:{}, token:{}", ip, rawPath, appCode, token);
                    return responseError(exchange, BaseResponse.error(ErrorType.ERROR_TOKEN));
                }
                //权限不足(管理系统验证接口权限，消费者端暂时不验证)
            } else if (isManage) {
//                Route route = (Route) exchange.getAttributes().get(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
//                //路由ID和appid不符
//                if (!route.getId().equals(appCode)) {
//                    log.error("路由ID:{}与appCode{}不符", route.getId(), appCode);
//                    return responseError(exchange, BaseResponse.error(ErrorType.ERROR_PERMISSION));
//                }
                /*baseAdminReq = getAdmin(token);
                if (baseAdminReq == null) {
                    log.error("权限不足: ip:{}, url:{}, appCode:{}, token:{}", ip, rawPath, appCode, token);
                    return responseError(exchange, BaseResponse.error(ErrorType.ERROR_TOKEN));
                }*/
            }
        }
        return chain.filter(exchange.mutate().request(newRequest).build());
    }

    /**
     * 根据path和method组装resourceCode
     *
     * @param path
     * @param method
     * @return
     */
    private String createResourceCode(String path, String method) {
        path = path.substring(1).replaceAll("/", RedisConstant.CONNECTOR);
        return path + RedisConstant.CONNECTOR + method.toLowerCase();
    }


    /**
     * 判断此url是否验证签名
     *
     * @param url
     * @return
     */
    private boolean ignoreSignPath(String url) {
        return Stream.of(gatewayProperties.getSign().getIgnoreUrls()).anyMatch(ignoreUrl -> pathMatcher.match(ignoreUrl, url));
    }

    /**
     * 判断此url是否验证权限
     *
     * @param url
     * @return
     */
    private boolean ignoreAuthPath(String url) {
        return Stream.of(gatewayProperties.getAuth().getIgnoreUrls()).anyMatch(ignoreUrl -> pathMatcher.match(ignoreUrl, url));
    }

    /**
     * 判断改平台此接口是否验证权限
     * @param appCode
     * @param code
     * @return
     */
   /* private boolean checkAppPermission(String appCode, String code) {
        return redisService.isMember(RedisConstant.SDX_UAA.APP_RESOUCE + appCode, code);
    }



    private BaseAdminReq getAdmin(String token) {
        Object admin = redisService.get(RedisConstant.SDX_UAA.TOKEN_ADMIN + token);
        return admin == null ? null : (BaseAdminReq) admin;
    }*/

    /**
     * 酒店小程序、网咖公众号、网咖云桌面
     * @param appCode
     * @param token
     * @return
     */
    private BaseUserReq getUser(String appCode, String token) {
        String tokenKey = null;
        switch (appCode) {
            case CommonConstant.APPCODE_XY_HOTEL_APPLET:
                tokenKey = RedisConstant.XY_APPLET_HOTEL.TOKEN_USER_HOTEL;
                break;
        }
        Object user = redisService.get(tokenKey + token);
        return user == null ? null : (BaseUserReq)user;
    }

    /**
     * 自定义返回
     *
     * @param exchange
     * @param baseResponse
     * @return
     */
    private Mono<Void> responseError(ServerWebExchange exchange, BaseResponse baseResponse) {
        ServerHttpResponse serverHttpResponse = exchange.getResponse();
        serverHttpResponse.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        byte[] bytes = JSON.toJSONString(baseResponse).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = serverHttpResponse.bufferFactory().wrap(bytes);
        return serverHttpResponse.writeWith(Flux.just(buffer));
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 2;
    }

    @Data
    public static class UserToken {
        private BaseUserReq user;

    }

}
