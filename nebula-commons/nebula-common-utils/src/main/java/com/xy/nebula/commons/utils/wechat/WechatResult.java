package com.xy.nebula.commons.utils.wechat;

import lombok.Data;

import java.io.Serializable;

@Data
public class WechatResult implements Serializable {

    private static final long serialVersionUID = 1417412664416197798L;

    private Integer errcode;
    private String errmsg;

}
