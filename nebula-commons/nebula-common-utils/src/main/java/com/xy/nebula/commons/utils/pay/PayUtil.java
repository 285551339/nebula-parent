package com.xy.nebula.commons.utils.pay;

import java.util.regex.Pattern;

/**
 * @author zangliulu
 * @Title:
 * @Package
 * @Description: 支付模块公共工具类
 * @date 2021/4/15 15:10
 */
public class PayUtil {

    /**
     * @param authCode
     * @Description: 根据支付码判断支付方式
     * @return:
     * @Author: zangliulu
     * @Date: 2019/3/18 15:25
     */
    public static String validateCode(String authCode) {
        String codeStr = null;
        //是否是微信支付码
        if (Pattern.matches(PayAuthCodeRegxps.WXAUTHCODEREGEXP.getRegexp(), authCode)) {
            codeStr = PayWayType.WECHAT.getType();
            //是否是支付宝支付码
        } else if (Pattern.matches(PayAuthCodeRegxps.ALIPAYAUTHCODEREGEXP.getRegexp(), authCode)) {
            codeStr = PayWayType.ALIPAY.getType();
            //是否是京东支付码
        } else if (Pattern.matches(PayAuthCodeRegxps.JDPAYAUTHCODEREGEXP.getRegexp(), authCode)) {
            codeStr = PayWayType.JDPAY.getType();
        }
        return codeStr;
    }
}
