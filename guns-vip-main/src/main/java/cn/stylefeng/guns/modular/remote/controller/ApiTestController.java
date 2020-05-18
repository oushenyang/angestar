package cn.stylefeng.guns.modular.remote.controller;

import cn.stylefeng.roses.core.util.HttpContext;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Enumeration;

/**
 * <p></p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2020/5/18
 * @since JDK 1.8
 */
@Controller
@RequestMapping("/api")
public class ApiTestController {
    @RequestMapping("/test")
    @ResponseBody
    public ResponseData test() {
       Enumeration<String> cookies = HttpContext.getRequest().getParameterNames();
       System.out.println(cookies);
        return ResponseData.success();
    }
}
