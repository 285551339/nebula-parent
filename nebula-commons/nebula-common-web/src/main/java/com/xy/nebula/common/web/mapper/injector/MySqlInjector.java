package com.xy.nebula.common.web.mapper.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.xy.nebula.common.web.mapper.methods.DeleteBatchIdsForever;
import com.xy.nebula.common.web.mapper.methods.DeleteByIdForever;
import com.xy.nebula.common.web.mapper.methods.DeleteByMapForever;
import com.xy.nebula.common.web.mapper.methods.DeleteForever;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description 注入自定义方法
 * @Author chenxudong
 * @Date 2021/3/18 16:25
 */
@Component
public class MySqlInjector extends DefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass);
        methodList.add(new DeleteForever());
        methodList.add(new DeleteByIdForever());
        methodList.add(new DeleteByMapForever());
        methodList.add(new DeleteBatchIdsForever());
        return methodList;
    }
}
