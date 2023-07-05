package com.xy.nebula.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author zangliulu
 * @Title: 用户启动类
 * @Package
 * @Description:
 * @date 2021/6/21 20:25
 */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.xy.nebula.user", "com.xy.nebula.common.web"})
@MapperScan(basePackages = {"com.xy.nebula.user.mapper"})
@EnableTransactionManagement
@ServletComponentScan(basePackages = {"com.xy.nebula.common.web"})
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
