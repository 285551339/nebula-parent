package com.xy.nebula.common.domain.constant;

/**
 * description: LogConstant
 * date: 2020-10-08 14:45
 * author: chenxd
 * version: 1.0
 */
public class OperationLogConstant {


    public static final String CONNECTOR = ":";

    public static final String SEPARATOR = ",";
    /**
     * 操作成功
     */
    public final static String OPERATION_RESULT_SUCCESS = "成功";
    /**
     * 操作失败
     */
    public final static String OPERATION_RESULT_FILED = "失败";

    /**
     * 登录
     */
    public final static int OPERATION_TYPE_LOGIN = 0;
    /**
     * 个人中心
     */
    public final static int OPERATION_TYPE_PERSONAL_CENTER = 1;
    /**
     * 用户管理
     */
    public final static int OPERATION_TYPE_USER = 2;
    /**
     * 角色管理
     */
    public final static int OPERATION_TYPE_ROLE = 3;
    /**
     * 账号管理
     */
    public final static int OPERATION_TYPE_ACCOUNT = 4;

    public final static int OPERATION_TYPE_APPLICATION = 999;


}
