package com.xy.nebulaol.uaa.Service;

import com.google.common.collect.Sets;
import com.xy.nebulaol.api.SysClientService;
import com.xy.nebulaol.common.domain.constant.OperationLogConstant;
import com.xy.nebulaol.user.api.RpcClientService;
import com.xy.nebulaol.user.dto.req.ClientDetailsReqDto;
import com.xy.nebulaol.user.dto.resp.ClientDetailsRespDto;
import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author zangliulu
 * @Title: app应用接口
 * @Package
 * @Description:
 * @date 2021/6/23 15:22
 */
@Service
public class SysClientServiceImpl implements SysClientService, ClientDetailsService {

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Reference
    private RpcClientService rpcClientService;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        ClientDetailsReqDto clientDetailsReqDto = new ClientDetailsReqDto();
        clientDetailsReqDto.setClientId(clientId);
        ClientDetailsRespDto clientByClientId = rpcClientService.findClientByClientId(clientDetailsReqDto);
        return this.buildBaseClientDetails(clientByClientId);
    }

    /**
     * 构建ClientDetails
     *
     * @param sysClient 系统客户端
     * @return ClientDetails
     */
    private ClientDetails buildBaseClientDetails(ClientDetailsRespDto sysClient) {
        BaseClientDetails baseClientDetails = new BaseClientDetails();
        baseClientDetails.setClientId(sysClient.getClientId());
        baseClientDetails.setAutoApproveScopes(new ArrayList<>());
        String autoApproveScopes = sysClient.getAutoApproveScopes();
        if (StringUtils.isNotBlank(autoApproveScopes)) {
            String[] split = StringUtils.split(autoApproveScopes, OperationLogConstant.SEPARATOR);
            baseClientDetails.setAutoApproveScopes(Arrays.asList(split));
        }
        baseClientDetails.setClientSecret(passwordEncoder.encode(sysClient.getClientSecret()));
        baseClientDetails.setScope(new ArrayList<>());
        String scopes = sysClient.getScopes();
        if (StringUtils.isNotBlank(scopes)) {
            String[] split = StringUtils.split(scopes, OperationLogConstant.SEPARATOR);
            baseClientDetails.setScope(Arrays.asList(split));
        }
        String resourceIds = sysClient.getResourceIds();
        baseClientDetails.setResourceIds(new ArrayList<>());
        if (StringUtils.isNotBlank(resourceIds)) {
            String[] split = StringUtils.split(resourceIds, OperationLogConstant.SEPARATOR);
            baseClientDetails.setResourceIds(Arrays.asList(split));
        }
        String grantTypes = sysClient.getGrantTypes();
        if (StringUtils.isNotBlank(grantTypes)) {
            String[] split = StringUtils.split(grantTypes, OperationLogConstant.SEPARATOR);
            baseClientDetails.setAuthorizedGrantTypes(Arrays.asList(split));
        }
        String redirectUris = sysClient.getRedirectUris();
        baseClientDetails.setRegisteredRedirectUri(Sets.newHashSet());
        if (StringUtils.isNotBlank(redirectUris)) {
            String[] split = StringUtils.split(redirectUris, OperationLogConstant.SEPARATOR);
            baseClientDetails.setRegisteredRedirectUri(Sets.newHashSet(split));
        }
        String authorityIds = sysClient.getAuthorityIds();
        baseClientDetails.setAuthorities(new ArrayList<>());
        baseClientDetails.setAccessTokenValiditySeconds(Math.toIntExact(sysClient.getAccessTokenValiditySeconds()));
        baseClientDetails.setRefreshTokenValiditySeconds(Math.toIntExact(sysClient.getRefreshTokenValiditySeconds()));
        // 附加信息
        // baseClientDetails.setAdditionalInformation();
        return baseClientDetails;
    }
}
