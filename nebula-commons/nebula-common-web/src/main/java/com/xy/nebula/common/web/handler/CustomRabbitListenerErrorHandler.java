package com.xy.nebula.common.web.handler;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

/**
 * @author zangliulu
 * @Title:
 * @Package
 * @Description: mq 监听 异常处理
 * @date 2021/10/13 11:18
 */
@Slf4j
@Component
public class CustomRabbitListenerErrorHandler implements RabbitListenerErrorHandler {
    @Override
    public Object handleError(Message message, org.springframework.messaging.Message<?> message1, ListenerExecutionFailedException exception) throws Exception {
        ObjectInputStream ois = null;
        ByteArrayInputStream bis = null;
        try {
            log.error("CustomRabbitListenerErrorHandler " + exception.getMessage() + "|" + exception.getFailedMessage());
            bis = new ByteArrayInputStream(message.getBody());
            ois = new ObjectInputStream(bis);
            JSONObject context = (JSONObject)ois.readObject();
            log.warn("CustomRabbitListenerErrorHandler. context={}",context.toJSONString());
        } catch (Exception e) {
            throw e;
        } finally {
            if (bis != null) {
                bis.close();
            }
            if (ois != null) {
                ois.close();
            }
        }
        return null;
    }
}
