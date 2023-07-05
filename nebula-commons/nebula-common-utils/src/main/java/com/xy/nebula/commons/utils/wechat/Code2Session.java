package com.xy.nebula.commons.utils.wechat;

import lombok.*;

/**
 * @author zangliulu
 * @Title:
 * @Package
 * @Description: 小程序登录 返回值
 * @date 2021/4/9 10:55
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper=true)
public class Code2Session extends WechatResult {

    private String openid;
    private String session_key;
    private String unionid;
}
