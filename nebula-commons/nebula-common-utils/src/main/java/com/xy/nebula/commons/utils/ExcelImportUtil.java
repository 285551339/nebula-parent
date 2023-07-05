package com.xy.nebula.commons.utils;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Slf4j
public class ExcelImportUtil {

    /**
     * 读取excel表格内容返回List<Map>
     *
     * @param inputStream excel文件流
     * @param head        表头数组
     * @param headerAlias 表头别名数组
     * @return
     */
    public static List<Map<String, Object>> importExcel(InputStream inputStream, String[] head, String[] headerAlias) {
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<Object> header = reader.readRow(0);
        //替换表头关键字
        if (ArrayUtils.isEmpty(head) || ArrayUtils.isEmpty(headerAlias) || head.length != headerAlias.length) {
            return null;
        } else {
            for (int i = 0; i < head.length; i++) {
                if (head[i].equals(header.get(i))) {
                    reader.addHeaderAlias(head[i], headerAlias[i]);
                } else {
                    return null;
                }

            }
        }

        return reader.read(0, 1, Integer.MAX_VALUE);
    }

    /**
     * 读取excel表格内容返回List<Bean>
     *
     * @param inputStream excel文件流
     * @param head        表头数组
     * @param headerAlias 表头别名数组
     * @return
     */
    public static  <T> List<T> importExcel(InputStream inputStream, String[] head, String[] headerAlias, Class<T> bean) {
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<Object> header = reader.readRow(0);
        //替换表头关键字
        if (ArrayUtils.isEmpty(head) || ArrayUtils.isEmpty(headerAlias) || head.length != headerAlias.length) {
            return null;
        } else {
            for (int i = 0; i < head.length; i++) {
                if (head[i].equals(header.get(i))) {
                    reader.addHeaderAlias(head[i], headerAlias[i]);
                } else {
                    return null;
                }

            }
        }
        //读取指点行开始的表数据（从0开始）
        return reader.read(0, 1, bean);
    }
}