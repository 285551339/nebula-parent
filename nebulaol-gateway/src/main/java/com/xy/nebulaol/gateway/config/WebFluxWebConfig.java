package com.xy.nebulaol.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author zangliulu
 * @Title:
 * @Package
 * @Description:
 * @date 2021/8/20 16:37
 */
@Configuration
@EnableWebFlux
public class WebFluxWebConfig implements WebFluxConfigurer {

    @Bean
    public WebClient getWebClientBuilder(){
        return WebClient.builder()
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(16 * 1024 * 1024))
                .build();
    }
}
