package com.nebula.commons.utils;

/**
 * @Description: 正则表达式类
 * @Param:
 * @throws:
 * @author: fushilin
 * @Date: 2021/5/19 10:23
 * @versions: 1.0
 */
public class RegularUtil {

    //0-99.9 两位数+一位小数正则
    public final static String TWO_NUMBER = "^(\\d|[1-9]\\d)(\\.\\d+)*$";

    //ip正则
    public final static String IP = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";

    //MAC正则
    public final static String MAC = "((([A-Fa-f0-9]{2}-){5})|(([A-Fa-f0-9]{2}:){5}))[A-Fa-f0-9]{2}$";

    //机器名称正则
    public final static String LETTE_OR_RNUMBER = "^[0-9a-zA-Z]{1,10}";

    //机器名称前缀正则
    public final static String LETTE = "^[a-zA-Z]{0,7}";

}
