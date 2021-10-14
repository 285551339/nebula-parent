package com.xy.uaa;

import com.alibaba.fastjson.JSONObject;
import com.xy.BaseTest;
import com.xy.nebulao.commons.utils.SignUtil;
import com.xy.nebulao.commons.utils.network.HttpClientUtil;
import com.xy.nebulaol.common.domain.constant.CommonConstant;
import com.xy.nebulaol.common.domain.vo.resp.BaseResponse;
import com.xy.nebulaol.common.domain.vo.resp.ErrorType;
import org.junit.Test;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zangliulu
 * @Title:
 * @Package
 * @Description: 登录测试
 * @date 2021/10/14 10:50
 */
public class LoginTest extends BaseTest {

    @Test
    public void testLogin() {
        headers.put(CommonConstant.APPCODE, CommonConstant.APPCODE_XY_MANAGE_UAA);
        headers.put(CommonConstant.CURRENT_ROLE, "");
        headers.put(CommonConstant.CURRENT_BRAND, "");
        Map<String, Object> signMap = new HashMap<>();
        signMap.putAll(headers);
        String sign =  SignUtil.buildSign(signMap, secretMap.get(CommonConstant.APPCODE_XY_MANAGE_UAA));
        headers.put(CommonConstant.SIGN, sign);
        String url = target_url + "api/uaa/login";
        String result = HttpClientUtil.deleteWithHeader(url, headers);
        System.out.println(result);
        BaseResponse response = JSONObject.parseObject(result, BaseResponse.class);
        Assert.isTrue(response != null && response.getCode().equals(ErrorType.SUCCESS_SYSTEM.getCode()), "接口异常");
    }
}
