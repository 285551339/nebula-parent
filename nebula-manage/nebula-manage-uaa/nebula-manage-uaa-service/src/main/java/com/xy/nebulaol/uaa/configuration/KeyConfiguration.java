package com.xy.nebulaol.uaa.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author zangliulu
 * @Title:
 * @Package
 * @Description: JWT配置key
 * @date 2021/7/9 15:58
 */
@Configuration
@Data
public class KeyConfiguration {

    @Value("${jwt.user.secret}")
    private String userSecret;
    private byte[] userPubKey;
    private byte[] userPriKey;
}
