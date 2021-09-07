package com.xy.nebulaol.common.domain.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.xy.nebulaol.common.domain.context.ThreadLocalContext;
import com.xy.nebulaol.common.domain.vo.req.BaseAdminReq;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * description: BaseEntity
 * date: 2020-09-01 22:18
 * author: chenxd
 * version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 5663975977550116965L;

    //主键
    @TableId
    private Long id;
    @TableLogic
    private Integer deleted;
    //创建人
    @TableField(fill = FieldFill.INSERT)
    private Long createdBy;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;
    //更新人
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updatedBy;
    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedTime;

    /**
     * 新增时使用
     */
    public void setCreateCommon() {
        //BaseAdminReq admin = ThreadLocalContext.getAdmin();
        //Long userId = admin == null ? 0 : admin.getUserId();
        Long userId = 0L;
        Date now = new Date();
        this.deleted = 0;
        this.createdBy = userId;
        this.createdTime = now;
        this.updatedBy = userId;
        this.updatedTime = now;
    }

    /**
     * 修改时使用
     */
    public void setUpdateCommon() {
        //BaseAdminReq admin = ThreadLocalContext.getAdmin();
        //Long userId = admin == null ? 0 : admin.getUserId();
        Long userId = 0L;
        this.updatedBy = userId;
    }
}
