package com.xy.nebula.commons.utils.wechat;


import lombok.*;

/**
* @Description: 微信token凭证
* @Param:
* @throws:
* @author: fushilin
* @Date: 2020/12/29 12:02
* @versions: 1.0
*/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper=true)
public class AccessToken extends WechatResult {
    // 获取到的凭证
    private String access_token;
    // 凭证有效时间，单位：秒
    private Integer expires_in;
    // 凭证获得的时间
    private Long createdTime;

    private String openid;

    public boolean tokenExpired(){
        return (System.currentTimeMillis() - getCreatedTime()) / 1000 > 7000;
    }
}

