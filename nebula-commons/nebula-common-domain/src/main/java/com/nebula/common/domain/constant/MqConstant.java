package com.nebula.common.domain.constant;

public class MqConstant {

    //订单支付key
    public static final String HOTEL_ORDER_PAY_KEY = "hotel.order.pay.key";
    //订单支付结果队列名
    public static final String HOTEL_ORDER_SALE = "hotel.order.sale";

    public static final String MEMBER_ORDER_SALE = "member.order.sale";

    public static final String MEMBER_ORDER_SALE_KEY = "member.order.sale.key";
    //延迟队列 交换机类型
    public static final String DELAY_EXCHANGE_TYPE = "x-delayed-message";

    //延迟队列 交换机类型
    public static final String X_DELAYED_TYPE = "x-delayed-type";

    // 会员卡订单延迟队列
    public static final String CARD_DELAY_QUEUE = "card.delay.queue";
    public static final String CARD_DELAY_EXCHANGE = "card.delay.exchange";
    public static final String CARD_ROUTING_KEY = "card.routing.key";

    // 酒店预定订单延迟队列名称
    public static final String ORDER_DELAY_QUEUE = "order.delay.queue";
    // 酒店预定订单交换机名称
    public static final String ORDER_DELAY_EXCHANGE = "order.delay.exchange";
    // 酒店预定订单路由key
    public static final String ORDER_ROUTING_KEY = "client.order.key";

}
