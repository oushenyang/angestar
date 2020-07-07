package cn.stylefeng.guns.webApi.dingweimao;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.core.constant.state.RedisType;
import cn.stylefeng.guns.modular.apiManage.entity.ApiManage;
import cn.stylefeng.guns.modular.apiManage.service.ApiManageService;
import cn.stylefeng.guns.sys.core.auth.util.RedisUtil;
import cn.stylefeng.guns.sys.core.util.CreateNamePicture;
import cn.stylefeng.guns.sys.core.util.GPS;
import cn.stylefeng.guns.sys.core.util.GPSConverterUtils;
import cn.stylefeng.roses.core.util.HttpContext;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * <p>定位猫</p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2020/5/28
 * @since JDK 1.8
 */
@Controller
@RequestMapping("/api/cat/config")
public class DingWeiMaoController {

    @RequestMapping("/Getlocations")
    @ResponseBody
    public Object cardLogin() throws Exception {
        Map<String, String[]> cookies = HttpContext.getRequest().getParameterMap();
        String aaa = null;
        for (Map.Entry<String, String[]> m : cookies.entrySet()) {
            System.out.println("key:" + m.getKey() + " value:" + String.join("", m.getValue()));
            if (m.getKey().equals("p")){
                String.join("", m.getValue());
                String a = CreateNamePicture.decrypt(String.join("", m.getValue()), Charset.forName("utf8"), "00122897");
                JSONObject jb = JSONObject.parseObject(a);
                GPS aps = GPSConverterUtils.Bd09ToGcj02(jb.getLongValue("latitude"),jb.getLongValue("longitude"));
                aaa = CreateNamePicture.encrypt(String.join("",JSON.toJSONString(aps)), Charset.forName("utf8"), "00122897");
            }
        }
        return "{\"error\":0,\"message\":\"\",\"type\":0,\"data\":{\"x\":"+aaa+"}}";
    }
}
