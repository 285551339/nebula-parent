package com.xy.nebulao.commons.utils.wechat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: MaXin
 * @Description:
 * @DateTime: 2021/5/17$ 14:34$
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SnsUserInfo extends WechatResult implements Serializable {
    private static final long serialVersionUID = 4051927878045065327L;

    private String openid;

    private String nickname;

    private String sex;

    private String province;

    private String city;

    private String country;

    private String headimgurl;

    private String privilege;

    private String unionid;
}
