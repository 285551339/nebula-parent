package com.xy.nebula.common.web.mapper.methods;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * @Description 根据 ID 集合删除(物理删除)
 * @Author chenxudong
 * @Date 2021/3/18 16:17
 */
public class DeleteBatchIdsForever extends AbstractMethod {

    private static final String METHOD = "deleteBatchIdsForever";

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlMethod sqlMethod = SqlMethod.DELETE_BATCH_BY_IDS;
        String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), tableInfo.getKeyColumn(),
                SqlScriptUtils.convertForeach("#{item}", COLLECTION, null, "item", COMMA));
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, Object.class);
        return this.addDeleteMappedStatement(mapperClass, METHOD, sqlSource);
    }
}
