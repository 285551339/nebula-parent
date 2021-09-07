package com.xy.nebulao.commons.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * description: RequestUtil
 * date: 2020-12-13 22:09
 * author: chenxd
 * version: 1.0
 */
@Slf4j
public class RequestUtil {

    public static String getHeader(String name) {
        HttpServletRequest request = getHttpServletRequest();
        if (request == null) {
            return null;
        }
        return request.getHeader(name);
    }

    public static HttpServletRequest getHttpServletRequest() {
        try {
            // hystrix隔离策略会导致RequestContextHolder.getRequestAttributes()返回null
            // 解决方案：http://www.itmuch.com/spring-cloud-sum/hystrix-threadlocal/
            RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                ServletRequestAttributes sra = (ServletRequestAttributes) attributes;
                if (sra != null) {
                    return sra.getRequest();
                }
            }
            return null;
        } catch (Exception e) {
            log.error("getHttpServletRequest failed", e);
        }
        return null;
    }
}
