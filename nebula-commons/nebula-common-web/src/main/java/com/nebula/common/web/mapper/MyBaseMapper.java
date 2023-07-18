package com.nebula.common.web.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * @Description 自定义基础mapper
 * @Author chenxudong
 * @Date 2021/3/18 16:04
 */
public interface MyBaseMapper<T> extends BaseMapper<T> {


    /**
     * 根据 ID 删除（物理删除）
     *
     * @param id 主键ID
     */
    int deleteByIdForever(Serializable id);

    /**
     * 根据 columnMap 条件，删除记录（物理删除）
     *
     * @param columnMap 表字段 map 对象
     */
    int deleteByMapForever(@Param(Constants.COLUMN_MAP) Map<String, Object> columnMap);

    /**
     * 根据 entity 条件，删除记录（物理删除）
     *
     * @param wrapper 实体对象封装操作类（可以为 null）
     */
    int deleteForever(@Param(Constants.WRAPPER) Wrapper<T> wrapper);

    /**
     * 删除（根据ID 批量删除）（物理删除）
     *
     * @param idList 主键ID列表(不能为 null 以及 empty)
     */
    int deleteBatchIdsForever(@Param(Constants.COLLECTION) Collection<? extends Serializable> idList);

}
