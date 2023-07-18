package com.nebula.uaa.vo.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @Author: MaXin
 * @Description:
 * @DateTime: 2021/5/17$ 10:12$
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatLoginVo implements Serializable {
    private static final long serialVersionUID = 158756897567109769L;

    @NotEmpty(message = "code不能为空")
    private String code;
}
