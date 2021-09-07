package com.xy.nebulao.commons.utils.pay;

/**
 * @author zangliulu
 * @Title:
 * @Package
 * @Description: pay通知类型
 * @date 2021/4/19 20:53
 */
public enum PayNotificationType {

    REFD("REFD", "退款"),
    SALE("SALE", "消费");

    private String type;
    private String description;

    PayNotificationType(String type, String description) {
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
