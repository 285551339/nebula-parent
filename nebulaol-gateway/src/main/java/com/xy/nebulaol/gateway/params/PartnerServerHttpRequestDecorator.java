package com.xy.nebulaol.gateway.params;

import com.alibaba.fastjson.JSON;
import com.xy.nebulaol.common.domain.constant.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.web.server.ServerWebExchange;

import java.util.Map;
import java.util.Objects;

@Slf4j
public class PartnerServerHttpRequestDecorator extends ServerHttpRequestDecorator{

    public PartnerServerHttpRequestDecorator(ServerWebExchange delegate) {
        super(delegate.getRequest());
        if (ParamsUtil.logBody(delegate)) {
            RequestParamsHandle.log(delegate);
        } else {
            Map<String, Object> params = ParamsUtil.buildParams(delegate.getRequest(), null);
            delegate.getAttributes().put(CommonConstant.PARAMS, params);
            //访问ip
            String ip = Objects.requireNonNull(delegate.getRequest().getRemoteAddress()).getAddress().getHostAddress();
            String rawPath = delegate.getRequest().getURI().getRawPath();
            String appCode = delegate.getRequest().getHeaders().getFirst(CommonConstant.APPCODE);
            // 请求参数打印
            log.info("request: [ip:{}, url:{}, appCode:{}, params:{}]", ip, rawPath, appCode, JSON.toJSONString(params));
        }
    }

}
