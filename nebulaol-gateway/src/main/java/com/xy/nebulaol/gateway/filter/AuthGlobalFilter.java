package com.xy.nebulaol.gateway.filter;

import com.xy.nebulaol.common.domain.constant.CommonConstant;
import com.xy.nebulaol.common.domain.constant.RedisConstant;
import com.xy.nebulaol.gateway.params.ParamsUtil;
import com.xy.nebulaol.user.dto.resp.UserDetailsRespDto;
import com.xy.sdx.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Objects;

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

    @Autowired
    private RedisService redisService;

    @Autowired
    private TokenStore tokenStore;

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
        if(StringUtils.isNoneBlank(token)){
            OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token);
            OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication(token);
            UserDetailsRespDto detailsRespDto = (UserDetailsRespDto) oAuth2Authentication.getPrincipal();
        }
        boolean iskey = redisService.hasKey(RedisConstant.nebulaol_uaa.ACCESS + token);
        log.info("token 存在 "+ iskey);
        ServerHttpRequest newRequest = exchange.getRequest();
        return chain.filter(exchange.mutate().request(newRequest).build());
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
