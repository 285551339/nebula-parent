package com.xy.nebulaol.uaa.mq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zangliulu
 * @Title:
 * @Package
 * @Description: 消息发送
 * @date 2021/10/13 14:16
 */
@RestController
public class Mqsend {

    @Autowired
    RabbitTemplate rabbitTemplate;



}
