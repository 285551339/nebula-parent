package com.nebula.user.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nebula.user.dto.req.UserDetailsReqDto;
import com.nebula.user.dto.resp.UserDetailsRespDto;
import com.nebula.user.po.AdminUserPo;

/**
 * @author zangliulu
 * @Title: 用户业务类
 * @Package
 * @Description:
 * @date 2021/6/22 14:37
 */
public interface RpcUserService extends IService<AdminUserPo> {

    /**
     *  根据用户名和密码获取用户信息
     * @param userDetailsReqDto
     * @return
     */
    UserDetailsRespDto findUserByName(UserDetailsReqDto userDetailsReqDto);
}
