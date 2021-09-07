package com.xy.nebulaol.uaa.handle;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * @author zangliulu
 * @Title: 自定义oauth2异常
 * @Package
 * @Description:
 * @date 2021/6/23 13:40
 */
@EqualsAndHashCode(callSuper = true)
@Data
@JsonSerialize(using = CustomOauthTokenExceptionJsonSerializer.class)
public class CustomOauthTokenException extends OAuth2Exception {

    private int code;

    private String message;

    public CustomOauthTokenException(String msg, Throwable t) {
        super(msg, t);
    }

    public CustomOauthTokenException(String msg) {
        super(msg);
    }

    public CustomOauthTokenException(int code, String message) {
        super(message);
        setCode(code);
        setMessage(message);
    }
}
