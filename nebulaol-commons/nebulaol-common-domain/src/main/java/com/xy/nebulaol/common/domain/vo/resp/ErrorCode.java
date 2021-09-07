package com.xy.nebulaol.common.domain.vo.resp;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * description: ErrorCode
 * date: 2020-08-26 15:57
 * author: chenxd
 * version: 1.0
 */
@Getter
public class ErrorCode {

    private static final Map<String, String> SERVICE_ERROR_MAP = new HashMap<>();

    static {
        SERVICE_ERROR_MAP.put("sdx-gateway", "00");
        SERVICE_ERROR_MAP.put("sdx-uaa", "01");
        SERVICE_ERROR_MAP.put("sdx-supplier", "02");
        SERVICE_ERROR_MAP.put("sdx-staff", "03");
        SERVICE_ERROR_MAP.put("sdx-purchase", "04");
        SERVICE_ERROR_MAP.put("sdx-ops", "05");
        SERVICE_ERROR_MAP.put("sdx-cashier", "06");
        SERVICE_ERROR_MAP.put("sdx-bi", "07");
        SERVICE_ERROR_MAP.put("sdx-activity", "08");
        SERVICE_ERROR_MAP.put("sdx-branch", "09");
        SERVICE_ERROR_MAP.put("sdx-order", "10");
        SERVICE_ERROR_MAP.put("sdx-product", "11");
        SERVICE_ERROR_MAP.put("sdx-user", "12");
        SERVICE_ERROR_MAP.put("sdx-pubwin", "13");
        SERVICE_ERROR_MAP.put("sdx-tools", "14");
    }


}
