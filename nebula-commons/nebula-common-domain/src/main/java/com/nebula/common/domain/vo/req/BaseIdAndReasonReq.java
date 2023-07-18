package com.nebula.common.domain.vo.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * description: 主键ID
 * date: 2020-09-02 22:02
 * author: chenxd
 * version: 1.0
 */
@Data
public class BaseIdAndReasonReq implements Serializable {

    private static final long serialVersionUID = 7568222576672488221L;

    //主键
    @NotNull(message = "ID不能为空")
    private Long id;
    //主键
    @NotBlank(message = "原因不能为空")
    private String reason;
}
