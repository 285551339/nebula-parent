package com.xy.nebulaol.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author zangliulu
 * @Title: gateway启动类
 * @Package
 * @Description:
 * @date 2021/6/24 13:43
 */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.xy.nebulaol.gateway", "com.xy.sdx.redis"})
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class);
    }
}
