package com.nebula.common.domain.vo.resp;

import com.nebula.common.domain.context.ThreadLocalContext;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * description: BaseResponse
 * date: 2020-09-02 08:15
 * author: chenxd
 * version: 1.0
 */
@Data
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 7691875735974010542L;

    private String code = "00000";
    private String msg = "成功";
    private String traceId;
    private T data;

    public BaseResponse() {

    }

    public BaseResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
        this.traceId = ThreadLocalContext.getTraceId();
    }

    public BaseResponse(ErrorType errorType) {
        this.code = errorType.getCode();
        this.msg = errorType.getMsg();
        this.traceId = ThreadLocalContext.getTraceId();
    }

    public static BaseResponse error() {
        return new BaseResponse(ErrorType.ERROR_SYSTEM);
    }

    public static BaseResponse error(String code, String msg) {
        return new BaseResponse(code, msg);
    }

    public static BaseResponse error(ErrorType errorType) {
        return new BaseResponse(errorType);
    }

    public static <T> BaseResponse<T> ok() {
        return new BaseResponse<>();
    }

    public static <T> BaseResponse<T> ok(T data) {
        BaseResponse<T> r = ok();
        r.setData(data);
        return r;
    }

    public static BaseResponse okList(List<?> list) {
        return ok(new PageResponse(list));
    }

    public static boolean isOk(BaseResponse baseResponse) {
        return baseResponse != null && baseResponse.getCode().equals(ErrorType.SUCCESS_SYSTEM.getCode());
    }

}
