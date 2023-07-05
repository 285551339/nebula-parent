package com.xy.nebula.common.web.handler;


import com.xy.nebula.common.web.exception.BusinessException;
import com.xy.nebula.common.web.exception.ParamInvalidException;
import com.xy.nebula.common.domain.vo.resp.BaseResponse;
import com.xy.nebula.common.domain.vo.resp.ErrorType;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * description: BaseResponse
 * date: 2020-09-02 08:15
 * author: chenxd
 * version: 1.0
 */
@RestControllerAdvice
@Order
@Slf4j
public class GlobalExceptionHandler {

    private static final String COMMA = ",";

    @ExceptionHandler(IllegalArgumentException.class)
    public BaseResponse badArgumentHandler(IllegalArgumentException e) {
        log.error(e.getMessage(), e);
        return BaseResponse.error(ErrorType.ERROR_PARAMS);
    }

    @ExceptionHandler(BindException.class)
    public BaseResponse badArgumentHandler(BindException e) {
        log.error(e.getMessage(), e);
        StringBuilder sb = new StringBuilder();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            sb.append(error.getDefaultMessage()).append(COMMA);
        }
        String msg = sb.substring(0, sb.lastIndexOf(COMMA));
        return BaseResponse.error(ErrorType.ERROR_PARAMS.getCode(), msg);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public BaseResponse badArgumentHandler(MethodArgumentTypeMismatchException e) {
        log.error(e.getMessage(), e);
        return BaseResponse.error(ErrorType.ERROR_PARAMS);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public BaseResponse badArgumentHandler(MissingServletRequestParameterException e) {
        log.error(e.getMessage(), e);
        return BaseResponse.error(ErrorType.ERROR_PARAMS);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public BaseResponse badArgumentHandler(HttpMessageNotReadableException e) {
        log.error(e.getMessage(), e);
        return BaseResponse.error(ErrorType.ERROR_PARAMS);
    }

    @ExceptionHandler(ValidationException.class)
    public BaseResponse badArgumentHandler(ValidationException e) {
        log.error(e.getMessage(), e);
        if (e instanceof ConstraintViolationException) {
            ConstraintViolationException exs = (ConstraintViolationException) e;
            Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
            for (ConstraintViolation<?> item : violations) {
                String message = ((PathImpl) item.getPropertyPath()).getLeafNode().getName() + item.getMessage();
                return BaseResponse.error(ErrorType.ERROR_PARAMS.getCode(), message);
            }
        }
        return BaseResponse.error(ErrorType.ERROR_PARAMS);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse badArgumentHandler(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        String message = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(COMMA));
        return BaseResponse.error(ErrorType.ERROR_PARAMS.getCode(), message);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public BaseResponse duplicateKeyHandler(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        return BaseResponse.error(ErrorType.ERROR_DUPLICATEKEY);
    }

    @ExceptionHandler(BusinessException.class)
    public BaseResponse businessHandler(BusinessException e) {
        log.error(e.getMessage(), e);
        return BaseResponse.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(ParamInvalidException.class)
    public BaseResponse paramInvalidHandler(ParamInvalidException e) {
        log.error(e.getMessage(), e);
        return BaseResponse.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(FeignException.class)
    public BaseResponse feignHandler(FeignException e) {
        log.error(e.getMessage(), e);
        return BaseResponse.error(ErrorType.ERROR_INTERNAL_CALL.getCode(), ErrorType.ERROR_INTERNAL_CALL.getMsg().concat(": ").concat(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public BaseResponse seriousHandler(Exception e) {
        log.error(e.getMessage(), e);
        return BaseResponse.error();
    }

}
