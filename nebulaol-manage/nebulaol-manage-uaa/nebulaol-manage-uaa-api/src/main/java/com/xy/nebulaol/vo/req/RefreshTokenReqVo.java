package com.xy.nebulaol.vo.req;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * description: LoginVo
 * date: 2020-09-09 21:33
 * author: chenxd
 * version: 1.0
 */
@Data
public class RefreshTokenReqVo {

    @NotNull(message = "刷新token值不能为空")
    private String refreshToken;

}
