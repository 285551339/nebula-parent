package com.xy.nebula.gateway.params;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebExchangeDecorator;

public class PayloadServerWebExchangeDecorator extends ServerWebExchangeDecorator {

    private final PartnerServerHttpRequestDecorator requestDecorator;

    private final PartnerServerHttpResponseDecorator responseDecorator;

    public PayloadServerWebExchangeDecorator(ServerWebExchange delegate) {
        super(delegate);
        this.requestDecorator = new PartnerServerHttpRequestDecorator(delegate);
        this.responseDecorator = new PartnerServerHttpResponseDecorator(delegate);
    }

    @Override
    public ServerHttpRequest getRequest() {
        return requestDecorator;
    }

    @Override
    public ServerHttpResponse getResponse() {
        return responseDecorator;
    }

}
