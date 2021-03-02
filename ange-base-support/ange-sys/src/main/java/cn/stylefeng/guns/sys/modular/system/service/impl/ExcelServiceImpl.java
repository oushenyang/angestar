package cn.stylefeng.guns.sys.modular.system.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.export.styler.AbstractExcelExportStyler;
import cn.afterturn.easypoi.excel.export.styler.IExcelExportStyler;
import cn.stylefeng.guns.sys.modular.system.service.ExcelService;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

/**
 * <p>excel 服务</p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2021/2/2
 * @since JDK 1.8
 */
@Service
public class ExcelServiceImpl implements ExcelService {
    /**
     * Excel文件导出,导出的文件名默认为:headTitle+当前系统时间
     * @param listData 要导出的list数据
     * @param pojoClass 定义excel属性信息
     * @param headTitle Excel文件头信息
     * @param sheetName Excel文件sheet名称
     * @param response
     */
    @Override
    public void exportExcel(Collection<?> listData, Class<?> pojoClass, String headTitle, String sheetName, HttpServletResponse response) {
        ExportParams params = new ExportParams();
        params.setHeight((short) 8);
        params.setStyle(ExcelExportMyStylerImpl.class);
        try {
            Workbook workbook = ExcelExportUtil.exportExcel(params, pojoClass, listData);
            String fileName = headTitle + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            fileName = URLEncoder.encode(fileName, "UTF8");
            response.setContentType("application/vnd.ms-excel;chartset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename="+fileName + ".xlsx");
            ServletOutputStream out=response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * excel 样式
     */
    public static class ExcelExportMyStylerImpl extends AbstractExcelExportStyler implements IExcelExportStyler {

        public ExcelExportMyStylerImpl(Workbook workbook) {
            super.createStyles(workbook);
        }

        @Override
        public CellStyle getTitleStyle(short color) {
            CellStyle titleStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);// 加粗
            titleStyle.setFont(font);
            titleStyle.setAlignment(HorizontalAlignment.CENTER);// 居中
            titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
            titleStyle.setFillForegroundColor(IndexedColors.AQUA.index);// 设置颜色
            titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            titleStyle.setBorderRight(BorderStyle.THIN);
            titleStyle.setWrapText(true);
            return titleStyle;
        }

        @Override
        public CellStyle stringSeptailStyle(Workbook workbook, boolean isWarp) {
            CellStyle style = workbook.createCellStyle();
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            style.setDataFormat(STRING_FORMAT);
            if (isWarp) {
                style.setWrapText(true);
            }
            return style;
        }

        @Override
        public CellStyle getHeaderStyle(short color) {
            CellStyle titleStyle = workbook.createCellStyle();
//            Font font = workbook.createFont();
//            font.setBold(true);// 加粗
//            font.setColor(IndexedColors.RED.index);
//            font.setFontHeightInPoints((short) 11);
//            titleStyle.setFont(font);
//            titleStyle.setAlignment(HorizontalAlignment.CENTER);// 居中
//            titleStyle.setFillForegroundColor(IndexedColors.WHITE.index);// 设置颜色
//            titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
//            titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//            titleStyle.setBorderRight(BorderStyle.THIN);
//            titleStyle.setWrapText(true);
            return titleStyle;
        }

        @Override
        public CellStyle stringNoneStyle(Workbook workbook, boolean isWarp) {
            CellStyle style = workbook.createCellStyle();
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            style.setDataFormat(STRING_FORMAT);
            if (isWarp) {
                style.setWrapText(true);
            }
            return style;
        }
    }
}
