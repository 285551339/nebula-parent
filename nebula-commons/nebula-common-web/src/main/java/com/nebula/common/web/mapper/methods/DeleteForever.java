package com.nebula.common.web.mapper.methods;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * @Description 根据 entity 条件删除记录
 * @Author chenxudong
 * @Date 2021/3/18 16:17
 */
public class DeleteForever extends AbstractMethod {

    private static final String METHOD = "deleteForever";

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlMethod sqlMethod = SqlMethod.DELETE;
        String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(),
                sqlWhereEntityWrapper(true, tableInfo),
                sqlComment());
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return this.addDeleteMappedStatement(mapperClass, METHOD, sqlSource);
    }
}
