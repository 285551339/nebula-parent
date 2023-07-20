package com.nebula.common.domain.context;


import com.nebula.common.domain.constant.CommonConstant;
import com.nebula.common.domain.vo.req.BaseAdminReq;
import com.nebula.common.domain.vo.req.BaseUserReq;

import java.util.HashMap;
import java.util.Map;

/**
 * description: ThreadLocalContext
 * date: 2020-09-12 14:25
 * author: chenxd
 * version: 1.0
 */
public class ThreadLocalContext {

    /**
     * 使用InheritableThreadLocal便于在主子线程间传递参数
     */
    private static final ThreadLocal<Map<String, Object>> CONTEXT = new InheritableThreadLocal<>();

    public static void set(String key, Object value) {
        Map<String, Object> map = CONTEXT.get();
        if (map == null) {
            map = new HashMap<>(32);
        }
        map.put(key, value);
        CONTEXT.set(map);
    }

    public static void remove() {
        CONTEXT.remove();
    }

    public static String getTraceId() {
        Map<String, Object> map = CONTEXT.get();
        Object traceIdObj = map == null ? null : map.get(CommonConstant.TRACEID);
        return traceIdObj == null ? null : traceIdObj.toString();
    }

    public static void setTraceId(String traceId) {
        set(CommonConstant.TRACEID, traceId);
    }

    public static String getAppCode() {
        Map<String, Object> map = CONTEXT.get();
        Object appCode = map == null ? null : map.get(CommonConstant.APPCODE);
        return appCode == null ? null : appCode.toString();
    }

    public static void setAppCode(String appCode) {
        set(CommonConstant.APPCODE, appCode);
    }

    public static BaseAdminReq getAdmin() {
        Map<String, Object> map = CONTEXT.get();
        Object adminObj = map == null ? null : map.get(CommonConstant.ADMIN);
        return adminObj == null ? null : (BaseAdminReq) adminObj;
    }

    public static void setAdmin(BaseAdminReq admin) {
        set(CommonConstant.ADMIN, admin);
    }

    public static BaseUserReq getUser() {
        Map<String, Object> map = CONTEXT.get();
        Object userObj = map == null ? null : map.get(CommonConstant.USER);
        return userObj == null ? null : (BaseUserReq) userObj;
    }

    public static void setUser(BaseUserReq user) {
        set(CommonConstant.USER, user);
    }

    public static String getIp() {
        Map<String, Object> map = CONTEXT.get();
        Object ipObj = map == null ? null : map.get(CommonConstant.IP);
        return ipObj == null ? null : ipObj.toString();
    }

    public static void setIp(String ip) {
        set(CommonConstant.IP, ip);
    }

    public static Long getCurrentBrand() {
        Map<String, Object> map = CONTEXT.get();
        Object brandObj = map == null ? null : map.get(CommonConstant.CURRENT_BRAND);
        return brandObj == null ? null : Long.valueOf(brandObj.toString());
    }

    public static void setCurrentBrand(Long brand) {
        set(CommonConstant.CURRENT_BRAND, brand);
    }

    public static Long getCurrentRole() {
        Map<String, Object> map = CONTEXT.get();
        Object roleObj = map == null ? null : map.get(CommonConstant.CURRENT_ROLE);
        return roleObj == null ? null :  Long.valueOf(roleObj.toString());
    }

    public static void setCurrentRole(Long role) {
        set(CommonConstant.CURRENT_ROLE, role);
    }


}
