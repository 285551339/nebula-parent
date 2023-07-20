package com.nebula.gateway.filter;

import com.nebula.common.domain.constant.CommonConstant;
import com.nebula.gateway.properties.CustomGatewayProperties;
import com.nebula.gateway.params.ParamsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Stream;

@Component
@Slf4j
public class CacheBodyParamsFilter implements GlobalFilter, Ordered {

    private static final PathMatcher PATH_MATCHER = new AntPathMatcher(System.getProperty("file.separator"));
    @Autowired
    private CustomGatewayProperties gatewayProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (gatewayProperties.getLog().isEnable() && !ignoreLogPath(exchange.getRequest().getURI().getPath()) && ParamsUtil.logBody(exchange)) {
            return DataBufferUtils.join(exchange.getRequest().getBody())
                    .flatMap(dataBuffer -> {
                        byte[] bytes = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(bytes);
                        DataBufferUtils.release(dataBuffer);
                        Flux<DataBuffer> cachedFlux = Flux.defer(() -> {
                            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
                            DataBufferUtils.retain(buffer);
                            return Mono.just(buffer);
                        });
                        ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
                            @Override
                            public Flux<DataBuffer> getBody() {
                                return cachedFlux;
                            }
                        };
                        ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();
                        return ServerRequest.create(mutatedExchange, HandlerStrategies.withDefaults().messageReaders())
                                .bodyToMono(String.class)
                                .doOnNext(objectValue -> {
                                    mutatedExchange.getAttributes().put(CommonConstant.PARAMS, ParamsUtil.buildParams(mutatedRequest, objectValue));
                                }).then(chain.filter(mutatedExchange));
                    });
        }
        return chain.filter(exchange);
    }

    private boolean ignoreLogPath(String url) {
        return Stream.of(gatewayProperties.getLog().getIgnoreUrls()).anyMatch(ignoreUrl -> PATH_MATCHER.match(ignoreUrl, url));
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
