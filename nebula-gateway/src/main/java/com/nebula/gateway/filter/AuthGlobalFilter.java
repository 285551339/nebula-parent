package com.nebula.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.nebula.gateway.properties.CustomGatewayProperties;
import com.nebula.redis.service.RedisService;
import com.nebula.commons.utils.SignUtil;
import com.nebula.common.domain.constant.CommonConstant;
import com.nebula.common.domain.constant.RedisConstant;
import com.nebula.common.domain.vo.req.BaseUserReq;
import com.nebula.common.domain.vo.resp.BaseResponse;
import com.nebula.common.domain.vo.resp.ErrorType;
import com.nebula.gateway.params.ParamsUtil;

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
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
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

    private static final PathMatcher PATH_MATCHER = new AntPathMatcher(System.getProperty("file.separator"));

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
            params.put(CommonConstant.TRACEID, traceId);
            params.put(CommonConstant.TS, ts);
            //校验sign
            String ourSign = SignUtil.buildSign(params, gatewayProperties.getSign().getKey().get(appCode));
            if (!sign.equals(ourSign)) {
                log.error("签名错误: ip:{}, url:{}, appCode:{}, ts:{}, sign:{}, ourSign:{}", ip, rawPath, appCode, ts, sign, ourSign);
                return responseError(exchange, BaseResponse.error(ErrorType.ERROR_SIGNATURE));
            }
        }
        BaseUserReq baseUserReq = null;
        ServerHttpRequest newRequest = exchange.getRequest();
        //验证权限
        if (gatewayProperties.getAuth().isEnable() && !ignoreAuthPath(request.getURI().getPath())) {
            //无效token
            if (StringUtils.isEmpty(token)) {
                log.error("token为空: ip:{}, url:{}, appCode:{}, traceId:{}, ts:{}", ip, rawPath, appCode, traceId, ts);
                return responseError(exchange, BaseResponse.error(ErrorType.ERROR_TOKEN));
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
        return Stream.of(gatewayProperties.getSign().getIgnoreUrls()).anyMatch(ignoreUrl -> PATH_MATCHER.match(ignoreUrl, url));
    }

    /**
     * 判断此url是否验证权限
     *
     * @param url
     * @return
     */
    private boolean ignoreAuthPath(String url) {
        return Stream.of(gatewayProperties.getAuth().getIgnoreUrls()).anyMatch(ignoreUrl -> PATH_MATCHER.match(ignoreUrl, url));
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
