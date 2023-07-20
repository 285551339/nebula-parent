package com.nebula.uaa.service;

import com.nebula.uaa.api.UserService;
import com.nebula.uaa.vo.req.LoginReqVo;
import com.nebula.uaa.vo.req.RefreshTokenReqVo;
import com.nebula.uaa.vo.resp.LoginRespVo;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
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


    @Autowired
    RocketMQTemplate rocketMQTemplate;

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
