package com.nebula.commons.utils;

import cn.hutool.extra.pinyin.PinyinUtil;

/**
 * @author zangliulu
 * @Title:
 * @Package
 * @Description: 拼音工具类  重新封装hutool拼音工具类
 * @date 2020/12/15 11:48
 */
public class PinyinUtils extends PinyinUtil {

    /**
     * 将输入字符串转为拼音首字母，其它字符原样返回
     *
     * @param str
     * @return
     */
    public static String getFirstLetter(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char firstLetter = PinyinUtil.getFirstLetter(str.charAt(i));
            stringBuffer.append(firstLetter);
        }
        return stringBuffer.toString();
    }

    /**
     * 是否为中文字符串
     *
     * @param str
     * @return
     */
    public static boolean isChinese(String str) {
        for (int i = 0; i < str.length(); i++) {
            boolean flag = PinyinUtil.isChinese(str.charAt(i));
            if (!flag) {
                return flag;
            }
        }
        return true;
    }

    /**
     * @param
     * @return
     * @throws
     * @Description: 返回连续的拼音字符串
     * @author zangliulu
     * @date 2020/12/16 17:58
     */
    public static String getPinyinContinuous(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        String pinyin = PinyinUtil.getPinyin(str);
        String[] pinyins = StrUtil.split(pinyin, " ");
        for (int i = 0; i < pinyins.length; i++) {
            stringBuffer.append(pinyins[i]);
        }
        return stringBuffer.toString();
    }
}
