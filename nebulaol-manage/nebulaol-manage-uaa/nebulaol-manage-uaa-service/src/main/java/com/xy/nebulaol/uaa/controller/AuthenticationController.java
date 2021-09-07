package com.xy.nebulaol.uaa.controller;


import com.alibaba.fastjson.JSON;
import com.xy.nebulao.commons.utils.ExcelImportUtil;
import com.xy.nebulaol.common.domain.vo.resp.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * 认证类
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class AuthenticationController {

    @Autowired
    private SpringClientFactory factory;

    @GetMapping("/info")
    public BaseResponse login(HttpServletResponse response){

        return  BaseResponse.ok();
    }

}
