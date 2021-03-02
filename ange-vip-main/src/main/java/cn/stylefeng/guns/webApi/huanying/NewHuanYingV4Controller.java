package cn.stylefeng.guns.webApi.huanying;

import cn.hutool.core.io.file.FileReader;
import cn.stylefeng.guns.base.consts.ConstantsContext;
import cn.stylefeng.guns.modular.appPower.service.AppPowerService;
import cn.stylefeng.guns.sys.core.auth.util.RedisUtil;
import cn.stylefeng.guns.sys.core.util.AESECBUtil;
import cn.stylefeng.guns.sys.core.util.CustomEnAndDe;
import cn.stylefeng.guns.sys.modular.system.entity.Dict;
import cn.stylefeng.guns.sys.modular.system.service.DictService;
import cn.stylefeng.guns.webApi.huanying.model.result.AppTokenResult;
import cn.stylefeng.guns.webApi.huanying.model.result.AppUrlResult;
import cn.stylefeng.roses.core.util.HttpContext;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p></p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2020/7/16
 * @since JDK 1.8
 */
@Controller
@RequestMapping("api/v4")
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

    @RequestMapping("/appurl")
    @ResponseBody
    public String appurl(){
        String pkgId = HttpContext.getRequest().getParameter("pkgid");
        Map<String, Object> map = new HashMap<>();
        //微信
        if ("E19156B0683CD93E".equals(pkgId)){
            AppUrlResult appUrlResult = new AppUrlResult("e19156b0683cd93e","https://dldir1.qq.com/weixin/android/weixin7022android1820.apk",1);
            String aaa = AESECBUtil.Encrypt(JSON.toJSONString(appUrlResult), "0b31c497990cc6ee");
            assert aaa != null;
            aaa = aaa.replaceAll("\r|\n", "");
            map.put("data",aaa);
        }
        //抖音
        if ("27B851B548BA47A9".equals(pkgId)){
            AppUrlResult appUrlResult = new AppUrlResult("27b851b548ba47a9","https://www-public-static.oss-cn-beijing.aliyuncs.com/douyin.apk",2);
            String aaa = AESECBUtil.Encrypt(JSON.toJSONString(appUrlResult), "0b31c497990cc6ee");
            assert aaa != null;
            aaa = aaa.replaceAll("\r|\n", "");
            map.put("data",aaa);
        }
        map.put("message", "success");
        map.put("code", 200);
        JSONObject json = new JSONObject(map);
        return json.toString();
    }

    @RequestMapping("/apptoken")
    @ResponseBody
    public String apptoken(@RequestHeader(value = "User-Token", required = false) String token){
        String app_pkg = HttpContext.getRequest().getParameter("app_pkg");
        String app_version = HttpContext.getRequest().getParameter("app_version");
        String appversioncode = HttpContext.getRequest().getParameter("appversioncode");
        token = "823C016854EADD58D80ADFEBCFF3C935DDB00D7E1DD66B3A3226D6494ED81E8D99303A5C271A63B597CFE1E75D404A72D7161ADDB4A0A7F5090EA21CCCEC62B3E0BE5EC8088625711C80AA7E7D5037FA18E81559D6910E721BE1CAE787136E7FB93DDF5FA96FD1FDF16384F6CA44A4BC5144ADF76CE501E1";
        //应用名称
//        String virtualId = HttpContext.getRequest().getParameter("virtual_id");
        String virtualId = "18AEB64F337F9CD63528DC262B7417704B4BB374C3C4C1EB";
//        String application = HttpContext.getRequest().getParameter("an");
        String application = "3D2762F32E1EB552EDADBF98DE0914ED";
        String sign;
        String applicationName = null;
        if (StringUtils.isEmpty(virtualId)||StringUtils.isEmpty(token)){
            return null;
        }else {
            String deSign = CustomEnAndDe.deCrypto(token);
            if (StringUtils.isNotEmpty(application)){
                applicationName = CustomEnAndDe.deCrypto(application);
            }
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

        boolean isShow = appPowerService.whetherLegalBySignAndAppCodeNoInsert(sign,applicationName,virtualId);
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
            List<Dict> dicts = new ArrayList<>();
            if (appversioncode.equals("129")){
                dicts = dictService.listDictsByCodeByRedis("HUANYING_TOKEN129");
            }else if (appversioncode.equals("131")){
                dicts = dictService.listDictsByCodeByRedis("HUANYING_TOKEN129");
            }else {
                dicts = dictService.listDictsByCodeByRedis("HUANYING_TOKEN");
            }

            boolean isChu = false;
            for (Dict dict : dicts){
                if (dict.getName().equals(app_version)){
                    if (appversioncode.equals("129")||appversioncode.equals("131")){
                        String appkey = dict.getDescription();
                        if (StringUtils.isEmpty(appkey)){
                            appkey = "Abandoned";
                        }
                        AppTokenResult appTokenResult = new AppTokenResult(appkey,dict.getSort(),dict.getCode(),System.currentTimeMillis());
                        String aaa = AESECBUtil.Encrypt(JSON.toJSONString(appTokenResult), "0b31c497990cc6ee");
                        assert aaa != null;
                        aaa = aaa.replaceAll("\r|\n", "");
                        map.put("data", aaa);
                    }else {
                        map.put("data", dict.getCode());
                    }
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
