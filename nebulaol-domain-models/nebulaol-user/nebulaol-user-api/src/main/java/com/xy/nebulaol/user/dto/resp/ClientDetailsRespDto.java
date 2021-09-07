package com.xy.nebulaol.user.dto.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zangliulu
 * @Title: 应用详情接口
 * @Package
 * @Description:
 * @date 2021/6/23 15:31
 */
@Data
public class ClientDetailsRespDto implements Serializable {
    private static final long serialVersionUID = 2673423100452438797L;

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
