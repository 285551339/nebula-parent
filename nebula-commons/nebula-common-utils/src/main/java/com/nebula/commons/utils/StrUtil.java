package com.nebula.commons.utils;

/**
 * @author zangliulu
 * @Title:
 * @Package
 * @Description: 字符串工具类
 * @date 2020/12/17 16:51
 */
public class StrUtil extends cn.hutool.core.util.StrUtil {

    /**
     * @Description: 按照一定长度，在数字前面增加零
     * @return String
     * @throws
     * @author zangliulu
     * @date 2020/12/17 16:56
     */
    public static String addZeroNumberToStr(Integer length,Integer number){

        return  String.format("%0"+length+"d", number);
    }

}
