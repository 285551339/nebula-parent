package com.xy.nebulao.commons.utils.bean;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.List;

/**
 * 包含LocalDateTime 类型字段对象复制工具类
 *
 * @apiNote 与 {@link BeanMapperUtil} 内部不同，这里使用的是其他版本，对应字段若要使用@Mapping 需要使用 {@link com.github.dozermapper.core.Mapping}
 *
 * @author zhengdd
 * @createTime 2021-05-25
 */
public class BeanMapperLocalUtil {
    private static final Mapper mapper = DozerBeanMapperBuilder.buildDefault();
    /**
     * 拷贝对象
     * @param source
     * @param destinationClass
     * @param <T>
     * @return
     */
    public static <T> T map(Object source, Class<T> destinationClass) {
        if(null != source) {
            return mapper.map(source, destinationClass);
        }
        return null;
    }

    /**
     * 拷贝list
     * @param sourceList
     * @param destinationClass
     * @param <T>
     * @return
     */
    public static <T> List<T> mapList(Collection sourceList, Class<T> destinationClass) {
        List<T> destinationList = Lists.newArrayList();
        if (sourceList != null) {
            for (Object sourceObject : sourceList) {
                T destinationObject = mapper.map(sourceObject, destinationClass);
                destinationList.add(destinationObject);
            }
        }
        return destinationList;
    }

    /**
     * 拷贝list
     * @param sourceList
     * @param t
     * @param <T>
     * @return
     */
    public static <T> List<T> mapObjectList(Collection<?> sourceList, T t) {
        List<T> destinationList = Lists.newArrayList();
        if (sourceList != null) {
            for (Object sourceObject : sourceList) {
                T destinationObject = (T) mapper.map(sourceObject, t.getClass());
                destinationList.add(destinationObject);
            }
        }
        return destinationList;
    }

    /**
     * 拷贝对象（需要先创建目的对象）
     * @param source
     * @param destination
     */
    public static void copy(Object source, Object destination) {
        mapper.map(source, destination);
    }
}
