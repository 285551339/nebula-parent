package com.xy.nebulaol.uaa.controller;

import com.xy.nebulaol.api.UserService;
import com.xy.nebulaol.common.domain.vo.resp.BaseResponse;
import com.xy.nebulaol.vo.req.LoginReqVo;
import com.xy.nebulaol.vo.req.RefreshTokenReqVo;
import com.xy.nebulaol.vo.req.WechatLoginVo;
import com.xy.nebulaol.vo.resp.LoginRespVo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author zangliulu
 * @Title: 用户管理接口
 * @Package
 * @Description:
 * @date 2021/9/16 16:52
 */
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    RabbitTemplate rabbitTemplate;
    /**
     * 登录
     * @param req
     * @return
     */
    @PostMapping("/login")
    public BaseResponse login(@Valid @RequestBody LoginReqVo req) {
        return BaseResponse.ok();
    }



    /**
     * 微信登录
     * @param wechatLoginVo
     * @return
     */
    @PostMapping("/wechat/login")
    public BaseResponse wechatLogin(@RequestBody @Valid WechatLoginVo wechatLoginVo){
        LoginRespVo loginRespVo = userService.wechatLogin(wechatLoginVo.getCode());
        return BaseResponse.ok(loginRespVo);
    }

    /**
     * 登出
     * @return
     */
    @DeleteMapping("/logout")
    public BaseResponse logout() {
        userService.logout();
        return BaseResponse.ok();
    }

    /**
     * 刷新token
     * @param req
     * @return
     */
    @PutMapping("/refreshToken")
    public BaseResponse refreshToken(@Valid @RequestBody RefreshTokenReqVo req) {
        LoginRespVo login = userService.refreshToken(req);
        return BaseResponse.ok(login);
    }

}
