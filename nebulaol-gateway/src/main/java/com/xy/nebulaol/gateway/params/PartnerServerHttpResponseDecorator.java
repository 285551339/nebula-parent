package com.xy.nebulaol.gateway.params;

import com.xy.nebulaol.common.domain.constant.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.slf4j.MDC;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static reactor.core.scheduler.Schedulers.single;

@Slf4j
public class PartnerServerHttpResponseDecorator extends ServerHttpResponseDecorator {

    private ServerWebExchange exchange;

    PartnerServerHttpResponseDecorator(ServerWebExchange delegate) {
        super(delegate.getResponse());
        this.exchange = delegate;
    }

    @Override
    public Mono<Void> writeAndFlushWith(Publisher<? extends Publisher<? extends DataBuffer>> body) {
        return super.writeAndFlushWith(body);
    }

    @Override
    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
//        final MediaType contentType = super.getHeaders().getContentType();
//        if (ParamsUtil.CHAIN_MEDIA_TYPE.contains(contentType)) {
//            if (body instanceof Mono) {
//                final Mono<DataBuffer> monoBody = (Mono<DataBuffer>) body;
//                return super.writeWith(monoBody.publishOn(single()).map(dataBuffer -> ResponseParamsHandle.chain(dataBuffer)));
//            } else if (body instanceof Flux) {
//                Mono<DataBuffer> mono = DataBufferUtils.join(body);
//                final Flux<DataBuffer> monoBody = mono.publishOn(single()).map(dataBuffer -> ResponseParamsHandle.chain(dataBuffer)).flux();
//                return super.writeWith(monoBody);
//            }
//        }
        if (body instanceof Mono) {
            final Mono<DataBuffer> monoBody = (Mono<DataBuffer>) body;
            return super.writeWith(monoBody.publishOn(single()).map(dataBuffer -> {
                MDC.put(CommonConstant.TRACEID, exchange.getRequest().getHeaders().getFirst(CommonConstant.TRACEID));
                try {
                    return ResponseParamsHandle.chain(dataBuffer);
                } finally {
                    MDC.remove(CommonConstant.TRACEID);
                }
            }));
        } else if (body instanceof Flux) {
            Mono<DataBuffer> mono = DataBufferUtils.join(body);
            final Flux<DataBuffer> monoBody = mono.publishOn(single()).map(dataBuffer -> {
                MDC.put(CommonConstant.TRACEID, exchange.getRequest().getHeaders().getFirst(CommonConstant.TRACEID));
                try {
                    return ResponseParamsHandle.chain(dataBuffer);
                } finally {
                    MDC.remove(CommonConstant.TRACEID);
                }
            }).flux();
            return super.writeWith(monoBody);
        }
        return super.writeWith(body);
    }

}
