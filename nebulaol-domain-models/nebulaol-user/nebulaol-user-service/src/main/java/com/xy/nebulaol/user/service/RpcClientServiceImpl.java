package com.xy.nebulaol.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.nebulao.commons.utils.bean.BeanUtil;
import com.xy.nebulaol.common.web.exception.BusinessException;
import com.xy.nebulaol.user.api.RpcClientService;
import com.xy.nebulaol.user.dto.req.ClientDetailsReqDto;
import com.xy.nebulaol.user.dto.resp.ClientDetailsRespDto;
import com.xy.nebulaol.user.mapper.NebulaolClientMapper;
import com.xy.nebulaol.user.po.NebulaolClientPo;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author zangliulu
 * @Title: 应用业务类
 * @Package
 * @Description:
 * @date 2021/6/22 14:39
 */
@Service
public class RpcClientServiceImpl extends ServiceImpl<NebulaolClientMapper, NebulaolClientPo> implements RpcClientService {
    @Override
    public ClientDetailsRespDto findClientByClientId(ClientDetailsReqDto clientDetailsReqDto) {
        QueryWrapper<NebulaolClientPo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(NebulaolClientPo::getClientId,clientDetailsReqDto.getClientId());
        NebulaolClientPo nebulaolClientPo = this.baseMapper.selectOne(queryWrapper);
        if(null == nebulaolClientPo){
            throw new BusinessException("未找到应用");
        }
        return BeanUtil.copyProperties(nebulaolClientPo,ClientDetailsRespDto.class);
    }
}
