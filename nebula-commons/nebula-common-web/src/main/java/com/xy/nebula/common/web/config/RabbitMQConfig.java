package com.xy.nebula.common.web.config;

import org.springframework.amqp.remoting.client.AmqpProxyFactoryBean;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description rabbitmq通用配置
 * @Author chenxudong
 * @Date 2021/4/28 11:53
 */
@Configuration
@ConditionalOnClass(value = AmqpProxyFactoryBean.class)
public class RabbitMQConfig {

    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}
