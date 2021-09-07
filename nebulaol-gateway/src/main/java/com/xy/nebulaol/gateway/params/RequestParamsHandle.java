package com.xy.nebulaol.gateway.params;

import com.alibaba.fastjson.JSON;
import com.xy.nebulaol.common.domain.constant.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import java.util.Map;
import java.util.Objects;

/**
 * 请求参数拦截
 */
@Slf4j
public class RequestParamsHandle {

    public static <T extends DataBuffer> T chain(ServerWebExchange delegate, T buffer) {
        ServerHttpRequest request = delegate.getRequest();
        ParamsUtil.BodyDecorator bodyDecorator = ParamsUtil.buildBodyDecorator(buffer);
        Map<String, Object> params = ParamsUtil.buildParams(request, bodyDecorator.getBody());
        delegate.getAttributes().put(CommonConstant.PARAMS, params);
        logIPOther(request, params, delegate);
        return (T) bodyDecorator.getDataBuffer();
    }

    public static void log(ServerWebExchange delegate) {
        ServerHttpRequest request = delegate.getRequest();
        Map<String, Object> params = getAllParams(delegate);
        logIPOther(request, params, delegate);
    }

    private static void logIPOther(ServerHttpRequest request, Map<String, Object> params, ServerWebExchange delegate) {
        //访问ip
        String ip = Objects.requireNonNull(request.getRemoteAddress()).getAddress().getHostAddress();
        String rawPath = request.getURI().getRawPath();
        String appCode = request.getHeaders().getFirst(CommonConstant.APPCODE);
        //请求参数打印
        log.info("request: [ip:{}, url:{}, appCode:{}, params:{}]", ip, rawPath, appCode, JSON.toJSONString(params));
    }


    public static Map<String, Object> getAllParams(ServerWebExchange delegate) {
        return (Map<String, Object>) delegate.getAttributes().get(CommonConstant.PARAMS);
    }

}
