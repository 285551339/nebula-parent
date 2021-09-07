package com.xy.nebulaol.user.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xy.nebulaol.user.dto.req.UserDetailsReqDto;
import com.xy.nebulaol.user.dto.resp.UserDetailsRespDto;
import com.xy.nebulaol.user.po.NebulaolUserPo;

/**
 * @author zangliulu
 * @Title: 用户业务类
 * @Package
 * @Description:
 * @date 2021/6/22 14:37
 */
public interface RpcUserService extends IService<NebulaolUserPo> {

    /**
     *  根据用户名和密码获取用户信息
     * @param userDetailsReqDto
     * @return
     */
    UserDetailsRespDto findUserByName(UserDetailsReqDto userDetailsReqDto);
}
