package com.xy.nebula.common.domain.vo.req;

import lombok.Data;

import java.io.Serializable;

/**
 * description: 酒店用户
 * date: 2020-09-09 11:15
 * author: chenxd
 * version: 1.0
 */
@Data
public class BaseUserReq implements Serializable {

    private static final long serialVersionUID = -8344604891919090465L;

    private Long userId;
    private String openId;
    private String mobile;
    private String idCard;
    private String realname;

    //网关云桌面
    private Long branchId;
    private String branchName;
    private String machineName;
}
