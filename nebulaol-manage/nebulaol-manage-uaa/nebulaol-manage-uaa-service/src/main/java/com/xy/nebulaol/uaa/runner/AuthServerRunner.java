package com.xy.nebulaol.uaa.runner;

import com.xy.nebulao.commons.utils.cipher.RsaKeyHelper;
import com.xy.nebulaol.common.domain.constant.RedisConstant;
import com.xy.nebulaol.uaa.configuration.KeyConfiguration;
import com.xy.sdx.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.security.KeyPair;

/**
 * @author zangliulu
 * @Title:
 * @Package
 * @Description: 初始化RAS公私key
 * @date 2021/7/9 16:06
 */
//@Configuration
public class AuthServerRunner implements CommandLineRunner {

    @Autowired
    private KeyConfiguration keyConfiguration;

    @Autowired
    private RedisService redisService;

    @Override
    public void run(String... args) throws Exception {
        if (redisService.hasKey(RedisConstant.nebulaol_ras.REDIS_USER_PRI_KEY)&&redisService.hasKey(RedisConstant.nebulaol_ras.REDIS_USER_PUB_KEY)) {
            keyConfiguration.setUserPriKey(RsaKeyHelper.toBytes(redisService.get(RedisConstant.nebulaol_ras.REDIS_USER_PRI_KEY).toString()));
            keyConfiguration.setUserPubKey(RsaKeyHelper.toBytes(redisService.get(RedisConstant.nebulaol_ras.REDIS_USER_PUB_KEY).toString()));
        } else {
            KeyPair keyPair = RsaKeyHelper.generateKeyPair(keyConfiguration.getUserSecret());
            byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
            byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
            keyConfiguration.setUserPriKey(publicKeyBytes);
            keyConfiguration.setUserPubKey(privateKeyBytes);
            redisService.set(RedisConstant.nebulaol_ras.REDIS_USER_PARI,keyPair);
            redisService.set(RedisConstant.nebulaol_ras.REDIS_USER_PRI_KEY, RsaKeyHelper.toHexString(publicKeyBytes));
            redisService.set(RedisConstant.nebulaol_ras.REDIS_USER_PUB_KEY, RsaKeyHelper.toHexString(privateKeyBytes));
        }
    }
}
