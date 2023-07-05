package com.xy.nebula.common.web.mapper.methods;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * @Description 根据 ID 删除
 * @Author chenxudong
 * @Date 2021/3/18 16:17
 */
public class DeleteByIdForever extends AbstractMethod {

    private static final String METHOD = "deleteByIdForever";

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlMethod sqlMethod = SqlMethod.DELETE_BY_ID;
        String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), tableInfo.getKeyColumn(),
                tableInfo.getKeyProperty());
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, Object.class);
        return this.addDeleteMappedStatement(mapperClass, METHOD, sqlSource);
    }
}
