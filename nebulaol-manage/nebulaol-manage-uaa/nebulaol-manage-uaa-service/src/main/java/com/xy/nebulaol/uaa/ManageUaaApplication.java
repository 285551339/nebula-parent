package com.xy.nebulaol.uaa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * description: ManageUaaApplication
 * date: 2020-09-05 16:40
 * author: chenxd
 */
@SpringBootApplication(scanBasePackages = {"com.xy.nebulaol.uaa", "com.xy.nebulaol.common", "com.xy.sdx.redis"})
@EnableFeignClients(basePackages = {"com.xy.nebulaol.**.feign"})
@ServletComponentScan(basePackages = {"com.xy.nebulaol.common.web"})
public class ManageUaaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManageUaaApplication.class, args);
    }
}
