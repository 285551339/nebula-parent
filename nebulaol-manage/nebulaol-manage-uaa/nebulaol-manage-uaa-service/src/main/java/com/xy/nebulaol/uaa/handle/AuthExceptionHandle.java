package com.xy.nebulaol.uaa.handle;

import com.xy.nebulaol.common.domain.enumeration.OAuth2ErrorEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidScopeException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.common.exceptions.UnsupportedGrantTypeException;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author zangliulu
 * @Title: auth异常处理类
 * @Package
 * @Description:
 * @date 2021/6/23 11:31
 */
@RestControllerAdvice(basePackages = "com.xy.nebulaol.uaa.controller")
@ResponseBody
@Log4j2
public class AuthExceptionHandle implements WebResponseExceptionTranslator<OAuth2Exception> {

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        CustomOauthTokenException body = this.handleOAuth2Exception(e);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    private CustomOauthTokenException handleOAuth2Exception(Exception e) {
        if (e instanceof BadCredentialsException) {
            return new CustomOauthTokenException(OAuth2ErrorEnum.BAD_CREDENTIALS.getErrorCode(), OAuth2ErrorEnum.BAD_CREDENTIALS.getErrorMsg());
        }
        if (e instanceof LockedException) {
            return new CustomOauthTokenException(OAuth2ErrorEnum.ACCOUNT_DISABLE.getErrorCode(), OAuth2ErrorEnum.ACCOUNT_DISABLE.getErrorMsg());
        }
        if (e instanceof AccountExpiredException) {
            return new CustomOauthTokenException(OAuth2ErrorEnum.ACCOUNT_EXPIRED.getErrorCode(), OAuth2ErrorEnum.ACCOUNT_EXPIRED.getErrorMsg());
        }
        if (e instanceof CredentialsExpiredException) {
            return new CustomOauthTokenException(OAuth2ErrorEnum.CREDENTIALS_EXPIRED.getErrorCode(), OAuth2ErrorEnum.CREDENTIALS_EXPIRED.getErrorMsg());
        }
        if (e instanceof InvalidGrantException) {
            // 刷新token认证时
            String message = e.getMessage();
            if (message != null && message.contains("Invalid refresh token")) {
                return new CustomOauthTokenException(OAuth2ErrorEnum.REFRESH_CREDENTIALS_INVALID.getErrorCode(), OAuth2ErrorEnum.REFRESH_CREDENTIALS_INVALID.getErrorMsg());
            }
            // 授权码认证时
            if (message != null && message.contains("Invalid authorization code")) {
                return new CustomOauthTokenException(OAuth2ErrorEnum.INVALID_REQUEST.getErrorCode(), "授权码无效或已过期");
            }
            return new CustomOauthTokenException(OAuth2ErrorEnum.BAD_CREDENTIALS.getErrorCode(), OAuth2ErrorEnum.BAD_CREDENTIALS.getErrorMsg());
        }
        // 无效grant_type
        if (e instanceof UnsupportedGrantTypeException) {
            return new CustomOauthTokenException(OAuth2ErrorEnum.INVALID_REQUEST.getErrorCode(), "无效认证类型");
        }
        // 自定义的认证模式可以直接用这个，可参考mobileCodeAuthenticationProvider
       /* if (e instanceof CustomOauthTokenException) {
            CustomOauthTokenException exception = (CustomOauthTokenException) e;
            return new CustomOauthTokenException(exception.getCode(), exception.getMessage());
        }*/
        // 范围不正确
        if (e instanceof InvalidScopeException) {

        }
        log.error("未知认证异常:{0}", e);
        return new CustomOauthTokenException(OAuth2ErrorEnum.SYSTEM_ERROR.getErrorCode(), OAuth2ErrorEnum.SYSTEM_ERROR.getErrorMsg());
    }
}
