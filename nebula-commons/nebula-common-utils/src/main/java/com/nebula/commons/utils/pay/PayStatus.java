package com.nebula.commons.utils.pay;

/**
 * @author zangliulu
 * @Title:
 * @Package
 * @Description: 京东支付状态
 * @date 2021/4/16 16:26
 */
public enum PayStatus {

    PAYMENT_SUCCESS("SUCCESS", "支付成功"),
    PAYMENT_PROCESSING("PROCESSING", "处理中"),
    PAYMENT_FINISH("FINISH", "已完成"),
    PAYMENT_FAIL("FAIL", "支付失败");

    private String status;
    private String description;

    PayStatus(String status, String description) {
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
