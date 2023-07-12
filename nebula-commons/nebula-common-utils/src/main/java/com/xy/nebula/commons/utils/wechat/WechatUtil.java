package com.xy.nebula.commons.utils.wechat;


import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.xy.nebula.commons.utils.bean.SpringBeanUtil;
import com.xy.nebula.commons.utils.network.HttpClientUtil;
import com.xy.nebula.commons.utils.random.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 微信工具类
 * created by wensheng on 14-11-16.
 */
@Slf4j
public class WechatUtil {

    // 获取access_token的接口地址（GET）
    private final static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    // 菜单创建（POST）
    public final static String MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    public final static String MENU_QUERY_URL = "https://api.weixin.qq.com/cgi-bin/get_current_selfmenu_info?access_token=ACCESS_TOKEN";

    //获取oauth2的AccessToken
    public final static String OAUTH2_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

    //小程序获取用户信息
    public final static String APPLET_PAID_UNIONID_URL = "https://api.weixin.qq.com/wxa/getpaidunionid?access_token=ACCESS_TOKEN&openid=OPENID";
    public final static String APPLET_CODE_SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";

    //获取用户信息
    public final static String SNS_USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s";

    //微信OAuth2,获取code
    public final static String WECHAT_OAUTH2_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect";

    //获取用户基本信息
    public final static String WECHAT_GET_USER_INFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN";

    //获取临时素材
    public final static String MEDIA_URL = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=%s&media_id=%s";

    //发送模板消息
    public static String WECHAT_TM_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s";

    // 网页授权 不弹出授权页面，直接跳转，只能获取用户openid
    public static String OAUTH2_SCOPE_BASE = "snsapi_base";

    public static final String AES = "AES";
    public static final String AES_CBC_PADDING = "AES/CBC/PKCS7Padding";


    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        try {
            KeyGenerator.getInstance(AES).init(128);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 获取微信用户信息
     * @param accessToken
     * @param openid
     * @return
     */
    public static WechatUser getWechatUserInfo(String accessToken, String openid){
        String requestUrl = String.format(WECHAT_GET_USER_INFO_URL, accessToken, openid);
        log.info("远程请求用户信息, Url: {}", requestUrl);
        String result = HttpClientUtil.get(requestUrl);
        log.info("远程请求用户信息, Result: {}", result);
        WechatUser wechatUser = JSONObject.parseObject(result, WechatUser.class);
        return wechatUser;
    }

    /**
     * @Description: 获取微信公众号/小程序token
     * @Param: [appid, appsecret,grantType]
     * @throws: com.xy.sdx.common.utils.wechat.AccessToken
     * @author: fushilin
     * @Date: 2020/12/29 11:28
     * @versions: 1.0
     */
    public static String getRemoteAccessToken(String appId, String appSecret, String grantType) {
        RedissonClient redissonClient = SpringBeanUtil.getBean(RedissonClient.class);
        RedisService redisService = SpringBeanUtil.getBean(RedisService.class);
        RLock weChatTokenLock = redissonClient.getLock(WechatConstant.ACCESS_TOKEN_LOCK_KEY + WechatConstant.CONNECTOR + appId);
        String tokens = getCacheAccessToken(appId, redisService);

        if (StringUtils.isNotBlank(tokens)) {
            return tokens;
        }

        try {
            tokens = getCacheAccessToken(appId, redisService);
            if (StringUtils.isNotBlank(tokens)) {
                return tokens;
            }
            boolean b = weChatTokenLock.tryLock();
            if (b) {
                AccessToken accessToken = getAccessToken(appId, appSecret, grantType);
                String token = accessToken != null ? accessToken.getAccess_token() : null;
                if (StringUtils.isNotBlank(token)) {
                    redisService.set(WechatConstant.ACCESS_TOKEN_CACHE + WechatConstant.CONNECTOR + appId, token, (int) (accessToken.getExpires_in() * 0.9));
                    return token;
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            weChatTokenLock.unlock();
        }
        return null;
    }

    /**
     * @Description: redis读取数据
     * @Param: [appId]
     * @return: java.lang.String
     * @Author: fushilin
     * @Date: 2020/12/15 17:21
     */
    private static String getCacheAccessToken(String appId, RedisService redisService) {
        Object res = redisService.get(WechatConstant.ACCESS_TOKEN_CACHE + WechatConstant.CONNECTOR + appId);
        return res == null ? null : res.toString();
    }

    private static AccessToken getAccessToken(String appid, String appsecret, String grantType) {
        String requestUrl = null;
        if (StrUtil.isNotBlank(grantType)) {
            //小程序请求地址
            requestUrl = ACCESS_TOKEN_URL.replace(WechatConstant.PARAM_PLACEHOLDER_CLIENT_CREDENTIAL, grantType).replace(WechatConstant.PARAM_PLACEHOLDER_APPID, appid).replace(WechatConstant.PARAM_PLACEHOLDER_APPSECRET, appsecret);
        } else {
            //公众号请求地址
            requestUrl = ACCESS_TOKEN_URL.replace(WechatConstant.PARAM_PLACEHOLDER_APPID, appid).replace(WechatConstant.PARAM_PLACEHOLDER_APPSECRET, appsecret);
        }
        log.info("远程请求AccessToken, Url: {}", requestUrl);
        String result = HttpClientUtil.get(requestUrl);
        log.info("远程请求AccessToken, Result: {}", result);
        if (result == null) {
            return null;
        }
        AccessToken accessToken = JSONObject.parseObject(result, AccessToken.class);
        if (accessToken.getErrcode() != null) {
            return null;
        }
        return accessToken;
    }


    /**
     * @Description: 创建菜单
     * @Param: [menuJsonStr, accessToken]
     * @throws: int
     * @author: fushilin
     * @Date: 2020/12/29 11:29
     * @versions: 1.0
     */
    public static boolean createMenu(String menuJsonStr, String accessToken) {
        // 拼装创建菜单的url
        String requestUrl = MENU_CREATE_URL.replace(WechatConstant.PARAM_PLACEHOLDER_ACCESS_TOKEN, accessToken);
        Map<String, Object> headerMap = new HashMap<>();
        Map<String, String> params = JSONObject.parseObject(menuJsonStr, Map.class);
        headerMap.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8);
        log.info("远程请求创建公众号菜单accessToken:{},menuJsonStr:{}", accessToken, menuJsonStr);
        String result = HttpClientUtil.post(requestUrl, headerMap, params);
        log.info("远程请求创建公众号菜单 result:{}", result);
        if (result == null) {
            return false;
        }
        WechatResult wechatResult = JSONObject.parseObject(result, WechatResult.class);
        return wechatResult.getErrcode() == 0;
    }


    /**
     * @Description: 获取微信公众号菜单
     * @Param: [accessToken]
     * @throws: java.lang.String
     * @author: fushilin
     * @Date: 2020/12/29 11:29
     * @versions: 1.0
     */
    public static String getMenu(String accessToken) {
        // 拼装创建菜单的url
        String requestUrl = MENU_QUERY_URL.replace(WechatConstant.PARAM_PLACEHOLDER_ACCESS_TOKEN, accessToken);
        log.info("远程获取微信公众号菜单accessToken:{},url{}", accessToken, requestUrl);
        String result = HttpClientUtil.get(requestUrl);
        log.info("获取微信公众号菜单:result{}", result);
        return result;
    }

    /**
     * 获取oauth2授权access_token
     *
     * @param appid
     * @param appsecret
     * @param code
     * @return
     */
    public static OAuth2AccessToken getOauth2AccessToken(String appid, String appsecret, String code) {
        String requestUrl = String.format(OAUTH2_ACCESS_TOKEN_URL, appid, appsecret, code);
        log.info("获取oauth2授权access_token, Url:{}", requestUrl);
        String result = HttpClientUtil.get(requestUrl);
        log.info("获取oauth2授权access_token, Result:{}", result);
        if (StringUtils.isBlank(result)) {
            return null;
        }
        OAuth2AccessToken auth2AccessToken = JSONObject.parseObject(result, OAuth2AccessToken.class);
        if (auth2AccessToken.getErrcode() != null) {
            return null;
        }
        return auth2AccessToken;
    }

    /**
     * 获取用户信息
     * errorCode有值为错误
     *
     * @param accessToken
     * @param openId
     * @return
     * @throws IOException
     */
    public static SnsUserInfo getSnsUserInfo(String accessToken, String openId) {
        log.info("获取微信用户信息 snsUserinfo accessToken: {} openid: {} ", accessToken, openId);
        String requestUrl = String.format(SNS_USERINFO_URL, accessToken, openId);
        RestTemplate restTemplate = new RestTemplate();
        log.info("SnsUserInfo 拼接的后的requestUrl为:" + requestUrl);
        Resource res = restTemplate.getForObject(requestUrl, Resource.class);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        InputStream inputStream = null;
        try {
            inputStream = res.getInputStream();
            int i;
            while ((i = inputStream.read()) != -1) {
                bos.write(i);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
        String str = bos.toString();
        log.info("获取到的SnsUserInfo返回结果为： " + str);
        SnsUserInfo snsUserInfo = JSONObject.parseObject(str, SnsUserInfo.class);
        log.info("获取到的snsUserInfo为： " + snsUserInfo);
        if (snsUserInfo.getErrcode() == null) {
            return snsUserInfo;
        } else {
            return null;
        }
    }

    /**
     * 获取微信授权url
     * @param url
     * @param appId
     * @return
     * @throws Exception
     */
    public static String getWechatOauthUrl(String url, String appId) {
        try {
            url = URLEncoder.encode(url,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        }
        long state = IdUtil.nextId();
        String returnUrl = String.format(WECHAT_OAUTH2_URL, appId, url, OAUTH2_SCOPE_BASE, state);
        return returnUrl;
    }

    /**
     * 小程序用户登录：
     * errcode:值	说明
     * -1	系统繁忙，此时请开发者稍候再试
     * 0	请求成功
     * 40029	code 无效
     * 45011	频率限制，每个用户每分钟100次
     *
     * @param appId
     * @param appSecret
     * @param jsCode
     * @return
     */
    public static Code2Session getAppletCode2Session(String appId, String appSecret, String jsCode) {
        RedisService redisService = SpringBeanUtil.getBean(RedisService.class);
        String requestUrl = APPLET_CODE_SESSION_URL.replace(WechatConstant.PARAM_PLACEHOLDER_APPID, appId).replace(WechatConstant.PARAM_PLACEHOLDER_SECRET, appSecret).replace(WechatConstant.PARAM_PLACEHOLDER_JSCODE, jsCode);
        log.info("获取小程序登录, Url:{}", requestUrl);
        String result = HttpClientUtil.get(requestUrl);
        log.info("获取小程序登录, Result:{}", result);
        if (result == null) {
            return null;
        }
        Code2Session code2Session = JSONObject.parseObject(result, Code2Session.class);
        if (code2Session.getErrcode() != null) {
            return null;
        }
        if (StringUtils.isNotBlank(code2Session.getSession_key())) {
            redisService.set(WechatConstant.SESSION_KEY_CACHE + WechatConstant.CONNECTOR + code2Session.getOpenid(), code2Session.getSession_key());
        }
        return code2Session;
    }

    /**
     * @Description: redis读取数据 用户的session_Key
     * @Param: [appId]
     * @return: java.lang.String
     * @Author: fushilin
     * @Date: 2020/12/15 17:21
     */
    public static String getCacheSessionKey(String openId) {
        RedisService redisService = SpringBeanUtil.getBean(RedisService.class);
        Object sessionKey = redisService.get(WechatConstant.SESSION_KEY_CACHE + WechatConstant.CONNECTOR + openId);
        return sessionKey == null ? null : sessionKey.toString();
    }

    /**
     * 查询jsapiTicket
     *
     * @param accessToken
     * @return
     */
    public static String getJsapiTicket(String accessToken, String appId) {
        // 查询redis
        RedisService redisService = SpringBeanUtil.getBean(RedisService.class);
        String ticket = (String) redisService.get(WechatConstant.JSAPI_TICKET_CACHE + WechatConstant.CONNECTOR + appId);

        if (StringUtils.isBlank(ticket)) {
            RedissonClient redissonClient = SpringBeanUtil.getBean(RedissonClient.class);
            RLock weChatTokenLock = redissonClient.getLock(WechatConstant.JSAPI_TICKET_LOCK_KEY + WechatConstant.CONNECTOR + appId);
            try {
                boolean b = weChatTokenLock.tryLock();
                if (b) {
                    ticket = (String) redisService.get(WechatConstant.JSAPI_TICKET_CACHE + ":" + appId);
                    if (StringUtils.isBlank(ticket)) {
                        // 查询数据
                        log.info("缓存中没有appid:" + appId + "的JsApiTicket");
                        String requestUrl = String.format(WechatConstant.WECHAT_JSAPI_GET_TICKET_URL, accessToken);
                        JsApiTicket jsApiTicket = JSONObject.parseObject(requestUrl, JsApiTicket.class);
                        ticket = jsApiTicket.getTicket();
                        log.info("获取到JsApiTicket为： " + ticket);
                        if (StringUtils.isNotBlank(ticket)) {
                            // 微信ticket有效期为7200s
                            redisService.set(WechatConstant.JSAPI_TICKET_CACHE + WechatConstant.CONNECTOR + appId, ticket, (long) (jsApiTicket.getExpires_in() * 0.9));
                        }
                        return ticket;
                    } else {
                        return ticket;
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            } finally {
                weChatTokenLock.unlock();
            }
            // 查询数据
            log.info("缓存中没有appid:" + appId + "的JsApiTicket");
            String requestUrl = String.format(WechatConstant.WECHAT_JSAPI_GET_TICKET_URL, accessToken);
            RestTemplate restTemplate = new RestTemplate();
            JsApiTicket jsApiTicket = restTemplate.getForObject(requestUrl, JsApiTicket.class);
            ticket = jsApiTicket.getTicket();
            log.info("获取到JsApiTicket为： " + ticket);
            if (StringUtils.isNotBlank(ticket)) {
                // 微信ticket有效期为7200s
                redisService.set(WechatConstant.JSAPI_TICKET_CACHE + ":" + appId, ticket, (long) (jsApiTicket.getExpires_in() * 0.9));
            }
            return ticket;
        } else {
            return ticket;
        }
    }

    /**
     * 获取微信小程序用户信息：auth.getPaidUnionId
     * errcode :
     * 值	说明
     * -1	系统繁忙，此时请开发者稍候再试
     * 0	请求成功
     * 40003	openid 错误
     * 89002	没有绑定开放平台帐号
     * 89300	订单无效
     *
     * @param accessToken
     * @param openId
     * @return
     */
    public static String getAppletPaidUnionId(String accessToken, String openId) {
        String requestUrl = APPLET_PAID_UNIONID_URL.replace(WechatConstant.PARAM_PLACEHOLDER_ACCESS_TOKEN, accessToken).replace(WechatConstant.PARAM_PLACEHOLDER_OPENID, openId);
        log.info("远程获取微信小程序获取用户信息, accessToken:{},openId{},url{}", accessToken, openId, requestUrl);
        String result = HttpClientUtil.get(requestUrl);
        log.info("获取微信小程序获取用户信息, Result:{}", result);
        if (result == null) {
            return null;
        }
        PaidUnionId paidUnionId = JSONObject.parseObject(result, PaidUnionId.class);
        if (paidUnionId.getErrcode() != 0) {
            return null;
        }
        return paidUnionId.getUnionid();
    }


    /**
     * 开放数据校验与解密 <br/>
     * 对称解密使用的算法为 AES-128-CBC，数据采用PKCS#7填充<br/>
     * 对称解密的目标密文:encrypted=Base64_Decode(encryptData)<br/>
     * 对称解密秘钥:key = Base64_Decode(session_key),aeskey是16字节<br/>
     * 对称解密算法初始向量:iv = Base64_Decode(iv),同样是16字节<br/>
     *
     * @param encrypted   目标密文
     * @param session_key 会话ID
     * @param iv          加密算法的初始向量
     * @see 'https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/signature.html
     */
    public static String wxDecrypt(String encrypted, String session_key, String iv) {
        String result = null;
        byte[] encrypted64 = Base64.decodeBase64(encrypted);
        byte[] key64 = Base64.decodeBase64(session_key);
        byte[] iv64 = Base64.decodeBase64(iv);
        try {
            result = new String(decrypt(encrypted64, key64, generateIV(iv64)), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * description: 生成iv
     *
     * @param iv
     * @return zangliulu
     * @throws Exception
     * @version v1.0
     * @author w
     * @date 2021年4月9日 下午3:50:35
     */
    private static AlgorithmParameters generateIV(byte[] iv) throws Exception {
        // iv 为一个 16 字节的数组，这里采用和 iOS 端一样的构造方法，数据全为0
        // Arrays.fill(iv, (byte) 0x00);
        AlgorithmParameters params = AlgorithmParameters.getInstance(AES);
        params.init(new IvParameterSpec(iv));
        return params;
    }

    /**
     * description: 执行解密操作
     *
     * @param encryptedData 加密后的字符串
     * @param keyBytes      密钥key
     * @param iv            便宜向量iv
     * @return byte[]
     * @throws Exception
     * @version v1.0
     * @author zangliulu
     * @date 2021年4月9日 下午3:50:35
     */
    private static byte[] decrypt(byte[] encryptedData, byte[] keyBytes, AlgorithmParameters iv) throws Exception {
        Key key = new SecretKeySpec(keyBytes, AES);
        Cipher cipher = Cipher.getInstance(AES_CBC_PADDING);
        // 设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        return cipher.doFinal(encryptedData);
    }

    /**
     * JS-SDK权限验证签名
     *
     * @param jsapi_ticket
     * @param url
     * @return
     */
    public static Map<String, String> getJssdkSignature(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = createNonceStr();
        String timestamp = createTimestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;
        log.info(string1);

        //将拼接的字符串进行sha1加密
        String tmpStr = null;
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHexStr(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            log.error("error", e);
        } catch (UnsupportedEncodingException e) {
            log.error("error", e);
        }

        ret.put("url", url);
        ret.put("jsapiTicket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        return ret;
    }

    /**
     * 获取媒体信息
     * @param accessToken
     * @param mediaId
     * @return
     */
    public static MediaInfo getMedia(String accessToken, String mediaId){
        String requestUrl = String.format(MEDIA_URL, accessToken, mediaId);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        ResponseEntity<Resource> result = restTemplate.getForEntity(requestUrl,Resource.class);
        MediaInfo mediaInfo = new MediaInfo();
        mediaInfo.setFileName(result.getHeaders().getContentDisposition().getFilename());
        try {
            BASE64Encoder base64Encoder = new BASE64Encoder();
            byte [] bytes = new byte[result.getBody().getInputStream().available()];
            result.getBody().getInputStream().read(bytes);
            mediaInfo.setInputStreamBase64(base64Encoder.encode(bytes));
            return mediaInfo;
        } catch (Exception e) {
            log.error("获取媒体信息失败，mediaId：{}", mediaId);
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 发送模板消息
     * @param accessToken
     * @param templateData
     */
    public static void sendTemplateMessage(String accessToken, TemplateData templateData){
        String requestUrl = String.format(WECHAT_TM_SEND_URL, accessToken);
        String result = HttpClientUtil.postJson(requestUrl, templateData);
        log.info("send template result:{}, openId:{}", result, templateData.getTouser());
        WechatResult wechatResult = JSONObject.parseObject(result, WechatResult.class);
        if (wechatResult.getErrcode() != 0) {
            log.error("发送模板消息失败 errcode:{},errmsg:{},openId:{}", wechatResult.getErrcode(), wechatResult.getErrmsg(), templateData.getTouser());
        }
    }

    private static String createNonceStr() {
        return UUID.randomUUID().toString();
    }

    /**
     * @return
     */
    private static String createTimestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param hash
     * @return
     */
    private static String byteToHexStr(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
}
