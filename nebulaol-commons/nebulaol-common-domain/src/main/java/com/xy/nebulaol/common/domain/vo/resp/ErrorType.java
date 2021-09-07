package com.xy.nebulaol.common.domain.vo.resp;

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

    SUCCESS_SYSTEM("00000", "成功"),
    ERROR_SYSTEM("99999", "系统异常"),
    ERROR_SIGNATURE_PARAM("90000", "签名参数为空"),
    ERROR_SIGNATURE("90001", "签名错误"),
    ERROR_SIGNATURE_TS("90002", "签名时间错误"),
    ERROR_APPID("90003", "无效的appid"),
    ERROR_INTERNAL_CALL("90005", "内部调用错误"),
    ERROR_SYSTEM_FLOW_LIMITING("90010", "系统限流，请稍后重试"),

    ERROR_BUSINESS("10000", "业务异常"),
    ERROR_NOT_FOUND("10001", "路径不存在，请检查路径是否正确"),
    ERROR_PERMISSION("10002", "权限不足，请联系管理员授权"),
    ERROR_PARAMS("10003", "参数异常"),
    ERROR_CURRENT_LIMITING("10004", "访问过于频繁，请稍后重试"),
    ERROR_DUPLICATEKEY("10005", "记录已存在"),
    ERROR_THEREARECHILDREN("10006", "存在子集"),
    ERROR_DOESNOTEXIST("10007", "记录不存在"),

    ERROR_LOGIN("20000", "用户名或密码错误"),
    ERROR_TOKEN("20001", "无效的token"),
    ERROR_ROLE("20002", "无效的角色"),

    ERROR_THIRD_PUBWIN("30000", "调用pubwin失败"),
    ERROR_THIRD_PAY("30001", "调用支付平台失败"),

    ERROR_COMMON_CHECK_CHINESE("50001","输入无效中文"),
    ERROR_FILE_UPLOAD_FAILED("50002","上传文件失败，请检查配置信息"),

    //考勤管理异常
    ERROR_ATTENDANCE_ROSTER_NOT_FOUND("70100", "排班记录未找到"),
    ERROR_ATTENDANCE_ATTENDANCE_NOT_FOUND("70101", "考勤记录未找到"),
    ERROR_ROSTER_SET_DEL_ROSTER_USED("70102", "排班删除异常，有未来排班在使用中"),
    ERROR_ATTENDANCE_GO_WORK_NOTTIME("70103", "还未到考勤打卡时间"),
    ERROR_ATTENDANCE_OFF_WORK_NOTTIME("70104", "当前已过打下班卡时间"),
    ERROR_ATTENDANCE_SET_RULE_EXIST("70105", "考勤扣罚规则名称重复"),
    ERROR_ATTENDANCE_SET_RULE_ROLE_REPEAT("70106", "考勤扣罚规则角色已经被其他规则使用"),
    ERROR_ROSTER_START_TIME_NOT_NULL("70107", "持久化排班设置中，排班上班打卡时间不能为空"),
    ERROR_ROSTER_END_TIME_NOT_NULL("70108", "持久化排班设置中，排班下班打卡时间不能为空"),
    ERROR_ATTENDANCE_REPEAT_CLOCK("70109", "不能重复打卡"),
    ERROR_ATTENDANCE_GO_WORK_CLOCK_FIRST("70110", "必须先打上班卡"),
    ERROR_ATTENDANCE_SETTING_TIME_REPEAT("70111", "考勤扣罚设置时间重复"),
    ERROR_ROSTER_SETTING_NAME_REPEAT("70112", "排班类型名称不能重复")

    ;


    private final String code;
    private final String msg;

}
