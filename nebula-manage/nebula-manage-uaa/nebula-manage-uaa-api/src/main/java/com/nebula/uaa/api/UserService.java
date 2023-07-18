package com.nebula.uaa.api;


import com.nebula.uaa.vo.resp.LoginRespVo;
import com.nebula.uaa.vo.req.LoginReqVo;
import com.nebula.uaa.vo.req.RefreshTokenReqVo;

/**
 * @author zangliulu
 * @Title: 用户接口
 * @Package
 * @Description:
 * @date 2021/9/16 16:24
 */
public interface UserService {

    /**
     * 登录
     * @param req
     * @return
     */
    LoginRespVo login(LoginReqVo req);

    /**
     * 微信登录
     * @param code
     * @return
     */
    LoginRespVo wechatLogin(String code);

    /**
     * 登出
     */
    void logout();

    /**
     * 刷新token
     * @param req
     * @return
     */
    LoginRespVo refreshToken(RefreshTokenReqVo req);

}
