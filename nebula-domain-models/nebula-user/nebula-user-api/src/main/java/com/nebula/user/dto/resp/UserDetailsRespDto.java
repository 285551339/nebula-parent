package com.nebula.user.dto.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zangliulu
 * @Title: 用户响应信息
 * @Package
 * @Description:
 * @date 2021/6/22 16:00
 */
@Data
public class UserDetailsRespDto implements Serializable {

    private static final long serialVersionUID = 3941981211320922885L;

    private Long id;

    private String username;

    private String password;

    private Boolean isEnabled;



}
