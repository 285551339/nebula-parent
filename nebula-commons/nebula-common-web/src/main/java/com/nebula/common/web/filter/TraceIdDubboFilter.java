package com.nebula.common.web.filter;


import com.nebula.common.domain.constant.CommonConstant;
import com.nebula.common.domain.context.ThreadLocalContext;
import com.nebula.common.domain.vo.req.BaseAdminReq;
import com.nebula.common.domain.vo.req.BaseUserReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.slf4j.MDC;

/**
 * description: TraceIdFilter
 * date: 2020-09-11 17:41
 * author: chenxd
 * version: 1.0
 */
@Slf4j
@Activate(group = {CommonConstants.PROVIDER, CommonConstants.CONSUMER})
public class TraceIdDubboFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        RpcContext rpcContext = RpcContext.getContext();
        String traceId;
        String appCode;
        String ip;
        BaseUserReq user;
        BaseAdminReq admin;
        Long currentBrand;
        Long currentRole;
        //服务消费方
        if (rpcContext.isConsumerSide()) {
            traceId = ThreadLocalContext.getTraceId();
            appCode = ThreadLocalContext.getAppCode();
            ip = ThreadLocalContext.getIp();
            user = ThreadLocalContext.getUser();
            admin = ThreadLocalContext.getAdmin();
            currentBrand = ThreadLocalContext.getCurrentBrand();
            currentRole = ThreadLocalContext.getCurrentRole();
            if (StringUtils.isBlank(traceId)) {
                log.warn("traceId is null, please check request params");
            }
            rpcContext.setAttachment(CommonConstant.TRACEID, traceId)
                    .setAttachment(CommonConstant.APPCODE, appCode)
                    .setAttachment(CommonConstant.IP, ip)
                    .setAttachment(CommonConstant.USER, user)
                    .setAttachment(CommonConstant.ADMIN, admin)
                    .setAttachment(CommonConstant.CURRENT_BRAND, currentBrand)
                    .setAttachment(CommonConstant.CURRENT_ROLE, currentRole);
        }
        //服务提供方
        if (rpcContext.isProviderSide()) {
            //TODO 验证清除逻辑
            ThreadLocalContext.remove();
            MDC.clear();
            traceId = rpcContext.getAttachment(CommonConstant.TRACEID);
            appCode = rpcContext.getAttachment(CommonConstant.APPCODE);
            ip = rpcContext.getAttachment(CommonConstant.IP);
            user = (BaseUserReq) rpcContext.getObjectAttachment(CommonConstant.USER);
            admin = (BaseAdminReq) rpcContext.getObjectAttachment(CommonConstant.ADMIN);
            currentBrand = rpcContext.getObjectAttachment(CommonConstant.CURRENT_BRAND) == null ? null : Long.valueOf(String.valueOf(rpcContext.getObjectAttachment(CommonConstant.CURRENT_BRAND)));
            currentRole = rpcContext.getObjectAttachment(CommonConstant.CURRENT_ROLE) == null ? null : Long.valueOf(String.valueOf(rpcContext.getObjectAttachment(CommonConstant.CURRENT_ROLE)));
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
            if (user != null) {
                ThreadLocalContext.setUser(user);
            }
            if (admin != null) {
                ThreadLocalContext.setAdmin(admin);
            }
            if (currentBrand != null) {
                ThreadLocalContext.setCurrentBrand(currentBrand);
            }
            if (currentRole != null) {
                ThreadLocalContext.setCurrentRole(currentRole);
            }
        }
        Result result = invoker.invoke(invocation);
        return result;
    }
}
