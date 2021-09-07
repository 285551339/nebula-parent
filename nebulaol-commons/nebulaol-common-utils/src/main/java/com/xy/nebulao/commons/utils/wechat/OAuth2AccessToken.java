package com.xy.nebulao.commons.utils.wechat;

import lombok.*;

/**
 * description: OAuth2AccessToken
 * date: 2021-01-08 10:26
 * author: chenxd
 * version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper=true)
public class OAuth2AccessToken extends WechatResult{

    //接口调用凭证
    private String access_token;
    //授权用户唯一标识
    private String openid;
    //用户授权的作用域，使用逗号（,）分隔
    private String scope;
    //access_token接口调用凭证超时时间，单位（秒）
    private Integer expires_in;
    //用户刷新access_token
    private String refresh_token;
    //当且仅当该网站应用已获得该用户的userinfo授权时，才会出现该字段。
    private String unionid;

}
