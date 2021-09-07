package com.xy.nebulao.commons.utils.pay;

/**
 * @author zangliulu
 * @Title:
 * @Package
 * @Description: 订单业务类型
 * @date 2021/4/15 21:01
 */
public enum OrderBusinessType {

    CHARGE("CHARGE", "充值"),
    SALE("SALE", "消费");
    private String type;
    private String description;

    OrderBusinessType(String type, String description) {
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
