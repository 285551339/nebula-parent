package com.nebula.common.domain.vo.req;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * description: PageReq
 * date: 2020-09-02 09:20
 * author: chenxd
 * version: 1.0
 */
@Data
public class BasePageReq implements Serializable {

    private static final long serialVersionUID = 532314830958582219L;

    @NotNull
    @Min(value = 1, message = "页码最小值为1")
    private int pageNum;

    @NotNull
    @Min(value = 1, message = "每页条数最小值1")
    @Max(value = 100, message = "每页条数最大值为100")
    private int pageSize;
}
