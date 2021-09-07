package com.xy.nebulao.commons.utils.wechat;

/**
 * @program: sdx-parent
 * @description: 微信公众号
 * @author: fushilin
 * @create: 2020-12-15 15:32
 * @versions: 1.0
 **/
public class WechatConstant {

    /**
     * placeholder of API URL
     */
    public final static String PARAM_PLACEHOLDER_APPID = "APPID";
    public final static String PARAM_PLACEHOLDER_OPENID = "OPENID";
    public final static String PARAM_PLACEHOLDER_APPSECRET = "APPSECRET";
    public final static String PARAM_PLACEHOLDER_SECRET = "SECRET";
    public final static String PARAM_PLACEHOLDER_JSCODE = "JSCODE";
    public final static String PARAM_PLACEHOLDER_CLIENT_CREDENTIAL = "CLIENT_CREDENTIAL";

    public final static String PARAM_PLACEHOLDER_ACCESS_TOKEN = "ACCESS_TOKEN";

    public final static String WECHAT_VISIT_URL = "/api/ops/wechat/server/";

    public static final String CONNECTOR = ":";


    /**
     * Wechat related cache name
     */
    public final static String ACCESS_TOKEN_CACHE = "accessTokenCache";
    public final static String WECHAT_OAUTH2_URL_STATE = "wechat_oauth2_url_state";

    public final static String SESSION_KEY_CACHE = "sessionKey";


    public final static String ACCESS_TOKEN_LOCK_KEY = "access_token_lock_key";

    public final static String JSAPI_TICKET_CACHE = "jsapi_ticket_cache";

    public final static String JSAPI_TICKET_LOCK_KEY = "jsapi_ticket_lock_key";

    public final static String WECHAT_JSAPI_GET_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";
}
