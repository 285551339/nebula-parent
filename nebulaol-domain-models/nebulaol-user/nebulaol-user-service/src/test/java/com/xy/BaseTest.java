package com.xy;

import com.xy.nebulaol.common.domain.constant.CommonConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zangliulu
 * @Title:
 * @Package
 * @Description:
 * @date 2021/10/14 10:51
 */
public class BaseTest {
    public static String env = "dev";
    public static String target_url;
    public static Map<String, Object> headers = new HashMap<>();
    public static Map<String, Object> params = new HashMap<>();
    public static Map<String, String> secretMap = new HashMap<>();
    private static final Map<String, String> tokenParams = new HashMap<>();

    static {
        switch (env) {
            case "dev":
                target_url = "http://127.0.0.1:2020/";
                secretMap.put(CommonConstant.APPCODE_XY_MANAGE_UAA, "OpLyfckC0F8KVml6");
                break;
            case "test":
                target_url = "";
                tokenParams.put("username","");
                tokenParams.put("password","");
                secretMap.put(CommonConstant.APPCODE_XY_MANAGE_UAA, "OpLyfckC0F8KVml6");
                break;
            case "prod":
                target_url = "xxx";
                tokenParams.put("username","");
                tokenParams.put("password","");
                secretMap.put(CommonConstant.APPCODE_XY_MANAGE_UAA, "OpLyfckC0F8KVml6");
        }
    }

}
