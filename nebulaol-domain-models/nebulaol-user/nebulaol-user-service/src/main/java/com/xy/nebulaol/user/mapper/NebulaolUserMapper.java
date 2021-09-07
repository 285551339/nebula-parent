package com.xy.nebulaol.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xy.nebulaol.user.dto.req.UserDetailsReqDto;
import com.xy.nebulaol.user.po.NebulaolUserPo;

/**
 * @author zangliulu
 * @Title: 用户数据库操作类
 * @Package
 * @Description:
 * @date 2021/6/22 14:46
 */
public interface NebulaolUserMapper extends BaseMapper<NebulaolUserPo> {

    NebulaolUserPo findUserByNameAndPassword(UserDetailsReqDto userDetailsReqDto);
}
