package com.xy.nebulao.commons.utils.bean;

import com.google.common.collect.Lists;
import org.dozer.DozerBeanMapper;

import java.util.Collection;
import java.util.List;

/**
 * @apiNote 针对java8的 LocalDate、LocalDateTime 等对象，复制不支持，使用{@link BeanMapperLocalUtil} 代替，推荐使用该复制工具类
 *
 * description: BeanMapper
 * date: 2020-12-17 16:06
 * author: chenxd
 * version: 1.0
 */
public class BeanMapperUtil {

    private static final DozerBeanMapper dozer = new DozerBeanMapper();
    /**
     * 拷贝对象
     * @param source
     * @param destinationClass
     * @param <T>
     * @return
     */
    public static <T> T map(Object source, Class<T> destinationClass) {
        if(null != source) {
            return dozer.map(source, destinationClass);
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
                T destinationObject = dozer.map(sourceObject, destinationClass);
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
                T destinationObject = (T) dozer.map(sourceObject, t.getClass());
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
        dozer.map(source, destination);
    }
}
