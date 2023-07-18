package com.nebula.common.web.config;


import com.nebula.common.domain.constant.CommonConstant;
import com.nebula.common.domain.context.ThreadLocalContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.amqp.remoting.client.AmqpProxyFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * description: RabbitListenerAop
 * date: 2021-05-04 18:23
 * author: chenxd
 * version: 1.0
 */
@Component
@Aspect
@ConditionalOnClass(value = AmqpProxyFactoryBean.class)
public class RabbitListenerAop {

    //声明切入点
    @Pointcut("@annotation(org.springframework.amqp.rabbit.annotation.RabbitListener)")
    public void pointCut(){

    }

    //声明前置通知
    @Before("pointCut()")
    public void before(JoinPoint point) {
        Map<String, Object> params = getNameAndValue(point);
        Object traceIdObj = params.get(CommonConstant.TRACEID);
        String traceId = traceIdObj == null ? null : traceIdObj.toString();
        MDC.put(CommonConstant.TRACEID, traceId);
        ThreadLocalContext.setTraceId(traceId);
    }

    //声明最终通知
    @After("pointCut()")
    public void after() {
        MDC.clear();
        ThreadLocalContext.remove();
    }

    Map<String, Object> getNameAndValue(JoinPoint joinPoint) {
        Map<String, Object> param = new HashMap<>();
        Object[] paramValues = joinPoint.getArgs();
        String[] paramNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames();
        for (int i = 0; i < paramNames.length; i++) {
            param.put(paramNames[i], paramValues[i]);
        }
        return param;
    }
}
