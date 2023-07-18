package com.nebula.common.domain.vo.req;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
* @Description: 门店id
* @Param:
* @throws:
* @author: fushilin
* @Date: 2021/5/17 16:59
* @versions: 1.0
*/
@Data
public class BaseBranchIdReq implements Serializable {

    @NotNull(message = "门店id不能为空")
    private Long branchId;

}
