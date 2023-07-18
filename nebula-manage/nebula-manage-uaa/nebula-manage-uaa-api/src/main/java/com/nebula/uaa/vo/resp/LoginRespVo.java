package com.nebula.uaa.vo.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * description: TokenService
 * date: 2020-09-09 20:52
 * author: chenxd
 * version: 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRespVo {

    private String token;
    private Long tokenExpiresIn;
    private String refreshToken;
    private Long refreshTokenExpiresIn;

}
