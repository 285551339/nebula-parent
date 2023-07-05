package com.xy.nebula.common.web.config;

import com.alibaba.fastjson.JSONObject;
import com.xy.nebula.common.web.exception.BusinessException;
import com.xy.nebula.common.domain.vo.resp.BaseResponse;
import com.xy.nebula.common.domain.vo.resp.ErrorType;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.Decoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * description: FeignResponseDecoder
 * date: 2020-09-16 17:18
 * author: chenxd
 * version: 1.0
 */
@Slf4j
public class FeignResponseDecoder implements Decoder {

    final Decoder delegate;

    public FeignResponseDecoder(Decoder delegate) {
        Objects.requireNonNull(delegate, "Decoder must not be null. ");
        this.delegate = delegate;
    }

    @Override
    public Object decode(Response response, Type type) throws IOException, FeignException {
        String result = Util.toString(response.body().asReader(Util.UTF_8));
        BaseResponse baseResponse = JSONObject.parseObject(result, BaseResponse.class);
        // 如果返回码不是成功，抛出异常
        if (baseResponse != null && !ErrorType.SUCCESS_SYSTEM.getCode().equals(baseResponse.getCode())) {
            log.error("内部调用失败, 返回:{}", result);
            throw new BusinessException(ErrorType.ERROR_INTERNAL_CALL.getCode(), ErrorType.ERROR_INTERNAL_CALL.getMsg().concat("：").concat(baseResponse.getMsg()));
        }
        return delegate.decode(response.toBuilder().body(result, Util.UTF_8).build(), type);
    }
}
