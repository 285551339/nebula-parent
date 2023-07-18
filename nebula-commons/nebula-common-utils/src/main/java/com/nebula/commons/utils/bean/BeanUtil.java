package com.nebula.commons.utils.bean;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * description: BeanUtils
 * date: 2020-09-06 13:34
 * author: chenxd
 * version: 1.0
 */
@Slf4j
public class BeanUtil extends org.springframework.beans.BeanUtils {


    public static <T> T copyProperties(Object source, Class<T> destinationClass) {
        if (null == source || null == destinationClass) {
            return null;
        }
        T t = null;
        try {
            t = destinationClass.newInstance();
        } catch (InstantiationException e) {
            log.error("复制属性异常", e);
        } catch (IllegalAccessException e) {
            log.error("复制属性异常", e);
        }
        copyProperties(source, t);
        return t;
    }

    /**
     * 拷贝集合
     * @param sources
     * @param targetSupplier
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> List<T> convertListProperties(List<S> sources, Supplier<T> targetSupplier) {
        return convertListProperties(sources, targetSupplier, null);
    }

    /**
     * 拷贝集合(可通过回调函数自定义规则)
     * @param sources
     * @param targetSupplier
     * @param callBack
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> List<T> convertListProperties(List<S> sources, Supplier<T> targetSupplier, ConvertCallBack<S, T> callBack) {
        if (null == sources || null == targetSupplier) {
            return null;
        }
        List<T> list = new ArrayList<>(sources.size());
        for (S source : sources) {
            T target = targetSupplier.get();
            copyProperties(source, target);
            if (callBack != null) {
                callBack.callBack(source, target);
            }
            list.add(target);
        }
        return list;
    }

    /**
     * 回调接口
     * @param <S>
     * @param <T>
     */
    @FunctionalInterface
    public interface ConvertCallBack<S, T> {
        void callBack(S t, T s);
    }

}
