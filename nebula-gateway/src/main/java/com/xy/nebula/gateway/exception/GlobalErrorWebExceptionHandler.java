package com.xy.nebula.gateway.exception;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xy.nebula.common.domain.vo.resp.BaseResponse;
import com.xy.nebula.common.domain.vo.resp.ErrorType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * description: GlobalErrorWebExceptionHandler
 * date: 2020-12-29 19:05
 * author: chenxd
 * version: 1.0
 */
@Slf4j
@Order(-1)
@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GlobalErrorWebExceptionHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable t) {
        ServerHttpResponse response = exchange.getResponse();
        if (response.isCommitted()) {
            return Mono.error(t);
        }

        // 设置返回JSON
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        return response.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory bufferFactory = response.bufferFactory();
            try {
                BaseResponse result = BaseResponse.error(ErrorType.ERROR_SYSTEM.getCode(), t.getMessage());
                if (t instanceof ResponseStatusException) {
                    String msg = ((ResponseStatusException) t).getStatus().getReasonPhrase();
                    result = BaseResponse.error(ErrorType.ERROR_SYSTEM.getCode(), msg);
                }
                log.info("response: [{}]", JSON.toJSONString(result));
                //返回响应结果
                return bufferFactory.wrap(objectMapper.writeValueAsBytes(result));
            } catch (JsonProcessingException e) {
                log.error("Error writing response", e);
                return bufferFactory.wrap(new byte[0]);
            }
        }));
    }
}
