package com.xy.nebulaol.user.po;


import com.baomidou.mybatisplus.annotation.TableName;
import com.xy.nebulaol.common.domain.po.BaseEntity;
import lombok.*;

import java.io.Serializable;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author llzang
 */
@Data
@Builder
@TableName("nebulaol_user")
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class NebulaolUserPo extends BaseEntity {


    private String username;

    private String password;

    private Integer enable;

    private Long phone;

    private String nickName;

    private String avatar;


}
