package com.xy.nebula.commons.utils.random;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderNoUtil {

    private static Long orderNo;

    public static Long getOrderNo(Integer type) {
        String rmString = null;
        switch (type) {
            case 20:
                rmString = NumUtil.getFixLengthString(3);
                orderNo = Long.parseLong(OrderType.HOTEL_ORDER.getType() + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + rmString);
                break;
            case 21:
                rmString = NumUtil.getFixLengthString(3);
                orderNo = Long.parseLong(OrderType.HOTEL_MEMBER_CARD_ORDER.getType() + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + rmString);
                break;
            case 10:
            case 11:
            case 31:
                rmString = NumUtil.getFixLengthString(3);
                orderNo = Long.parseLong(OrderType.REFUND_ORDER_NO.getType() + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + rmString);
                break;
            default:
                break;
        }
        return orderNo;
    }
}
