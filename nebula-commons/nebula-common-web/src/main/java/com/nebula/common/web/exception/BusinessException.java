package com.nebula.common.web.exception;

import com.nebula.common.domain.vo.resp.ErrorType;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * description: 业务异常
 * date: 2020-08-27 16:20
 * author: chenxd
 * version: 1.0
 */
@Data
@NoArgsConstructor
public class BusinessException extends RuntimeException {

    private String code = ErrorType.ERROR_BUSINESS.getCode();

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(String message, Throwable cause, String code) {
        super(message, cause);
        this.code = code;
    }

    public BusinessException(Throwable cause, String code) {
        super(cause);
        this.code = code;
    }

    public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    public BusinessException(String message) {
        super(message);
    }


    public BusinessException(ErrorType errorType) {
        super(errorType.getMsg());
        this.code = errorType.getCode();
    }
}
