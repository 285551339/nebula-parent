package com.xy.nebulaol.common.domain.vo.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @program: sdx-parent
 * @description: BaseBrandIdReq
 * @author: fushilin
 * @create: 2020-12-13 15:15
 * @versions: 1.0
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseBrandIdReq implements Serializable {
    private static final long serialVersionUID = 4563214192558417121L;
    @NotNull(message = "品牌id不能为空")
    private Long brandId;
}
