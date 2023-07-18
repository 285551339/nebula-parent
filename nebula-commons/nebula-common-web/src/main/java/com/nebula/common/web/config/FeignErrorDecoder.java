package com.nebula.common.web.config;


import com.nebula.common.web.exception.BusinessException;
import com.nebula.common.domain.vo.resp.ErrorType;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

/**
 * description: FeignErrorDecoderConfig
 * date: 2020-09-15 22:36
 * author: chenxd
 * version: 1.0
 */
@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() != HttpStatus.OK.value()) {
            try {
                String message = Util.toString(response.body().asReader(Util.UTF_8));
                log.error("内部调用:{}失败, 原因:{}", methodKey, message);
                return new BusinessException(ErrorType.ERROR_INTERNAL_CALL);
            } catch (Exception e) {
                log.error("feign decode error", e);
            }
        }
        return errorDecoder.decode(methodKey, response);
    }
}
