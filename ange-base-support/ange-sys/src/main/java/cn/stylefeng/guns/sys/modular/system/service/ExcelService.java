package cn.stylefeng.guns.sys.modular.system.service;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

/**
 * <p>excel表格</p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2021/2/2
 * @since JDK 1.8
 */
public interface ExcelService {
    /**
     * 通用导出excel
     * @param dataList 数据
     * @param pojo excel表头
     * @param response
     */
    void exportExcel(Collection<?> dataList, Class<?> pojo, String head, String sheetName, HttpServletResponse response);
}
