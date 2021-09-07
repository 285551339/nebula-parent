package com.xy.nebulaol.common.domain.vo.req;

import lombok.Data;

import java.io.Serializable;

@Data
public class BasePayReqVo  implements Serializable {

    private static final long serialVersionUID = -6607245271820074588L;

    private String code;

    private String msg;
    /**
     * 扩展字段
     */
    private String extMap;
    /**
     * 订单号
     */
    private String orderNo;

}
