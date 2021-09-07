package com.xy.nebulao.commons.utils.excel;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExcelUtil {

    /**
     * @Description: 导出
     * @auther: zwq
     * @date: 2021/2/9 0009 上午 10:44
     */
    public static void createExcel(HttpServletResponse response, ExcelWriter writer, String name){
        if (null == writer){
            throw new RuntimeException("字段名不能为空！");
        }
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + name + ".xls");
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            writer.flush(out, true);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            writer.close();
            IoUtil.close(out);
        }
    }
}
