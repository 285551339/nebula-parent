package com.xy.nebulaol.uaa.mq;

import com.xy.nebulaol.uaa.component.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import static org.springframework.amqp.core.ExchangeTypes.DIRECT;

/**
 * @author zangliulu
 * @Title:
 * @Package
 * @Description: 消息处理
 * @date 2021/10/13 14:18
 */
@Slf4j
public class NotifyProcessor {


    @RabbitListener(bindings = {@QueueBinding(value = @Queue(value = CommonConstants.PUBWIN_NOTIFY_MEMBER_CHECKIN),exchange = @Exchange(value = DIRECT),key = CommonConstants.PUBWIN_NOTIFY_MEMBER_CHECKIN)},errorHandler = "customRabbitListenerErrorHandler")
    public void processsorMsg(String msg){
        log.info(msg);
    }
}
