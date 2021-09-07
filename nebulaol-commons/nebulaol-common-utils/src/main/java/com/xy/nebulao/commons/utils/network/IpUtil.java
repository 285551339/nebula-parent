package com.xy.nebulao.commons.utils.network;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description 获取request中真实IP
 * @Author chenxudong
 * @Date 2020/5/12 17:34
 */
public class IpUtil {

    public static String getIP(HttpServletRequest request) {

        //X-Forwarded-For：Squid 服务代理
        String ip = request.getHeader("X-Forwarded-For");

        //有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
        if (!isInvalid(ip)) {
            ip = ip.split(",")[0];
        }

        if (isInvalid(ip)) {
            //X-Real-IP：nginx服务代理
            ip = request.getHeader("X-Real-IP");
        }

        if (isInvalid(ip)) {
            //Proxy-Client-IP：apache 服务代理
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (isInvalid(ip)) {
            //WL-Proxy-Client-IP：weblogic 服务代理
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (isInvalid(ip)) {
            //HTTP_CLIENT_IP：有些代理服务器
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        //还是不能获取到，最后再通过request.getRemoteAddr();获取
        if (isInvalid(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    private static boolean isInvalid(String ip) {
        return ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip);
    }

    public static String getIP(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String ip = headers.getFirst("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.indexOf(",") != -1) {
                ip = ip.split(",")[0];
            }
        }
        if (isInvalid(ip)) {
            ip = headers.getFirst("Proxy-Client-IP");
        }
        if (isInvalid(ip)) {
            ip = headers.getFirst("WL-Proxy-Client-IP");
        }
        if (isInvalid(ip)) {
            ip = headers.getFirst("HTTP_CLIENT_IP");
        }
        if (isInvalid(ip)) {
            ip = headers.getFirst("HTTP_X_FORWARDED_FOR");
        }
        if (isInvalid(ip)) {
            ip = headers.getFirst("X-Real-IP");
        }
        if (isInvalid(ip)) {
            ip = request.getRemoteAddress().getAddress().getHostAddress();
        }
        return ip;
    }
}
