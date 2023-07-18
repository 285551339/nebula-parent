package com.nebula.gateway.filter;

import cn.hutool.core.util.IdUtil;
import com.nebula.common.domain.constant.CommonConstant;
import com.nebula.gateway.params.PayloadServerWebExchangeDecorator;
import com.nebula.gateway.properties.CustomGatewayProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * description: LogFilter
 * date: 2020-09-25 09:16
 * author: chenxd
 * version: 1.0
 */
@Component
@Slf4j
public class LogFilter implements GlobalFilter, Ordered {

    private static final PathMatcher pathMatcher = new AntPathMatcher(System.getProperty("file.separator"));

    @Autowired
    private CustomGatewayProperties gatewayProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        MDC.clear();
        ServerHttpRequest request = exchange.getRequest();
        //访问ip
        String ip = Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getAddress().getHostAddress();
        //traceId
        String traceId = request.getHeaders().getFirst(CommonConstant.TRACEID);
        //例如支付回调，可能不会传此参数
        if (StringUtils.isEmpty(traceId)) {
            traceId = IdUtil.fastSimpleUUID();
        }
        MDC.put(CommonConstant.TRACEID, traceId);
        ServerHttpRequest newRequest = exchange.getRequest().mutate()
                .header(CommonConstant.TRACEID, traceId)
                .header(CommonConstant.IP, ip)
                .build();
        //打印日志
        if (gatewayProperties.getLog().isEnable() && !ignoreLogPath(request.getURI().getPath())) {
            exchange = new PayloadServerWebExchangeDecorator(exchange);
            return chain.filter(exchange.mutate().request(newRequest).build());
        }

        return chain.filter(exchange.mutate().request(newRequest).build());
    }

    /**
     * 判断此url是否忽略打印log
     * @param url
     * @return
     */
    private boolean ignoreLogPath(String url) {
        return Stream.of(gatewayProperties.getLog().getIgnoreUrls()).anyMatch(ignoreUrl -> pathMatcher.match(ignoreUrl, url));
    }


    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }
}
