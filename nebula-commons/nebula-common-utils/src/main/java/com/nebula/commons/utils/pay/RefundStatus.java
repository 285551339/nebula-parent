package com.nebula.commons.utils.pay;

/**
 * @Author: MaXin
 * @Description:
 * @DateTime: 2021/4/20$ 10:24$
 */
public enum RefundStatus {
    REFUND_SUCCESS("SUCCESS","退款完成"),
    REFUND_PROCESSING ("PROCESSING","退款处理中"),
    REFUND_FINISH("FINISH","退款失败");

    private String status;
    private String description;

    RefundStatus(String status, String description) {
        this.status = status;
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
