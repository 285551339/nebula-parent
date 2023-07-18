package com.nebula.uaa.vo.req;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * description: LoginVo
 * date: 2020-09-09 21:33
 * author: zangliulu
 * version: 1.0
 */
@Data
public class LoginReqVo {

    @NotNull(message = "用户名不能为空")
    private String username;
    @NotNull(message = "密码不能为空")
    private String password;

}
