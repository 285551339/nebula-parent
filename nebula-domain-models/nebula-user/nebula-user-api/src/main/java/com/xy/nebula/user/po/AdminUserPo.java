package com.xy.nebula.user.po;


import com.baomidou.mybatisplus.annotation.TableName;
import com.xy.nebula.common.domain.po.BaseEntity;
import lombok.*;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author llzang
 */
@Data
@Builder
@TableName("admin_user")
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class AdminUserPo extends BaseEntity {


    private String username;

    private String password;

    private Integer enable;

    private Long phone;

    private String nickName;

    private String avatar;


}
