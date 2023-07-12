package com.xy.nebulaol.uaa.Service;

import com.xy.nebula.commons.utils.bean.BeanUtil;
import com.xy.nebulaol.api.UserService;
import com.xy.nebulaol.common.domain.vo.resp.BaseResponse;
import com.xy.nebulaol.user.api.RpcUserService;
import com.xy.nebulaol.user.dto.req.UserDetailsReqDto;
import com.xy.nebulaol.user.dto.resp.UserDetailsRespDto;
import com.xy.nebulaol.vo.req.LoginReqVo;
import com.xy.nebulaol.vo.req.RefreshTokenReqVo;
import com.xy.nebulaol.vo.resp.LoginRespVo;
import org.apache.dubbo.config.annotation.DubboReference;
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
    
    @DubboReference
    RpcUserService rpcUserService;

    @Autowired
    RocketMQTemplate rocketMQTemplate;

    @Override
    public LoginRespVo login(LoginReqVo req) {
        UserDetailsReqDto reqDto = BeanUtil.copyProperties(req, UserDetailsReqDto.class);
        UserDetailsRespDto userDto = rpcUserService.findUserByName(reqDto);
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
