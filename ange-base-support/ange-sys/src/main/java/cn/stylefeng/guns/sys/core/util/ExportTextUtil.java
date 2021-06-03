package cn.stylefeng.guns.sys.core.util;

import cn.hutool.core.text.StrBuilder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * <p>导出txt</p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2020/4/26
 * @since JDK 1.8
 */
public class ExportTextUtil {
    // 输出TXT
    public static void writeToTxt(HttpServletResponse response, List<String> list, String  filename) {

        response.setContentType("text/plain");// 一下两行关键的设置
        String a = null;
        try {
            a = new String((filename).getBytes("GB2312"),"iso8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.addHeader("Content-Disposition", "attachment;filename="+a+".txt");// filename指定默认的名字
        BufferedOutputStream buff = null;
//        StringBuilder write = new StringBuilder();
        StrBuilder write = StrBuilder.create();
        String tab = "  ";
        String enter = "\r\n";
        ServletOutputStream outSTr = null;
        try {
            outSTr = response.getOutputStream();// 建立
            buff = new BufferedOutputStream(outSTr);
            for (String s : list) {
                write.append(s);
                write.append(enter);
            }
            buff.write(write.toString().getBytes(StandardCharsets.UTF_8));
            buff.flush();
            buff.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert buff != null;
                buff.close();
                outSTr.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
