package com.xy.nebula.common.domain.vo.resp;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * description: ErrorType
 * date: 2020-08-26 16:42
 * author: chenxd
 * version: 1.0
 */
@Getter
@AllArgsConstructor
public enum ErrorType {
    //9开头为系统异常
    SUCCESS_SYSTEM("00000", "成功"),
    ERROR_SYSTEM("99999", "系统异常"),
    ERROR_SIGNATURE_PARAM("90000", "签名参数为空"),
    ERROR_SIGNATURE("90001", "签名错误"),
    ERROR_SIGNATURE_TS("90002", "签名时间错误"),
    ERROR_APPID("90003", "无效的appid"),
    ERROR_INTERNAL_CALL("90005", "内部调用错误"),
    ERROR_SYSTEM_FLOW_LIMITING("90010", "系统限流，请稍后重试"),
    //91常用普通业务异常
    ERROR_BUSINESS("91000", "业务异常"),
    ERROR_NOT_FOUND("91001", "路径不存在，请检查路径是否正确"),
    ERROR_PERMISSION("91002", "权限不足，请联系管理员授权"),
    ERROR_PARAMS("91003", "参数异常"),
    ERROR_CURRENT_LIMITING("91004", "访问过于频繁，请稍后重试"),
    ERROR_DUPLICATEKEY("91005", "记录已存在"),
    ERROR_THEREARECHILDREN("91006", "存在子集"),
    ERROR_DOESNOTEXIST("91007", "记录不存在"),
    //10用户操作相关异常
    ERROR_LOGIN("10000", "用户名或密码错误"),
    ERROR_TOKEN("10001", "无效的token"),
    ERROR_ROLE("10002", "无效的角色"),

    ERROR_THIRD_PAY("30001", "调用支付平台失败"),

    ERROR_COMMON_CHECK_CHINESE("50001","输入无效中文"),
    ERROR_FILE_UPLOAD_FAILED("50002","上传文件失败，请检查配置信息")
    ;


    private final String code;
    private final String msg;

}
