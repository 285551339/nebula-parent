package com.xy.nebula.common.domain.vo.req;

import lombok.Data;

import java.io.Serializable;

/**
 * description: BaseAdminReq
 * date: 2020-09-09 11:15
 * author: chenxd
 * version: 1.0
 */
@Data
public class BaseAdminReq implements Serializable {

    private static final long serialVersionUID = 764549893506161809L;

    private Long userId;
    private String username;
    private String idCard;
    private String realname;
    private boolean superAdmin;

}
