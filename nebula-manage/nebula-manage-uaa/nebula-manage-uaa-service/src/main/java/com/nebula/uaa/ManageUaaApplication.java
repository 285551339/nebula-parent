package com.nebula.uaa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * description: ManageUaaApplication
 * date: 2020-09-05 16:40
 * author: chenxd
 */
@SpringBootApplication(scanBasePackages = {"com.nebula.uaa", "com.nebula.common", "com.nebula.redis"})
@EnableFeignClients(basePackages = {"com.nebula.**.feign"})
@ServletComponentScan(basePackages = {"com.nebula.common.web"})
public class ManageUaaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManageUaaApplication.class, args);
    }
}
