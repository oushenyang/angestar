package cn.stylefeng.guns.webApi.card;

import cn.stylefeng.guns.modular.remote.entity.RemoteData;
import cn.stylefeng.roses.core.util.HttpContext;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * <p>单码用户登录</p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2020/5/28
 * @since JDK 1.8
 */
@Controller
@RequestMapping("/cardLogin")
public class CardLoginController {

    @RequestMapping("/{appId}")
    @ResponseBody
    public Object cardLogin(@PathVariable String appId) {
        Map<String, String[]> cookies = HttpContext.getRequest().getParameterMap();
        System.out.println(cookies);
        return appId;
    }
}
