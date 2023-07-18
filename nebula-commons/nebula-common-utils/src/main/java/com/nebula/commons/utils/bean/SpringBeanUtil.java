package com.nebula.commons.utils.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * description: SpringBeanUtil
 * date: 2020-09-25 08:48
 * author: chenxd
 * version: 1.0
 */
@Slf4j
@Component
public class SpringBeanUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringBeanUtil.applicationContext == null) {
            SpringBeanUtil.applicationContext = applicationContext;
        }
    }

    //获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    //通过name获取 Bean.
    public static Object getBean(String name) {
        return  applicationContext != null ? applicationContext.getBean(name) : null;
    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext != null ? applicationContext.getBean(clazz) : null;
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name, Class<T> clazz) {
        return applicationContext != null ? applicationContext.getBean(name, clazz) : null;
    }

    //通过Clazz返回指定的Beans
    public static <T> Map<String, T> getBeansByType(Class<T> clazz) {
        return applicationContext != null ? applicationContext.getBeansOfType(clazz) : null;
    }

    //通过name，读取配置文件属性
    public static String getProperty(String name) {
        if (applicationContext == null) {
            return null;
        }
        Environment environment = applicationContext.getEnvironment();
        return environment.getProperty(name);
    }

}

