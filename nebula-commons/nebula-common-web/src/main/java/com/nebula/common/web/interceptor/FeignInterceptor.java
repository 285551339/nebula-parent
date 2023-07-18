package com.nebula.common.web.interceptor;

import com.nebula.commons.utils.RequestUtil;
import com.nebula.common.domain.constant.CommonConstant;
import com.nebula.common.domain.context.ThreadLocalContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;

/**
 * description: FeignInterceptor
 * date: 2020-09-09 10:32
 * author: chenxd
 * version: 1.0
 */
@Slf4j
@Configuration
public class FeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest request = RequestUtil.getHttpServletRequest();
        if (request != null) {
            requestTemplate.header(CommonConstant.TRACEID, request.getHeader(CommonConstant.TRACEID));
            requestTemplate.header(CommonConstant.USER, request.getHeader(CommonConstant.USER));
            requestTemplate.header(CommonConstant.ADMIN, request.getHeader(CommonConstant.ADMIN));
            requestTemplate.header(CommonConstant.IP, request.getHeader(CommonConstant.IP));
            requestTemplate.header(CommonConstant.CURRENT_BRAND, request.getHeader(CommonConstant.CURRENT_BRAND));
            requestTemplate.header(CommonConstant.CURRENT_ROLE, request.getHeader(CommonConstant.CURRENT_ROLE));
        } else {
            //防止异步fegin调用无法获取request问题
            requestTemplate.header(CommonConstant.TRACEID, ThreadLocalContext.getTraceId());
            //requestTemplate.header(CommonConstant.USER, JSON.toJSONString(ThreadLocalContext.getUser()));
            //requestTemplate.header(CommonConstant.ADMIN, JSON.toJSONString(ThreadLocalContext.getAdmin()));
            requestTemplate.header(CommonConstant.IP, ThreadLocalContext.getIp());
            Long currentBrand = ThreadLocalContext.getCurrentBrand();
            Long currentRole = ThreadLocalContext.getCurrentRole();
            requestTemplate.header(CommonConstant.CURRENT_BRAND, currentBrand == null ? null : Long.toString(currentBrand));
            requestTemplate.header(CommonConstant.CURRENT_ROLE, currentRole == null ? null : Long.toString(currentRole));
        }
    }

}
