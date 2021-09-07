package com.xy.nebulaol.gateway.params;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;

/**
 * @author llzang
 */
@Slf4j
public class ResponseParamsHandle {

    public static <T extends DataBuffer> T chain(T buffer) {
        ParamsUtil.BodyDecorator bodyDecorator = ParamsUtil.buildBodyDecorator(buffer);
        // 响应参数打印
        log.info("response: [{}]", bodyDecorator.getBody());
        return (T) bodyDecorator.getDataBuffer();
    }


}
