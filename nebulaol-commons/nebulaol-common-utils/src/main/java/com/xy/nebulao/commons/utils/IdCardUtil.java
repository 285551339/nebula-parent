package com.xy.nebulao.commons.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class IdCardUtil {

    private static SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

    /**
     * 身份证号大写后取MD5
     * @param idCard
     * @return
     */
    public static String IdCard2MD5(String idCard) {
        if (idCard == null) {
            return null;
        }
        return DigestUtils.md5Hex(idCard).toUpperCase();
    }

    /**
     * 根据身份证号提取性别信息
     * @param idCard
     * @return
     */
    public static int extractSex(String idCard) {
        if (idCard == null) {
            return 0;
        }
        String sexNumStr = idCard.substring(16, 17);
        return Integer.parseInt(sexNumStr) % 2 == 1 ? 1 : 2;
    }

    /**
     * 根据身份证号提取生日信息
     * @param idCard
     * @return
     */
    public static String extractBirthday(String idCard) {
        if (idCard == null) {
            return null;
        }
        return idCard.substring(6, 10) + "-" + idCard.substring(10, 12) + "-" + idCard.substring(12, 14);
    }

    /**
     * 根据身份证号提取年龄
     * @param idCard
     * @return
     */
    public static int extractAge(String idCard) {
        if (idCard == null) {
            return 0;
        }
        Date now = new Date();
        // 年份
        String year = idCard.substring(6, 10);
        // 月份
        String yue = idCard.substring(10, 12);
        // 天
        String day = idCard.substring(12, 14);

        String nowDate = format.format(now);
        // 当前年份
        String nowYear = nowDate.substring(0, 4);
        // 当前月份
        String nowMonth = nowDate.substring(4,6);
        // 当前天数
        String nowDay = nowDate.substring(6);
        // 当前
        if (Integer.parseInt(yue+day) <= Integer.parseInt(nowMonth+nowDay)) {
            return Integer.parseInt(nowYear) - Integer.parseInt(year) + 1;
            // 当前用户还没过生日
        } else {
            return Integer.parseInt(nowYear) - Integer.parseInt(year);
        }
    }

    /**
     * 判断是否正确的身份证
     * @param idCard
     * @return
     */
    public static boolean isIdCard(String idCard) {
        if (idCard == null || "".equals(idCard)) {
            return false;
        }
        // 定义判别用户身份证号的正则表达式（15位或者18位，最后一位可以为字母）
        String regularExpression = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
                "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
        //假设18位身份证号码:41000119910101123X  410001 19910101 123X
        //^开头
        //[1-9] 第一位1-9中的一个      4
        //\\d{5} 五位数字           10001（前六位省市县地区）
        //(18|19|20)                19（现阶段可能取值范围18xx-20xx年）
        //\\d{2}                    91（年份）
        //((0[1-9])|(10|11|12))     01（月份）
        //(([0-2][1-9])|10|20|30|31)01（日期）
        //\\d{3} 三位数字            123（第十七位奇数代表男，偶数代表女）
        //[0-9Xx] 0123456789Xx其中的一个 X（第十八位为校验值）
        //$结尾

        //假设15位身份证号码:410001910101123  410001 910101 123
        //^开头
        //[1-9] 第一位1-9中的一个      4
        //\\d{5} 五位数字           10001（前六位省市县地区）
        //\\d{2}                    91（年份）
        //((0[1-9])|(10|11|12))     01（月份）
        //(([0-2][1-9])|10|20|30|31)01（日期）
        //\\d{3} 三位数字            123（第十五位奇数代表男，偶数代表女），15位身份证不含X
        //$结尾
        boolean matches = idCard.matches(regularExpression);
        //判断第18位校验值
        if (matches) {
            if (idCard.length() == 18) {
                try {
                    char[] charArray = idCard.toCharArray();
                    //前十七位加权因子
                    int[] idCardWi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
                    //这是除以11后，可能产生的11位余数对应的验证码
                    String[] idCardY = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
                    int sum = 0;
                    for (int i = 0; i < idCardWi.length; i++) {
                        int current = Integer.parseInt(String.valueOf(charArray[i]));
                        int count = current * idCardWi[i];
                        sum += count;
                    }
                    char idCardLast = charArray[17];
                    int idCardMod = sum % 11;
                    if (idCardY[idCardMod].toUpperCase().equals(String.valueOf(idCardLast).toUpperCase())) {
                        return true;
                    } else {
                        log.error("身份证最后一位:" + String.valueOf(idCardLast).toUpperCase() +
                                "错误,正确的应该是:" + idCardY[idCardMod].toUpperCase());
                    }
                } catch (Exception e) {
                    log.error("身份证号码错误");
                }
            }

        }
        return matches;
    }
}