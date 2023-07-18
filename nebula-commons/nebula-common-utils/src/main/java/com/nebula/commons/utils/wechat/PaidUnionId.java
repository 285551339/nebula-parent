package com.nebula.commons.utils.wechat;

import lombok.*;

/**
 * @author zangliulu
 * @Title:
 * @Package
 * @Description: 小程序用户信息
 * @date 2021/4/8 17:23
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper=true)
public class PaidUnionId extends WechatResult {

    private String unionid;

}
