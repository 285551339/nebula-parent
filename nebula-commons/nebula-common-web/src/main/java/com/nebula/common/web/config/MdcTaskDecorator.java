package com.nebula.common.web.config;

import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

import java.util.Map;

/**
 * description: MdcTaskDecorator
 * date: 2020-09-14 20:07
 * author: chenxd
 * version: 1.0
 */
public class MdcTaskDecorator implements TaskDecorator {

    /**
     * 异步线程获取主线程上下文
     * @param runnable
     * @return
     */
    @Override
    public Runnable decorate(Runnable runnable) {
        Map<String,String> map = MDC.getCopyOfContextMap();
        return () -> {
            try{
                if (map != null) {
                    MDC.setContextMap(map);
                }
                runnable.run();
            } finally {
                MDC.clear();
            }
        };
    }
}
