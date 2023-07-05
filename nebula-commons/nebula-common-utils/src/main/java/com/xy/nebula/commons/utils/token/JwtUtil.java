package com.xy.nebula.commons.utils.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

/**
 * description: JwtUtil
 * date: 2020-09-09 17:12
 * author: chenxd
 * version: 1.0
 */
@Slf4j
public class JwtUtil {

    public static final String ISSUER = "SDX";

    /**
     * 生成token
     * @param secret 秘钥
     * @param expires 有效期 单位：秒
     * @param customData 自定义内容
     * @return
     */
    public static String generateToken(String secret, int expires, String customData) {
        Date now = new Date();
        Algorithm algorithm = Algorithm.HMAC256(secret);
        String token = JWT.create()
                .withIssuer(ISSUER)
                    .withIssuedAt(new Date()).withExpiresAt(DateUtils.addSeconds(now, expires))
                .withSubject(customData)
                .sign(algorithm);
        return token;
    }

    /**
     * 解析token
     * @param secret
     * @param token
     * @return
     * @throws InvalidClaimException
     * @throws SignatureVerificationException
     * @throws TokenExpiredException
     */
    public static String parseToken(String secret, String token) throws InvalidClaimException, SignatureVerificationException, TokenExpiredException {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build();
        DecodedJWT jwt = verifier.verify(token);
        String subject = jwt.getSubject();
        return subject;
    }

    /**
     * 校验token
     * @param secret
     * @param token
     * @return
     */
    public static boolean verifyToken(String secret, String token) {
        try {
            parseToken(secret, token);
            return true;
        } catch (InvalidClaimException e) {
            log.error("解析token失败:{}", token, e);
        } catch (SignatureVerificationException e) {
            log.error("解析token失败:{}", token, e);
        } catch (TokenExpiredException e) {
            log.error("token已失效:{}", token, e);
        }
        return false;
    }

    public static void main(String[] args) {
//        InvalidClaimException
//        SignatureVerificationException
//        TokenExpiredException

        String token = generateToken("sdxnetcafe", 60*60*4,"{\n" +
                "    \"code\": \"00000\",\n" +
                "    \"msg\": \"成功\",\n" +
                "    \"data\": {\n" +
                "        \"pageNum\": 1,\n" +
                "        \"pageSize\": 1,\n" +
                "        \"total\": 1,\n" +
                "        \"pages\": 1,\n" +
                "        \"list\": [\n" +
                "            {\n" +
                "                \"id\": 2312123213,\n" +
                "                \"name\": \"账户管理系统\",\n" +
                "                \"code\": \"sdx-auth\",\n" +
                "                \"description\": \"树呆熊账户管理系统\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}");
        System.out.println(token);

        System.out.println(parseToken("sdxnetcafe", token));

        System.out.println(verifyToken("sdxnetcafe", token));
    }
}
