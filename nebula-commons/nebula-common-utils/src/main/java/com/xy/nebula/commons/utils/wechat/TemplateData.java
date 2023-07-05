package com.xy.nebula.commons.utils.wechat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @Author: MaXin
 * @Description:
 * @DateTime: 2021/5/21$ 13:36$
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TemplateData {

    /**
     * 接收者openid 必传
     */
    private String touser;

    /**
     * 模板ID 必传
     */
    private String template_id;

    /**
     * 模板跳转链接（海外帐号没有跳转能力）
     */
    private String url;

    /**
     * 跳小程序所需数据，不需跳小程序可不用传该数据
     */
    private String miniprogram;

    /**
     * 所需跳转到的小程序appid（该小程序appid必须与发模板消息的公众号是绑定关联关系，暂不支持小游戏） 必传
     */
    private String appid;

    /**
     * 所需跳转到小程序的具体页面路径，支持带参数,（示例index?foo=bar），要求该小程序已发布，暂不支持小游戏
     */
    private String pagepath;

    /**
     * 模板数据  必传
     */
    private Map<String,Object> data;

    /**
     * 模板内容字体颜色，不填默认为黑色
     */
    private String color;
}
