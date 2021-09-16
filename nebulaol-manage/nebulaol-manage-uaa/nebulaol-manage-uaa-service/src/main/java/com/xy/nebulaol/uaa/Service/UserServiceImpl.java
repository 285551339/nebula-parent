package com.xy.nebulaol.uaa.Service;

import com.xy.nebulaol.api.UserService;
import com.xy.nebulaol.common.domain.vo.resp.BaseResponse;
import com.xy.nebulaol.vo.req.LoginReqVo;
import com.xy.nebulaol.vo.req.RefreshTokenReqVo;
import com.xy.nebulaol.vo.resp.LoginRespVo;
import org.springframework.stereotype.Service;

/**
 * @author zangliulu
 * @Title: 用户接口
 * @Package
 * @Description:
 * @date 2021/9/16 16:51
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public LoginRespVo login(LoginReqVo req) {
        return null;
    }

    @Override
    public LoginRespVo wechatLogin(String code) {
        return null;
    }

    @Override
    public void logout() {

    }

    @Override
    public LoginRespVo refreshToken(RefreshTokenReqVo req) {
        return null;
    }
}
