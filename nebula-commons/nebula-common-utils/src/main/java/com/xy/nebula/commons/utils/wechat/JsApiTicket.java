package com.xy.nebula.commons.utils.wechat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: MaXin
 * @Description:
 * @DateTime: 2021/5/18$ 15:39$
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JsApiTicket extends WechatResult implements Serializable {
    private static final long serialVersionUID = -3382843487419731709L;
    private String ticket;
    private Integer expires_in;
}
