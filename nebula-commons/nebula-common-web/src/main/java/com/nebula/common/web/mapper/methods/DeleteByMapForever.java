package com.nebula.common.web.mapper.methods;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.util.Map;

/**
 * @Description 根据columnMap 条件删除记录(物理删除)
 * @Author chenxudong
 * @Date 2021/3/18 16:17
 */
public class DeleteByMapForever extends AbstractMethod {

    private static final String METHOD = "deleteByMapForever";

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlMethod sqlMethod = SqlMethod.DELETE_BY_MAP;
        String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), this.sqlWhereByMap(tableInfo));
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, Map.class);
        return this.addDeleteMappedStatement(mapperClass, METHOD, sqlSource);
    }
}
