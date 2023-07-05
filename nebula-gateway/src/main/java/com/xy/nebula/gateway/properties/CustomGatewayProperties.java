package com.xy.nebula.gateway.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * description: LogIgnoreProperties
 * date: 2020-08-25 18:02
 * author: chenxd
 * version: 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "gateway")
public class CustomGatewayProperties {

    private Log log;
    private Sign sign;
    private Auth auth;

    @Data
    public static class Log {
        private boolean enable;
        private String[] ignoreUrls;
    }

    @Data
    public static class Sign {
        private boolean enable;
        private Map<String,String> key;
        private String[] ignoreUrls;
        private long timeout;
    }

    @Data
    public static class Auth {
        private boolean enable;
        private String secret;
        private String[] ignoreUrls;
    }
}
