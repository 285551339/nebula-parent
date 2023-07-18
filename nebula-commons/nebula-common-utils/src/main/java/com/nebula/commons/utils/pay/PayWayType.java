package com.nebula.commons.utils.pay;

/**
 * @author zangliulu
 * @Title:
 * @Package
 * @Description: 支付方式
 * @date 2021/4/15 16:02
 */
public enum PayWayType {

    WECHAT("WECHAT", "微信支付"),
    ALIPAY("ALIPAY", "支付宝支付"),
    UNIPAY("UNIPAY", "云闪付支付"),
    CASH("CASH", "现金支付"),
    JDPAY("JDPAY", "京东支付");


    private String type;
    private String description;

    PayWayType(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
