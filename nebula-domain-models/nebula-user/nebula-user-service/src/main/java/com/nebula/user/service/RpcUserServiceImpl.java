package com.nebula.user.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nebula.user.api.RpcUserService;
import com.nebula.user.dto.req.UserDetailsReqDto;
import com.nebula.user.dto.resp.UserDetailsRespDto;
import com.nebula.user.mapper.AdminUserMapper;

import com.nebula.user.po.AdminUserPo;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author zangliulu
 * @Title: 用户业务类
 * @Package
 * @Description:
 * @date 2021/6/22 14:40
 */
@Service
public class RpcUserServiceImpl extends ServiceImpl<AdminUserMapper, AdminUserPo> implements RpcUserService {

    @Override
    public UserDetailsRespDto findUserByName(UserDetailsReqDto userDetailsReqDto) {
        QueryWrapper<AdminUserPo> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(AdminUserPo::getUsername, userDetailsReqDto.getUsername());
        AdminUserPo adminUserPo = this.baseMapper.selectOne(wrapper);

        UserDetailsRespDto detailsRespDto = BeanUtil.copyProperties(adminUserPo, UserDetailsRespDto.class);
        return detailsRespDto;
    }
}
