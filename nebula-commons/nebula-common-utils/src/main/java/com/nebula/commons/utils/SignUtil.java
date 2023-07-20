package com.nebula.commons.utils;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * description: SignUtil
 * date: 2020-09-09 17:12
 * author: chenxd
 * version: 1.0
 */
@Slf4j
public class SignUtil {

    //连接串：&
    private static final String SEPARATOR="\u0026";

    /**
     * 构建签名数据
     * @param params
     * @return
     */
    public static String buildSignStr(Map<String, Object> params) {
        StringBuilder sb = new StringBuilder();
        // 将参数以参数名的字典升序排序
        Map<String, Object> sortParams = new TreeMap<>(params);
        // 遍历排序的字典,并拼接"key=value"格式
        for (Map.Entry<String, Object> entry : sortParams.entrySet()) {
            if (entry.getValue() == null) {
                continue;
            }
            if (sb.length() != 0) {
                sb.append(SEPARATOR);
            }
            if (entry.getValue() instanceof Map) {
                sb.append(entry.getKey()).append("=").append(buildSignStr((Map<String, Object>) entry.getValue()));
            } else if (entry.getValue() instanceof JSONArray) {
//                sb.append(entry.getKey()).append("=").append(Joiner.on(",").join((List) entry.getValue()));
                sb.append(entry.getKey()).append("=").append(buildSignStr((JSONArray) entry.getValue()));
            } else {
                sb.append(entry.getKey()).append("=").append(entry.getValue());
            }
        }
        return sb.toString();
    }

    private static String buildSignStr(JSONArray params) {
        StringBuilder childSb = new StringBuilder();
        if (params.get(0) instanceof String || ObjectUtil.isBasicType(params.get(0))) {
            childSb.append(Joiner.on(",").join(params));
        }
        if (params.get(0) instanceof JSONObject) {
            params.forEach(o -> {
                childSb.append(buildSignStr((JSONObject) o)).append(SEPARATOR);
            });

        }
        if (params.get(0) instanceof JSONArray) {
            params.forEach(o -> {
                childSb.append(buildSignStr((JSONArray) o));
            });
        }
        int lastSeparator = childSb.toString().lastIndexOf(SEPARATOR);
        if (lastSeparator >= 0) {
            return childSb.substring(0, lastSeparator);
        }
        return childSb.toString();
    }

    /**
     * 构建访问参数
     * @return
     */
    public static String buildSign(Map<String, Object> sortParams, String secret) {
        String sortParam = buildSignStr(sortParams) + "&key=" + secret;
        String sign = DigestUtils.md5Hex(sortParam).toUpperCase();
        return sign;
    }

    /**
     * 对象转map
     * @param obj
     * @return
     */
    public static Map<String, Object> obj2Map(Object obj) {
        Map<String, Object> map = new HashMap<>(16);
        // 获取f对象对应类中的所有属性域
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0, len = fields.length; i < len; i++) {
            String varName = fields[i].getName();
            try {
                // 获取原来的访问控制权限
                boolean accessFlag = fields[i].isAccessible();
                // 修改访问控制权限
                fields[i].setAccessible(true);
                // 获取在对象f中属性fields[i]对应的对象中的变量
                Object o = fields[i].get(obj);
                if (o != null) {
                    map.put(varName, o.toString());
                }
                // 恢复访问控制权限
                fields[i].setAccessible(accessFlag);
            } catch (IllegalArgumentException e) {
                log.error("obj2Map failed", e);
            } catch (IllegalAccessException e) {
                log.error("obj2Map failed", e);
            }
        }
        return map;
    }

    public static void main(String[] args) {
        String json = "{\n" +
                "\t\"d\":\"1992\",\n" +
                "\t\"a\":\"2020\",\n" +
                "\t\"c\":\"8989\",\n" +
                "\t\"sd\":\"0829\",\n" +
                "\t\"hh\":[\"好的\",\"不好\",\"整的\"],\n" +
                "\t\"xs\": [{\"language\":[{\"java\":\"good\"},{\"golang\":\"very good\"}]},{\"language\":[{\"java\":\"好的\"},{\"golang\":\"不好\"}]}],\n" +
                "\t\"dd\":\"帕西\"\n" +
                "}";
        Map<String, Object> map = JSONObject.parseObject(json, Map.class);
        System.out.println(map);
        String js = buildSignStr(map);
        System.out.println(js);
    }
    /**
     * 验证签名
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public static boolean checkSignature(String signature, String timestamp, String nonce, String token) {
        String[] arr = new String[] { token, timestamp, nonce };
        // 将token、timestamp、nonce三个参数进行字典序排序
        Arrays.sort(arr);

        // 将三个参数字符串拼接成一个字符串
        StringBuilder content = new StringBuilder();
        for(String str : arr){
            content.append(str);
        }

        //将拼接的字符串进行sha1加密
        String tmpStr = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return tmpStr != null && tmpStr.equals(signature.toUpperCase());
    }

    /**
     * 将字节数组转换为字符串
     * @param bytes
     * @return
     */
    private static String byteToStr(byte[] bytes) {
        String strDigest = "";
        for (byte aByte : bytes) {
            strDigest += byteToHexStr(aByte);
        }
        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte){
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];
        return  new String(tempArr);
    }
}