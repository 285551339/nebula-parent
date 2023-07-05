package com.xy.nebula.common.web.util;

import com.xy.nebulao.commons.utils.bean.BeanMapperLocalUtil;
import com.xy.nebula.common.domain.vo.resp.PageResponse;

/**
 * 分页对象复制工具类
 *
 * @apiNote 复制使用 {@link BeanMapperLocalUtil}来作为复制，对象复制 {@link org.dozer.Mapping} 将不可使用
 *
 * @author zhengdd
 * @createTime 2021-05-25
 */
public class PageResponseUtils {

    /**
     * 分页对象复制工具
     *
     * @apiNote 本方法使用 {@link BeanMapperLocalUtil}作为复制工具
     *
     * @param pageResponse
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> PageResponse<T> page(PageResponse<?> pageResponse, Class<T> targetClass) {
        PageResponse<T> response = BeanMapperLocalUtil.map(pageResponse, PageResponse.class);
        response.setList(
                BeanMapperLocalUtil.mapList(pageResponse.getList(), targetClass)
        );
        return response;
    }

}
