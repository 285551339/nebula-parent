package com.xy.nebula.common.web.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Description DML自动填充字段值
 * @Author chenxudong
 * @Date 2021/3/18 9:18
 */
@Slf4j
@Component
public class CustomMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        //BaseAdminReq admin = ThreadLocalContext.getAdmin();
        //Long userId = admin == null ? 0 : admin.getUserId();
        Long userId = 0L;
        Date now = new Date();
        this.setFieldValByName("createdBy", userId, metaObject);
        this.setFieldValByName("createdTime", now, metaObject);
        this.setFieldValByName("updatedBy", userId, metaObject);
        this.setFieldValByName("updatedTime", now, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //BaseAdminReq admin = ThreadLocalContext.getAdmin();
        //Long userId = admin == null ? 0 : admin.getUserId();
        Long userId = 0L;
        Date now = new Date();
        this.setFieldValByName("updatedBy", userId, metaObject);
        this.setFieldValByName("updatedTime", now, metaObject);
    }
}
