package com.xy.nebulaol.uaa.Service;

import com.xy.nebulao.commons.utils.bean.BeanUtil;
import com.xy.nebulaol.api.SysUserService;
import com.xy.nebulaol.user.api.RpcUserService;
import com.xy.nebulaol.user.dto.req.UserDetailsReqDto;
import com.xy.nebulaol.user.dto.resp.UserDetailsRespDto;
import com.xy.nebulaol.vo.SysUserVo;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.tomcat.util.modeler.BaseModelMBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zangliulu
 * @Title:
 * @Package
 * @Description:
 * @date 2021/6/11 10:23
 */
@Service
public class SysUserServiceImpl implements SysUserService, UserDetailsService {

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Reference
    private RpcUserService rpcUserService;

    @Autowired
    private HttpServletRequest request;

    @Override
    public SysUserVo loadUserByUsername(String username) throws UsernameNotFoundException {
        String clientId = request.getParameter("client_id");
        UserDetailsReqDto  reqDto = new UserDetailsReqDto();
        reqDto.setUsername(username);
        UserDetailsRespDto userByName = rpcUserService.findUserByName(reqDto);
        SysUserVo sysUserVo = new SysUserVo(userByName);
        sysUserVo.setClientId(clientId);
        sysUserVo.setEnabled(true);
        return sysUserVo;
    }
}
