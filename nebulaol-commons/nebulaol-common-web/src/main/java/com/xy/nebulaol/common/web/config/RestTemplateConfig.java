package com.xy.nebulaol.common.web.config;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * description: RestTemplateConfig
 * date: 2020-09-09 08:06
 * author: chenxd
 * version: 1.0
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    @SentinelRestTemplate
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
