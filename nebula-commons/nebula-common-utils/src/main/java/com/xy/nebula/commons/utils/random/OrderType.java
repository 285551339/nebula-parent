package com.xy.nebula.commons.utils.random;

/**
 * 订单生成工具
 */
public enum OrderType {
    NETCAFE_RECHARGE_ORDER(10, "网咖充值订单"),
    NETCAFE_SHOPPING_ORDER(11, "网咖购物订单"),
    HOTEL_ORDER(20, "酒店预定订单"),
    HOTEL_MEMBER_CARD_ORDER(21, "会员卡购买订单"),
    REFUND_ORDER_NO(30, "预定订单退款单号");

    private Integer type;
    private String description;

    OrderType(Integer type, String description) {
        this.type = type;
        this.description = description;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
