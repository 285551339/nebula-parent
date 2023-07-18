package com.nebula.commons.utils.pay;


/**
 * @author zangliulu
 * @Title:
 * @Package
 * @Description: 支付码正则表达式
 * @date 2021/4/15 15:14
 */
public enum PayAuthCodeRegxps {

    WXAUTHCODEREGEXP("WX_CODE_REGEXP", "^(10[0-9]|11[0-9]|12[0-9]|13[0-9]|14[0-9]|15[0-9])\\d{15}$"),
    ALIPAYAUTHCODEREGEXP("ALIPAY_CODE_REGEXP", "^(25[0-9]|26[0-9]|27[0-9]|28[0-9]|29[0-9]|30[0-9])\\d{15,21}$"),
    JDPAYAUTHCODEREGEXP("JDPAY_CODE_REGEXP", "^(62[0-9])\\d{16}$");


    private String type;
    private String regexp;

    PayAuthCodeRegxps(String type, String regexp) {
        this.type = type;
        this.regexp = regexp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRegexp() {
        return regexp;
    }

    public void setRegexp(String regexp) {
        this.regexp = regexp;
    }

    public static String getRegexpByType(String type) {
        PayAuthCodeRegxps[] values = PayAuthCodeRegxps.values();
        for (PayAuthCodeRegxps value : values) {
            if (value.getType().equals(type)) {
                return value.getRegexp();
            }
        }
        return null;
    }
}
