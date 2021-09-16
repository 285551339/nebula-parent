package com.xy.nebulaol.api;

import com.xy.nebulaol.common.domain.vo.resp.BaseResponse;
import com.xy.nebulaol.vo.req.LoginReqVo;
import com.xy.nebulaol.vo.req.RefreshTokenReqVo;
import com.xy.nebulaol.vo.resp.LoginRespVo;

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
    BaseResponse wechatLogin(String code);

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
