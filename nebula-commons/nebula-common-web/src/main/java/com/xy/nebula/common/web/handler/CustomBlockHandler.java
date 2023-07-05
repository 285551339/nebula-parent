package com.xy.nebula.common.web.handler;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.alibaba.fastjson.JSONObject;
import com.xy.nebula.common.domain.vo.resp.BaseResponse;
import com.xy.nebula.common.domain.vo.resp.ErrorType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * description: 自定义URL限流返回
 * date: 2020-10-24 14:21
 * author: chenxd
 * version: 1.0
 */
@Slf4j
public class CustomBlockHandler implements BlockExceptionHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {
        //限流
        if (e instanceof FlowException) {
            log.error("限流", e);
        //降级
        } else if (e instanceof DegradeException) {
           log.error("降级", e);
        //热点参数限流
        } else if (e instanceof ParamFlowException) {
            log.error("热点参数限流", e);
        //授权规则限制
        } else if (e instanceof AuthorityException) {
            log.error("授权不通过", e);
        //系统规则限制
        } else if (e instanceof SystemBlockException) {
            log.error("系统规则不满足", e);
        }
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        PrintWriter out = response.getWriter();
        out.print(JSONObject.toJSONString(BaseResponse.error(ErrorType.ERROR_SYSTEM_FLOW_LIMITING)));
        out.flush();
        out.close();
    }

}
