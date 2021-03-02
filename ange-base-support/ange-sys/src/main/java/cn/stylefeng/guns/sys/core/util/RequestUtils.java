package cn.stylefeng.guns.sys.core.util;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletResponse;
import java.io.PrintWriter;

/**
 * <p></p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2021/3/2
 * @since JDK 1.8
 */
public class RequestUtils {
    /**
     * @Title: out
     * @Description:  response输出JSON数据
     * @param response : 响应请求
     * @param object: object
     * @return void
     **/
    public static void out(ServletResponse response, Object object){
        PrintWriter out = null;
        try {
            response.setContentType("application/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            out = response.getWriter();
//            out.println(JSONObject.fromObject(resultMap).toString());
        } catch (Exception e) {
//            log.error("输出JSON报错!"+e);
        }finally{
            if(null != out){
                out.flush();
                out.close();
            }
        }
    }
}
