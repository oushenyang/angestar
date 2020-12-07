package cn.stylefeng.guns.webApi.huanying;

import cn.hutool.core.io.file.FileReader;
import cn.stylefeng.guns.base.consts.ConstantsContext;
import cn.stylefeng.guns.modular.appPower.service.AppPowerService;
import cn.stylefeng.guns.sys.core.auth.util.RedisUtil;
import cn.stylefeng.guns.sys.core.util.CustomEnAndDe;
import cn.stylefeng.guns.sys.modular.system.entity.Dict;
import cn.stylefeng.guns.sys.modular.system.service.DictService;
import cn.stylefeng.roses.core.util.HttpContext;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p></p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2020/7/16
 * @since JDK 1.8
 */
@Controller
@RequestMapping("api1/v4")
public class NewHuanYingV4Controller {

    @Autowired
    private AppPowerService appPowerService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private DictService dictService;

    @RequestMapping("/phonemodle")
    @ResponseBody
    public String phonemodle(){
        Map<String, Object> map = new HashMap<>();
        String path = ConstantsContext.getPojieUploadPath() + "jixin.txt";
        FileReader fileReader = new FileReader(path);
        String result = fileReader.readString();
        map.put("message", "success");
        map.put("code", 200);
        map.put("data",result);
        JSONObject json = new JSONObject(map);
        return json.toString();
    }

    @RequestMapping("/apptoken")
    @ResponseBody
    public String apptoken(@RequestHeader(value = "User-Token", required = false) String token){
        String app_pkg = HttpContext.getRequest().getParameter("app_pkg");
        String app_version = HttpContext.getRequest().getParameter("app_version");
        //应用名称
        String virtualId = HttpContext.getRequest().getParameter("virtual_id");
        String sign;
        if (StringUtils.isEmpty(virtualId)||StringUtils.isEmpty(token)){
            return null;
        }else {
            String deSign = CustomEnAndDe.deCrypto(token);
//            String time =  deSign.substring(deSign.length() -7);
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMd");
//            Date date = new Date(System.currentTimeMillis());
//            String newTime = simpleDateFormat.format(date);
//            //说明盗版
//            if (!time.equals(newTime)){
//                sign = deSign;
//            }else {
                //从数据库里查是否正版
                //去除最后七位
                sign = deSign.substring(0,deSign.length()-8);
//            }
        }

        boolean isShow = appPowerService.whetherLegalBySignAndAppCodeNoInsert(sign,virtualId);
        if (ConstantsContext.getPirateOpen()&&isShow){
            Map<String, Object> map = new HashMap<>();
            map.put("data", "r52fVRuqRKZwpOlKry70HdVfpYuzMiujTHxkvoloZLDYJbZYQroqqwghonFtEyWoNbyR205ZbXtKxLXKqzZjh0eojOuyEYXn/UAlFJ3m4/HgC4AWiYrsLQ3hZnehbSC54rzqCCEgA/o4BLCnFp0Bo4vjQo+HKdAoJGl4N5i+DdA2F0mSdt0A5ucLEbuWA88CXrNGl5cKwIlDfUmsRO1LXKxF6i6fCPaJgLAjoR0UufWfdBtU6lLJTZpEgqZRr/oXyKOTa/wSYODT1UeiDR2ISmxW87Bah8Vhz765S6C6rDxBxMmO+LO5fC8mVCJxUYqao7uHCo+4az4iOyqEPyVP/S2VWT9xYAXQDyIbjPxZZ6PGuq/lBRkuGCLAxtHeMTGPIxT6tcG6AzN7VVxl7czvLDOmi25Zb9DHBHl1lRSotJcNjlXJbjO1aOulDhgvMbxodu+GNftwUmjH4ToNuo20Bu2YforldLBimquzE9uoVX3vV2u8QiRjQrc6iOcJJ91iqQly7RWkedP4c6va+YkV7UbB04xa6JvJAPrg16KkPQOaWjaZ3ZmTg3cmGqL7SDW0UZBrxPyCTep/ggiW4CKBXKjR5Q93Pe6A3EJlJ31anptQa04iOd5X7FcJ7W7YmFEyRbmH9gQ5aSJWiuYtQTdnmUj7Y09GUUeyUd0+Ttu839OIihfYDSPyfQAO633WW");
            map.put("message", "ok");
            map.put("code", 200);
            JSONObject json = new JSONObject(map);
            return json.toString();
        }
        Map<String, Object> map = new HashMap<>();
        if ("com.alibaba.android.rimet".equals(app_pkg)){
            List<Dict> dicts = dictService.listDictsByCodeByRedis("HUANYING_TOKEN");
            boolean isChu = false;
            for (Dict dict : dicts){
                if (dict.getName().equals(app_version)){
                    map.put("data", dict.getCode());
                    isChu = true;
                    break;
                }
            }
            if (!isChu){
                map.put("message", "no data in database");
                map.put("code", 199);
                map.put("data", "");
                JSONObject json = new JSONObject(map);
                return json.toString();
            }
        }else {
            map.put("data", "");
        }
        map.put("message", "ok");
        map.put("code", 200);
        JSONObject json = new JSONObject(map);
        return json.toString();
    }
}
