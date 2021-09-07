package com.xy.nebulaol.uaa.config;

import com.xy.nebulao.commons.utils.cipher.RsaKeyHelper;
import com.xy.nebulaol.api.SysClientService;
import com.xy.nebulaol.api.SysUserService;
import com.xy.nebulaol.common.domain.constant.RedisConstant;
import com.xy.nebulaol.uaa.component.JwtTokenEnhancer;
import com.xy.nebulaol.uaa.configuration.KeyConfiguration;
import com.xy.nebulaol.uaa.handle.AuthExceptionHandle;
import com.xy.sdx.redis.service.RedisService;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter;
import org.springframework.security.oauth2.provider.implicit.ImplicitTokenGranter;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author zangliulu
 * @Title:
 * @Package
 * @Description:
 * @date 2021/6/10 15:53
 */
@Slf4j
@Configuration
@EnableAuthorizationServer
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SysUserService userService;
    @Autowired
    private SysClientService sysClientService;
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private RedisService redisService;
    @Autowired
    private JwtTokenEnhancer jwtTokenEnhancer;
    @Autowired
    private KeyConfiguration keyConfiguration;


    /**
     * 这个方法主要的作用用于控制token的端点等信息
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> delegates = new ArrayList<>();
        delegates.add(jwtTokenEnhancer);
        delegates.add(accessTokenConverter());
        //配置JWT的内容增强器
        enhancerChain.setTokenEnhancers(delegates);
        //使用密码模式需要配置
        endpoints.authenticationManager(authenticationManager)
                //指定token存储到redis
                .tokenStore(tokenStore)
                //refresh_token是否重复使用
                .reuseRefreshTokens(false)
                //刷新令牌授权包含对用户信息的检查
                .userDetailsService((UserDetailsService) userService)
                .tokenEnhancer(enhancerChain)
                //支持GET,POST请求
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
        endpoints.exceptionTranslator(new AuthExceptionHandle());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //允许表单认证
        security.allowFormAuthenticationForClients();
    }

    /**
     * 这个方法主要是用于校验注册的第三方客户端的信息，可以存储在数据库中，默认方式是存储在内存中，如下所示，注释掉的代码即为内存中存储的方式
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails((ClientDetailsService) sysClientService);
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() throws IOException, NoSuchAlgorithmException {
        KeyPair keyPair = getKeyPair();
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setKeyPair(keyPair);
        return jwtAccessTokenConverter;
    }

    public KeyPair getKeyPair() throws NoSuchAlgorithmException, IOException {
        KeyPair keyPair = RsaKeyHelper.generateKeyPair(keyConfiguration.getUserSecret());
        if (redisService.hasKey(RedisConstant.nebulaol_ras.REDIS_USER_PRI_KEY)&&redisService.hasKey(RedisConstant.nebulaol_ras.REDIS_USER_PUB_KEY)) {
            keyConfiguration.setUserPriKey(RsaKeyHelper.toBytes(redisService.get(RedisConstant.nebulaol_ras.REDIS_USER_PRI_KEY).toString()));
            keyConfiguration.setUserPubKey(RsaKeyHelper.toBytes(redisService.get(RedisConstant.nebulaol_ras.REDIS_USER_PUB_KEY).toString()));
        } else {
            byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
            byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
            keyConfiguration.setUserPriKey(publicKeyBytes);
            keyConfiguration.setUserPubKey(privateKeyBytes);
            redisService.set(RedisConstant.nebulaol_ras.REDIS_USER_PARI,keyPair);
            redisService.set(RedisConstant.nebulaol_ras.REDIS_USER_PRI_KEY, RsaKeyHelper.toHexString(publicKeyBytes));
            redisService.set(RedisConstant.nebulaol_ras.REDIS_USER_PUB_KEY, RsaKeyHelper.toHexString(privateKeyBytes));
        }
        return keyPair;
    }
}
