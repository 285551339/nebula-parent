package com.xy.nebula.gateway.params;

import com.alibaba.fastjson.JSON;
import com.xy.nebula.common.domain.constant.CommonConstant;
import io.netty.buffer.UnpooledByteBufAllocator;
import lombok.Data;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * @author zangliulu
 * @Title: 请求参数处理util
 * @Package
 * @Description:
 * @date 2021/6/23 18:10
 */
public class ParamsUtil {

    /**
     * 请求头类型 MediaType
     */
    public static final Set<MediaType> CHAIN_MEDIA_TYPE = new HashSet<MediaType>(){
            {
                add(MediaType.TEXT_XML);
                add(MediaType.APPLICATION_XML);
                add(MediaType.APPLICATION_JSON);
                add(MediaType.APPLICATION_JSON_UTF8);
                add(MediaType.TEXT_PLAIN);
                add(MediaType.TEXT_XML);
                add(MediaType.APPLICATION_FORM_URLENCODED);
            }
    };

    /**
     * 请求METHOD_TYPE类型名称
     */
    public static final Set<String> METHOD_TYPE = new HashSet<String>(){
        {
            add(HttpMethod.POST.name());
            add(HttpMethod.PUT.name());
        }
    };

    public static <T extends DataBuffer> BodyDecorator buildBodyDecorator(T buffer) {
        try {
            InputStream dataBuffer = buffer.asInputStream();
            byte[] bytes = IOUtils.toByteArray(dataBuffer);
            NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(new UnpooledByteBufAllocator(false));
            DataBufferUtils.release(buffer);
            BodyDecorator bodyDecorator = new BodyDecorator(new String(bytes), nettyDataBufferFactory.wrap(bytes));
            return bodyDecorator;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Data
    static class BodyDecorator {

        String body;

        DataBuffer dataBuffer;

        public BodyDecorator(String body, DataBuffer dataBuffer) {
            this.body = body;
            this.dataBuffer = dataBuffer;
        }

    }

    /**
     * 构建请求参数
     * @param request
     * @param body
     * @return
     */
    public static Map<String, Object> buildParams(ServerHttpRequest request, String body) {
        // 整理参数
        Map<String, Object> params = new HashMap<>();
        if (request.getQueryParams() != null) {
            params.putAll(request.getQueryParams().toSingleValueMap());
        }
        if (StringUtils.isNotBlank(body)) {
            params.putAll(JSON.parseObject(body));
        }
        return params;
    }

    /**
     * 获取请求参数
     * @param exchange
     * @return
     */
    public static Map<String, Object> getAllParams(ServerWebExchange exchange) {
        return (Map<String, Object>) exchange.getAttributes().get(CommonConstant.PARAMS);
    }

//    public static boolean logBody(ServerWebExchange delegate) {
//        return ParamsUtil.CHAIN_MEDIA_TYPE.contains(delegate.getRequest().getHeaders().getContentType());
//    }

    public static boolean logBody(ServerWebExchange delegate) {
        return ParamsUtil.METHOD_TYPE.contains(delegate.getRequest().getMethod().name());
    }

}
