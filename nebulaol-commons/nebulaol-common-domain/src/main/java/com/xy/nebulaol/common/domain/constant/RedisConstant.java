package com.xy.nebulaol.common.domain.constant;

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
        public static final String ACCESS = "access:";
        public static final String AUTH_TO_ACCESS = "auth_to_access:";
        public static final String AUTH = "auth:";
        public static final String REFRESH_AUTH = "refresh_auth:";
        public static final String ACCESS_TO_REFRESH = "access_to_refresh:";
        public static final String REFRESH = "refresh:";
        public static final String REFRESH_TO_ACCESS = "refresh_to_access:";
        public static final String CLIENT_ID_TO_ACCESS = "client_id_to_access:";
        public static final String UNAME_TO_ACCESS = "uname_to_access:";
    }

    public static class nebulaol_ras{
        public static final String REDIS_USER_PARI = "USER:AUTH:JWT:PARI";
        public static final String REDIS_USER_PRI_KEY = "USER:AUTH:JWT:PRI";
        public static final String REDIS_USER_PUB_KEY = "USER:AUTH:JWT:PUB";
   }

}
