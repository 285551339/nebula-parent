package com.xy.nebulaol.common.domain.constant;

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
    public final static String APPCODE_SDX_MANAGE_UAA = "sdx-manage-uaa";
    /**
     * 运维管理系统
     */
    public final static String APPCODE_SDX_MANAGE_OPS = "sdx-manage-ops";
    /**
     * 人事管理系统
     */
    public final static String APPCODE_SDX_MANAGE_PMS = "sdx-manage-pms";
    /**
     * 酒店管理系统
     */
    public final static String APPCODE_SDX_MANAGE_HMS = "sdx-manage-hms";
    /**
     * BI数据报表系统
     */
    public final static String APPCODE_SDX_MANAGE_BI = "sdx-manage-bi";
    /**
     * 收银系统
     */
    public final static String APPCODE_SDX_MANAGE_CASHIER = "sdx-manage-cashier";
    /**
     * 清算系统
     */
    public final static String APPCODE_SDX_MANAGE_LIQUIDATION = "sdx-manage-liquidation";
    /**
     * 运营管理系统
     */
    public final static String APPCODE_SDX_MANAGE_OMS = "sdx-manage-oms";
    /**
     * 采购系统
     */
    public final static String APPCODE_SDX_MANAGE_PURCHASE = "sdx-manage-purchase";
    /**
     * 供应商系统
     */
    public final static String APPCODE_SDX_MANAGE_SUPPLIER = "sdx-manage-supplier";

    /**
     * 管理系统appid set
     */
    public final static Set<String> APPCODE_SDX_MANAGE_SET = new HashSet<String>(){
        {
            add(APPCODE_SDX_MANAGE_UAA);
            add(APPCODE_SDX_MANAGE_OPS);
            add(APPCODE_SDX_MANAGE_PMS);
            add(APPCODE_SDX_MANAGE_HMS);
            add(APPCODE_SDX_MANAGE_BI);
            add(APPCODE_SDX_MANAGE_CASHIER);
            add(APPCODE_SDX_MANAGE_LIQUIDATION);
            add(APPCODE_SDX_MANAGE_OMS);
            add(APPCODE_SDX_MANAGE_PURCHASE);
            add(APPCODE_SDX_MANAGE_SUPPLIER);
        }
    };

    /**
     * 控制台
     */
    public final static String APPCODE_SDX_CONSOLE = "sdx-console";
    /**
     * 云桌面
     */
    public final static String APPCODE_SDX_CLOUDESK = "sdx-cloudesk";
    /**
     * 微信公众号-网咖
     */
    public final static String APPCODE_SDX_NETCAFE_WOA = "sdx-netcafe-woa";
     /**
     * 微信小程序-酒店
     */
    public final static String APPCODE_SDX_HOTEL_APPLET = "sdx-hotel-applet";

    /**
     * 消费者端appid set
     */
    public final static Set<String> APPCODE_SDX_CONSUMER_SET = new HashSet<String>(){
        {
            add(APPCODE_SDX_CONSOLE);
            add(APPCODE_SDX_CLOUDESK);
            add(APPCODE_SDX_NETCAFE_WOA);
            add(APPCODE_SDX_HOTEL_APPLET);
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


    /**
     * 京东支付类型  //JDPAY(京东支付), WX(微信支付), JIOU(京东白条), UNIPAY(银联在线支付), ALIPAY(支付宝支付)
     */
    public interface JdPayWayType {
        String JDPAY = "JDPAY";
        String WX = "WX";
        String ALIPAY = "ALIPAY";
        String JIOU = "JIOU";
        String UNIPAY = "UNIPAY";
    }

    /**
     * 京东支付类型  //JDPAY(京东支付), WECHAT(微信支付), JIOU(京东白条), UNIPAY(银联在线支付), ALIPAY(支付宝支付)
     */
    public interface PayWayType {
        String JDPAY = "JDPAY";
        String WECHAT = "WECHAT";
        String ALIPAY = "ALIPAY";
        String JIOU = "JIOU";
        String UNIPAY = "UNIPAY";
    }
}
