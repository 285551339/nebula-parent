package com.xy.nebulaol.user.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xy.nebulaol.user.dto.req.ClientDetailsReqDto;
import com.xy.nebulaol.user.dto.resp.ClientDetailsRespDto;
import com.xy.nebulaol.user.po.NebulaolClientPo;
import org.checkerframework.checker.units.qual.C;

/**
 * @author zangliulu
 * @Title: 应用业务接口
 * @Package
 * @Description:
 * @date 2021/6/22 14:37
 */

public interface RpcClientService extends IService<NebulaolClientPo> {

    /**
     * 根据clientId查询client应用详情
     * @param  clientDetailsReqDto
     * @return ClientDetailsRespDto
     *
     **/
     ClientDetailsRespDto findClientByClientId(ClientDetailsReqDto clientDetailsReqDto);
}
