package com.xy.nebulaol.user.dto.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zangliulu
 * @Title: 应用请求参数
 * @Package
 * @Description:
 * @date 2021/6/23 15:29
 */
@Data
public class ClientDetailsReqDto implements Serializable {
    private static final long serialVersionUID = 6136316309424885399L;

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
