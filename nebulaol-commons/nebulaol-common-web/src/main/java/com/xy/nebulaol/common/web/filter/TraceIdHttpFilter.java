package com.xy.nebulaol.common.web.filter;

import com.alibaba.fastjson.JSONObject;
import com.xy.nebulaol.common.domain.constant.CommonConstant;
import com.xy.nebulaol.common.domain.context.ThreadLocalContext;
import com.xy.nebulaol.common.domain.vo.req.BaseAdminReq;
import com.xy.nebulaol.common.domain.vo.req.BaseUserReq;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * description: TraceIdHttpFilter
 * date: 2020-09-14 13:36
 * author: chenxd
 * version: 1.0
 */
@WebFilter(urlPatterns = "/*")
public class TraceIdHttpFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String traceId = request.getHeader(CommonConstant.TRACEID);
        String appCode = request.getHeader(CommonConstant.APPCODE);
        String ip = request.getHeader(CommonConstant.IP);
        String userJson = StringUtils.isEmpty(request.getHeader(CommonConstant.USER)) ? null : URLDecoder.decode(request.getHeader(CommonConstant.USER),"UTF-8");
        String adminJson =  StringUtils.isEmpty(request.getHeader(CommonConstant.ADMIN)) ? null : URLDecoder.decode(request.getHeader(CommonConstant.ADMIN),"UTF-8");
        String currentBrand = request.getHeader(CommonConstant.CURRENT_BRAND);
        String currentRole = request.getHeader(CommonConstant.CURRENT_ROLE);
        if (StringUtils.isNotEmpty(traceId)) {
            MDC.put(CommonConstant.TRACEID, traceId);
            ThreadLocalContext.setTraceId(traceId);
        }
        if (StringUtils.isNotEmpty(appCode)) {
            ThreadLocalContext.setAppCode(appCode);
        }
        if (StringUtils.isNotEmpty(ip)) {
            ThreadLocalContext.setIp(ip);
        }
        if (StringUtils.isNotEmpty(userJson)) {
            //消费者信息
            BaseUserReq user = JSONObject.parseObject(userJson, BaseUserReq.class);
            ThreadLocalContext.setUser(user);
        }
        if (StringUtils.isNotEmpty(adminJson)) {
            //管理系统token
            BaseAdminReq admin = JSONObject.parseObject(adminJson, BaseAdminReq.class);
            ThreadLocalContext.setAdmin(admin);
        }
        if (StringUtils.isNotEmpty(currentBrand)) {
            ThreadLocalContext.setCurrentBrand(Long.parseLong(currentBrand));
        }
        if (StringUtils.isNotEmpty(currentRole)) {
            ThreadLocalContext.setCurrentRole(Long.parseLong(currentRole));
        }
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            ThreadLocalContext.remove();
            MDC.clear();
        }
    }

    @Override
    public void destroy() {

    }
}
