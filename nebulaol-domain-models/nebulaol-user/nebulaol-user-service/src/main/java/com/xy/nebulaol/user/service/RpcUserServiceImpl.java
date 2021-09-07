package com.xy.nebulaol.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.nebulao.commons.utils.bean.BeanUtil;
import com.xy.nebulaol.common.web.exception.BusinessException;
import com.xy.nebulaol.user.api.RpcUserService;
import com.xy.nebulaol.user.dto.req.UserDetailsReqDto;
import com.xy.nebulaol.user.dto.resp.UserDetailsRespDto;
import com.xy.nebulaol.user.mapper.NebulaolUserMapper;
import com.xy.nebulaol.user.po.NebulaolUserPo;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author zangliulu
 * @Title: 用户业务类
 * @Package
 * @Description:
 * @date 2021/6/22 14:40
 */
@Service
public class RpcUserServiceImpl extends ServiceImpl<NebulaolUserMapper, NebulaolUserPo> implements RpcUserService {
    @Override
    public UserDetailsRespDto findUserByName(UserDetailsReqDto userDetailsReqDto) {
        QueryWrapper<NebulaolUserPo> userPoQueryWrapper = new QueryWrapper<>();
        userPoQueryWrapper.lambda().eq(NebulaolUserPo::getUsername,userDetailsReqDto.getUsername());
        NebulaolUserPo userByNameAndPassword = this.baseMapper.selectOne(userPoQueryWrapper);
        if(null == userByNameAndPassword){
            throw new BusinessException("用户账号密码错误");
        }
        return BeanUtil.copyProperties(userByNameAndPassword,UserDetailsRespDto.class);
    }
}
