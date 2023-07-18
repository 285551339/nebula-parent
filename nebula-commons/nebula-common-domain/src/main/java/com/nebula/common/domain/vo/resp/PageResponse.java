package com.nebula.common.domain.vo.resp;

import com.github.pagehelper.Page;
import com.nebula.common.domain.vo.req.BasePageReq;
import com.nebula.commons.utils.bean.BeanMapperUtil;
import com.nebula.commons.utils.bean.BeanUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.function.Supplier;

/**
 * description: PageResponse
 * date: 2020-09-02 19:59
 * author: chenxd
 * version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> implements Serializable {

    private static final long serialVersionUID = 5263677351908684321L;

    /**
     * 当前页数
     */
    private int pageNum;
    /**
     * 每页记录数
     */
    private int pageSize;
    /**
     * 总记录数
     */
    private long total;
    /**
     * 总页数
     */
    private int pages;
    /**
     * 列表数据
     */
    private List<T> list;

    /**
     * 分页
     *
     * @param list 列表数据
     */
    public PageResponse(List<T> list) {
        if (list == null) {
            return;
        }
        this.list = list;
        if (list instanceof Page) {
            Page<T> page = (Page<T>) list;
            this.pageNum = page.getPageNum();
            this.pageSize = page.getPageSize();
            this.total = page.getTotal();
            this.pages = page.getPages();
            this.list = page.getResult();
        } else {
            this.pageNum = 1;
            this.pageSize = list.size();
            this.total = list.size();
            this.pages = 1;
        }
    }

    public PageResponse(BasePageReq pageReq, Page<?> page, List<T> list) {
        this.pageNum = pageReq.getPageNum();
        this.pageSize = pageReq.getPageSize();
        this.total = page.getTotal();
        this.pages = page.getPages();
        this.list = list;
    }

    public PageResponse(org.springframework.data.domain.Page<T> page) {
        if (page == null) {
            return;
        }
        this.list = page.getContent();
        this.pageNum = page.getPageable().getPageNumber();
        this.pageSize = page.getPageable().getPageSize();
        this.total = page.getTotalElements();
        this.pages = page.getTotalPages();
    }

    public static <T> PageResponse<T> page(List<T> list) {
        PageResponse<T> pageResponse = new PageResponse<T>(list);
        pageResponse.setList(list);
        return pageResponse;
    }

    /**
     * 列表分页返回，同时进行对象转换
     *
     * @param list
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> PageResponse<T> page(List<?> list, Class<T> targetClass) {
        PageResponse<T> pageResponse = new PageResponse<>();
        if (list instanceof Page) {
            Page<T> page = (Page<T>) list;
            pageResponse.pageNum = page.getPageNum();
            pageResponse.pageSize = page.getPageSize();
            pageResponse.total = page.getTotal();
            pageResponse.pages = page.getPages();
        }
        List<T> targetList = BeanMapperUtil.mapList(list, targetClass);
        pageResponse.setList(targetList);
        return pageResponse;
    }

    /**
     * 列表分页返回，对象转换
     *
     * @param list
     * @param targetSupplier
     * @param <T>
     * @return
     */
    public static <T> PageResponse<T> page(List<?> list, Supplier<T> targetSupplier) {
        PageResponse<T> pageResponse = new PageResponse<>();
        if (list instanceof Page) {
            Page<T> page = (Page<T>) list;
            pageResponse.pageNum = page.getPageNum();
            pageResponse.pageSize = page.getPageSize();
            pageResponse.total = page.getTotal();
            pageResponse.pages = page.getPages();
        }
        List<T> targetList = BeanMapperUtil.mapObjectList(list, targetSupplier.get());
        pageResponse.setList(targetList);
        return pageResponse;
    }

    /**
     * 分页返回对象复制转换
     *
     * @param pageResponse
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> PageResponse<T> pageCopyTransfer(PageResponse<?> pageResponse, Class<T> targetClass) {
        PageResponse<T> targetPage = new PageResponse<>();
        targetPage.setPageNum(pageResponse.getPageNum());
        targetPage.setPages(pageResponse.getPages());
        targetPage.setPageSize(pageResponse.getPageSize());
        targetPage.setTotal(pageResponse.getTotal());

        targetPage.setList(
                BeanMapperUtil.mapList(pageResponse.getList(), targetClass)
        );
        return targetPage;
    }

    /**
     * page分页返回，同时进行对象转换
     *
     * @param page
     * @param targetSupplier
     * @param <T>
     * @return
     */
    public static <T> PageResponse page(org.springframework.data.domain.Page page, Supplier<T> targetSupplier) {
        PageResponse pageResponse = new PageResponse(page);
        List<T> targetList = BeanUtil.convertListProperties(page.getContent(), targetSupplier);
        pageResponse.setList(targetList);
        return pageResponse;
    }

    public static <T> void convertListProperties(PageResponse pageResponse, Supplier<T> targetSupplier) {
        List<T> targetList = BeanUtil.convertListProperties(pageResponse.getList(), targetSupplier);
        pageResponse.setList(targetList);
    }
}
