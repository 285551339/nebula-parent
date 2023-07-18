package com.nebula.common.web.config;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.nebula.common.web.handler.CustomBlockHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * description: SentinelBlockExceptionConfig
 * date: 2020-10-26 14:21
 * author: chenxd
 * version: 1.0
 */
@Configuration
public class SentinelBlockExceptionConfig {

    @Bean
    public BlockExceptionHandler blockExceptionHandler() {
        return new CustomBlockHandler();
    }

}
