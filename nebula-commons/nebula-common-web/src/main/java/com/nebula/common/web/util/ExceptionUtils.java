package com.nebula.common.web.util;

import com.nebula.common.web.exception.BusinessException;
import com.nebula.common.domain.vo.resp.ErrorType;

/**
 * @author zhengdd
 * @createTime 2021-05-11 20:12
 */
public class ExceptionUtils {

    /**
     * 封装检查异常，异常时抛出异常
     *
     * @param errorFlag
     *                  false-异常，抛出异常
     *                  true-正常，不抛出异常
     *                  null-异常，抛出 {@link ErrorType#ERROR_BUSINESS}
     * @param errorType
     */
    public static void checkThrowBusinessException(Boolean errorFlag, ErrorType errorType) {
        checkThrowBusinessException(errorFlag, errorType.getCode(), errorType.getMsg());
    }

    /**
     * 封装检查判断
     * @param errorFlag
     *              false-异常，抛出异常
     *              true-正常，不抛出异常
     *              null-异常，抛出 {@link ErrorType#ERROR_BUSINESS}
     * @param code
     *              异常code
     * @param msg
     *              异常信息
     */
    public static void checkThrowBusinessException(Boolean errorFlag, String code, String msg) {
        if (errorFlag == null) {
            throw new BusinessException(ErrorType.ERROR_BUSINESS);
        }

        if (!errorFlag) {
            throw new BusinessException(code, msg);
        }
    }

    /**
     * 封装检查判断
     * @param errorFlag
     *              false-异常，抛出异常
     *              true-正常，不抛出异常
     *              null-异常，抛出 {@link ErrorType#ERROR_BUSINESS}
     * @param msg
     *              异常信息
     */
    public static void checkThrowBusinessException(Boolean errorFlag, String msg) {
        if (errorFlag == null) {
            throw new BusinessException(ErrorType.ERROR_BUSINESS);
        }

        if (!errorFlag) {
            throw new BusinessException(msg);
        }
    }
}
