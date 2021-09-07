package com.xy.nebulaol.canal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author zangliulu
 * @Title: 启动类
 * @Package
 * @Description:
 * @date 2021/7/27 11:42
 */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.xy.nebulaol.canal", "com.xy.nebulaol.common.web"})
@MapperScan(basePackages = {"com.xy.nebulaol.canal.mapper"})
@EnableTransactionManagement
@ServletComponentScan(basePackages = {"com.xy.nebulaol.common.web"})
public class CanalClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(CanalClientApplication.class);
    }
}
