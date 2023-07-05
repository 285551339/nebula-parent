package com.xy.nebula.common.domain.vo.req;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * description: 基础参数
 * date: 2020-09-02 08:15
 * author: chenxd
 * version: 1.0
 */
@Data
public class BaseReq implements Serializable {

    private static final long serialVersionUID = -6607245271820074588L;

    @NotEmpty(message = "应用标识不可为空")
    private String appid;
    @NotEmpty(message = "随机数不可为空")
    @Length(min = 32, max = 64, message = "长度必须在32~64位之间")
    private String nonce;
    @NotNull(message = "时间戳不可以为空")
    private Long ts;

}
