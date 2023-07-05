package com.xy.nebula.commons.utils.wechat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: MaXin
 * @Description:
 * @DateTime: 2021/5/21$ 9:46$
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MediaInfo implements Serializable {
    private static final long serialVersionUID = -8751289780281385001L;

    private String fileName;

    private String inputStreamBase64;
}
