package com.nebula.commons.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @Description 分批插入数据库工具类
 * @Author chenxudong
 * @Date 2021/3/10 11:24
 */
public class BatchInsertUtil {

    private final static int SIZE = 1000;

    /**
     * 如果需要调整并发数目，修改下面方法的第二个参数即可
     */
    static {
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "4");
    }

    /**
     * 分批插入数据库
     * @param list     插入数据集合
     * @param consumer 消费型方法，直接使用 mapper::method 方法引用的方式
     * @param <T>      插入的数据类型
     */
    public static <T> void insertData(List<T> list, Consumer<List<T>> consumer) {
        insertData(list, consumer, SIZE);
    }

    /** 分批插入数据库
     * 插入方法 BatchInsertUtil.insertData(list, xxxMapper::saveBatch, 2000);
     *
     * @param list     插入数据集合
     * @param consumer 消费型方法，直接使用 mapper::method 方法引用的方式
     * @param <T>      插入的数据类型
     * @param size     每次插入数据库条数
     */
    public static <T> void insertData(List<T> list, Consumer<List<T>> consumer, int size) {
        if (list == null || list.size() < 1) {
            return;
        }
        List<List<T>> streamList = new ArrayList<>();
        for (int i = 0; i < list.size(); i += size) {
            int j = Math.min((i + size), list.size());
            List<T> subList = list.subList(i, j);
            streamList.add(subList);
        }
        // 并行流使用的并发数是 CPU 核心数，不能局部更改。全局更改影响较大，斟酌
        streamList.parallelStream().forEach(consumer);
    }

}
