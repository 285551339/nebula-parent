package com.xy.nebulaol.user.dto.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zangliulu
 * @Title: 用户请求信息
 * @Package
 * @Description:
 * @date 2021/6/22 15:27
 */
@Data
public class UserDetailsReqDto implements Serializable {

    private static final long serialVersionUID = 7916392640123499382L;


    private String username;

    private String password;


}
