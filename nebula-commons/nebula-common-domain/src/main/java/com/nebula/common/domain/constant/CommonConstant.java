package com.nebula.common.domain.constant;

import java.util.HashSet;
import java.util.Set;

/**
 * description: CommonConstant
 * date: 2020-09-03 18:50
 * author: chenxd
 * version: 1.0
 */
public class CommonConstant {

    public final static String TOKEN = "authorization";
    public final static String IP = "ip";
    public final static String SIGN = "sign";
    public final static String ADMIN = "admin";
    public final static String USER = "user";
    public final static String CURRENT_BRAND = "currentBrandId";
    public final static String CURRENT_ROLE = "currentRoleId";
    public final static String APPCODE = "appCode";
    public final static String TRACEID = "traceId";
    public final static String TS = "ts";
    public final static String PARAMS = "params";
    public final static String JSCODE = "jsCode";
    public final static String INIT_BRANCH_NO = "000001";
    /**
     * 账户管理系统
     */
    public final static String APPCODE_XY_MANAGE_UAA = "xy-manage-uaa";

    /**
     * 管理系统appid set
     */
    public final static Set<String> APPCODE_XY_MANAGE_SET = new HashSet<String>(){
        {
            add(APPCODE_XY_MANAGE_UAA);
        }
    };

     /**
     * 微信小程序-酒店
     */
    public final static String APPCODE_XY_HOTEL_APPLET = "xy-hotel-applet";

    /**
     * 消费者端appid set
     */
    public final static Set<String> APPCODE_XY_CONSUMER_SET = new HashSet<String>(){
        {
            add(APPCODE_XY_HOTEL_APPLET);
        }
    };

    public interface DatePatterns {
        String YYYY_MM = "yyyy-MM";

        String yyyyMMdd = "yyyyMMdd";

        String YYYY_MM_DD = "yyyy-MM-dd";

        String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

        String YYYYMMDDHHMM = "yyyyMMddHHmm";

        String yyyyMMddHHmmsss = "yyyyMMddHHmmsss";

        String yyyyMMddHHmmss = "yyyyMMddHHmmss";

        String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

        String HH_MM = "HH:mm";

        String MM_DD = "MM月dd日";
    }
}
