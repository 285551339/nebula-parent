package com.xy.nebulaol.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * @author zangliulu
 * @Title:
 * @Package
 * @Description: gateway是用的webflux 和javax包下filter会冲突  注解这里使用 EnableWebFluxSecurity
 * @date 2021/7/6 20:26
 */
@EnableWebFluxSecurity
@Configuration
public class WebSecurityConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    public SecurityWebFilterChain webFluxFilterChain(ServerHttpSecurity http) {
        http
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers("/**").permitAll()
                //option 请求默认放行
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .and()
                .formLogin()
        ;

        return http.build();
    }


    @Bean
    public TokenStore tokenStore(){
        return new RedisTokenStore(redisConnectionFactory);
    }
}
