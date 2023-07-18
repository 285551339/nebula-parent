package com.nebula.common.domain.constant;

/**
 * description: RedisConstant
 * date: 2020-09-07 08:27
 * author: chenxd
 * version: 1.0
 */
public class RedisConstant {

    /**
     * redis key连接符
     */
    public static final String CONNECTOR = ":";
    /**
     * redis key 自动生成，慎用
     */
    public static final String REDIS_KEY_GENERATOR = "wiselyKeyGenerator";

    public static class nebulaol_uaa {
        public static final String ADMIN_USER_ACCESS_TOKEN = "admin:user:access:token";
        public static final String ADMIN_USER_REFRESH_ACCESS_TOKEN = "admin:user:refresh:access:token:";
        public static final String APP_USER_ACCESS_TOKEN = "app:user:access:token";
        public static final String APP_USER_REFRESH_ACCESS_TOKEN = "app:user:refresh:access:token:";
    }

    public static class nebulaol_ras{
        public static final String REDIS_USER_PARI = "USER:AUTH:JWT:PARI";
        public static final String REDIS_USER_PRI_KEY = "USER:AUTH:JWT:PRI";
        public static final String REDIS_USER_PUB_KEY = "USER:AUTH:JWT:PUB";
   }
    public static class XY_APPLET_HOTEL {
        public static final String ONLINE_USER_HOTEL = "online:user:hotel:";
        public static final String TOKEN_USER_HOTEL = "token:user:hotel:";
        public static final String TOKEN_REFRESH_USER_HOTEL = "token:refresh:user:hotel:";
    }

}
