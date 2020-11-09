package cn.stylefeng.guns.webApi.huanying;

import cn.hutool.core.io.file.FileReader;
import cn.stylefeng.guns.base.consts.ConstantsContext;
import cn.stylefeng.guns.sys.core.auth.util.BloomFilterHelper;
import cn.stylefeng.guns.sys.core.auth.util.RedisUtil;
import cn.stylefeng.guns.sys.modular.system.entity.Dict;
import cn.stylefeng.guns.sys.modular.system.service.DictService;
import cn.stylefeng.roses.core.util.HttpContext;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Charsets;
import com.google.common.hash.Funnel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
@RequestMapping("/api1/v4")
public class HuanYingV4Controller {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private DictService dictService;

    @RequestMapping("/phonemodle")
    @ResponseBody
    public String phonemodle(){
        Map map = new HashMap<String, String>();
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
    public String apptoken(){
        Map<String, String[]> cookies = HttpContext.getRequest().getParameterMap();
        String sign = null;
        String app_version = null;
        String app_pkg = null;
        for (Map.Entry<String, String[]> m : cookies.entrySet()) {
            if (m.getKey().equals("sign")){
                sign = String.join("", m.getValue());
            }
            if (m.getKey().equals("app_pkg")){
                app_pkg = String.join("", m.getValue());
            }
            if (m.getKey().equals("app_version")){
                app_version = String.join("", m.getValue());
            }
        }
        boolean isHave = false;
        List<Dict> dictss = dictService.listDictsByCodeByRedis("HUANYINGAPP");
        for (Dict dict : dictss){
            if (dict.getCode().equals(sign)){
                isHave = true;
                break;
            }
        }
        if (ConstantsContext.getPirateOpen()&&!isHave){
            Map map = new HashMap<String, String>();
            map.put("data", "r52fVRuqRKZwpOlKry70HdVfpYuzMiujTHxkvoloZLDYJbZYQroqqwghonFtEyWoNbyR205ZbXtKxLXKqzZjh0eojOuyEYXn/UAlFJ3m4/HgC4AWiYrsLQ3hZnehbSC54rzqCCEgA/o4BLCnFp0Bo4vjQo+HKdAoJGl4N5i+DdA2F0mSdt0A5ucLEbuWA88CXrNGl5cKwIlDfUmsRO1LXKxF6i6fCPaJgLAjoR0UufWfdBtU6lLJTZpEgqZRr/oXyKOTa/wSYODT1UeiDR2ISmxW87Bah8Vhz765S6C6rDxBxMmO+LO5fC8mVCJxUYqao7uHCo+4az4iOyqEPyVP/S2VWT9xYAXQDyIbjPxZZ6PGuq/lBRkuGCLAxtHeMTGPIxT6tcG6AzN7VVxl7czvLDOmi25Zb9DHBHl1lRSotJcNjlXJbjO1aOulDhgvMbxodu+GNftwUmjH4ToNuo20Bu2YforldLBimquzE9uoVX3vV2u8QiRjQrc6iOcJJ91iqQly7RWkedP4c6va+YkV7UbB04xa6JvJAPrg16KkPQOaWjaZ3ZmTg3cmGqL7SDW0UZBrxPyCTep/ggiW4CKBXKjR5Q93Pe6A3EJlJ31anptQa04iOd5X7FcJ7W7YmFEyRbmH9gQ5aSJWiuYtQTdnmUj7Y09GUUeyUd0+Ttu839OIihfYDSPyfQAO633WW");
            map.put("message", "ok");
            map.put("code", 200);
            JSONObject json = new JSONObject(map);
            return json.toString();
        }
        Map map = new HashMap<String, String>();
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
//
//
//    @PostMapping(value = "/addEmailToBloom", produces = "application/json")
//    public ResponseEntity<String> addUser(@RequestBody String params) {
//        ResponseEntity<String> response = null;
//        String returnResultStr;
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//        Map<String, Object> result = new HashMap<>();
//        try {
//            JSONObject requestJsonObj = JSON.parseObject(params);
//            UserVO inputUser = getUserFromJson(requestJsonObj);
//            BloomFilterHelper<String> myBloomFilterHelper = new BloomFilterHelper<>((Funnel<String>)(from, into) -> into.putString(from, Charsets.UTF_8).putString(from, Charsets.UTF_8), 1500000, 0.00001);
//            redisUtil.addByBloomFilter(myBloomFilterHelper, "email_existed_bloom", inputUser.getEmail());
//            result.put("code", HttpStatus.OK.value());
//            result.put("message", "add into bloomFilter successfully");
//            result.put("email", inputUser.getEmail());
//            returnResultStr = JSON.toJSONString(result);
//            logger.info("returnResultStr======>" + returnResultStr);
//            response = new ResponseEntity<>(returnResultStr, headers, HttpStatus.OK);
//        } catch (Exception e) {
//            logger.error("add a new product with error: " + e.getMessage(), e);
//            result.put("message", "add a new product with error: " + e.getMessage());
//            returnResultStr = JSON.toJSONString(result);
//            response = new ResponseEntity<>(returnResultStr, headers, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        return response;
//    }
//
//    @PostMapping(value = "/checkEmailInBloom", produces = "application/json")
//    public ResponseEntity<String> findEmailInBloom(@RequestBody String params) {
//        ResponseEntity<String> response = null;
//        String returnResultStr;
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//        Map<String, Object> result = new HashMap<>();
//        try {
//            JSONObject requestJsonObj = JSON.parseObject(params);
//            UserVO inputUser = getUserFromJson(requestJsonObj);
//            BloomFilterHelper<String> myBloomFilterHelper = new BloomFilterHelper<>((Funnel<String>)(from, into) -> into.putString(from, Charsets.UTF_8).putString(from, Charsets.UTF_8), 1500000, 0.00001);
//            boolean answer = redisUtil.includeByBloomFilter(myBloomFilterHelper, "email_existed_bloom", inputUser.getEmail());
//            logger.info("answer=====" + answer);
//            result.put("code", HttpStatus.OK.value());
//            result.put("email", inputUser.getEmail());
//            result.put("exist", answer);
//            returnResultStr = JSON.toJSONString(result);
//            logger.info("returnResultStr======>" + returnResultStr);
//            response = new ResponseEntity<>(returnResultStr, headers, HttpStatus.OK);
//        } catch (Exception e) {
//            logger.error("add a new product with error: " + e.getMessage(), e);
//            result.put("message", "add a new product with error: " + e.getMessage());
//            returnResultStr = JSON.toJSONString(result);
//            response = new ResponseEntity<>(returnResultStr, headers, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        return response;
//    }


}
