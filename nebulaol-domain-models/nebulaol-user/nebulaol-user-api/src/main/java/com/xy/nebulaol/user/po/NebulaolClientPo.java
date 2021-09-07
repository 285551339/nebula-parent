package com.xy.nebulaol.user.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xy.nebulaol.common.domain.po.BaseEntity;
import lombok.*;

/**
 * @author zangliulu
 * @Title: 应用管理
 * @Package
 * @Description:
 * @date 2021/6/22 11:04
 */
@Data
@Builder
@TableName("nebulaol_client")
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class NebulaolClientPo extends BaseEntity {

    private String clientId;

    private String clientSecret;

    private String scopes;

    private String resourceIds;

    private String grantTypes;

    private String redirectUris;

    private String autoApproveScopes;

    private Long accessTokenValiditySeconds;

    private Long refreshTokenValiditySeconds;

    private String authorityIds;
}
