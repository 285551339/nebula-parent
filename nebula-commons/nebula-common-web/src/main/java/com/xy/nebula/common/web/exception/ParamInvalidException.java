package com.xy.nebula.common.web.exception;

import com.xy.nebula.common.domain.vo.resp.ErrorType;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * description: 参数异常
 * date: 2020-08-27 16:20
 * author: chenxd
 * version: 1.0
 */
@Data
@NoArgsConstructor
public class ParamInvalidException extends RuntimeException {

    private String code = ErrorType.ERROR_PARAMS.getCode();

    public ParamInvalidException(String code, String message) {
        super(message);
        this.code = code;
    }

    public ParamInvalidException(String message, Throwable cause, String code) {
        super(message, cause);
        this.code = code;
    }

    public ParamInvalidException(Throwable cause, String code) {
        super(cause);
        this.code = code;
    }

    public ParamInvalidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    public ParamInvalidException(String message) {
        super(message);
    }


    public ParamInvalidException(ErrorType errorType) {
        super(errorType.getMsg());
        this.code = errorType.getCode();
    }
}
