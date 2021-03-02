package cn.stylefeng.guns.webApi.dingweimao;

import cn.stylefeng.guns.base.consts.ConstantsContext;
import cn.stylefeng.guns.modular.appPower.service.AppPowerService;
import cn.stylefeng.guns.sys.core.util.CreateNamePicture;
import cn.stylefeng.guns.sys.core.util.CustomEnAndDe;
import cn.stylefeng.guns.sys.core.util.GPS;
import cn.stylefeng.guns.sys.core.util.GPSConverterUtils;
import cn.stylefeng.guns.sys.modular.system.entity.Dict;
import cn.stylefeng.guns.sys.modular.system.model.params.DictParam;
import cn.stylefeng.guns.sys.modular.system.service.DictService;
import cn.stylefeng.roses.core.util.HttpContext;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
@RequestMapping("/api/cat")
public class DingWeiMaoController {

    @Autowired
    private DictService dictService;
    @Autowired
    private AppPowerService appPowerService;

    @RequestMapping("/decrypt")
    @ResponseBody
    public ResponseData decrypt(DictParam dictParam) {
        try {
            String a = CreateNamePicture.decrypt(dictParam.getSecretStr(), Charset.forName("utf8"), dictParam.getSecret());
            return ResponseData.success(a);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseData.success();
    }

    @RequestMapping("/User/login")
    @ResponseBody
    public JSONObject login(){
        Map map = new HashMap<String, String>();
        Map map1 = new HashMap<String, String>();
        map1.put("accessToken", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJqdGkiOiI0NTAwZTJhNi1jNThlLTRmZjQtOTViNy00MDQ3NWI0ZmNiYmQiLCJpYXQiOiI3LzgvMjAyMCA1OjEyOjM3IEFNIiwibmFtZWlkIjoiMTIyODk3IiwidHlwIjoiMSIsIm5iZiI6MTU5NDE4NTE1NywiZXhwIjoxNTk0Nzg5OTU3LCJpc3MiOiJ0ZXN0X24iLCJhdWQiOiJ0ZXN0X24ifQ.6vV4Md4IVsvT5rmRO3bXlWoNwW4bgP0eLjjM2V3TX7Q");
        map1.put("tokenType", "Bearer");
        map1.put("am", 0);
        map.put("data",map1);
        map.put("error", 0);
        map.put("message", "");
        map.put("type", 0);
        JSONObject json = new JSONObject(map);
        return json;
    }

    @RequestMapping("/config/GetAnnouncementList")
    @ResponseBody
    public JSONObject GetAnnouncementList(){
        Map map = new HashMap<String, String>();
        List<String> a = new ArrayList<>();
        map.put("data",a);
        map.put("error", 0);
        map.put("message", "");
        map.put("type", 0);
        JSONObject json = new JSONObject(map);
        return json;
    }

    @RequestMapping("/Config/Update")
    @ResponseBody
    public JSONObject Update(){
        Map map = new HashMap<String, String>();
        List<String> a = new ArrayList<>();
        map.put("error", 0);
        map.put("message", "没有更新");
        JSONObject json = new JSONObject(map);
        return json;
    }

    @RequestMapping("/User/Info")
    @ResponseBody
    public JSONObject Info(){
        Map map = new HashMap<String, String>();
        List<String> a = new ArrayList<>();
        Map map1 = new HashMap<String, String>();
        map1.put("id",122897);
        map1.put("name","Qrg93844473");
        map1.put("header","");
        map1.put("ph",0);
        map1.put("etm",1878183608);
        map1.put("inv",0);
        map1.put("ifc",1);
        map1.put("closedviptry","");
        map.put("data",map1);
        map.put("error", 0);
        map.put("message", "");
        map.put("type", 0);
        JSONObject json = new JSONObject(map);
        return json;
    }

    @RequestMapping("/Config/ConfigInfo")
    @ResponseBody
    public JSONObject ConfigInfo(){
        Map map = new HashMap<String, String>();
        List<String> a = new ArrayList<>();
        Map map1 = new HashMap<String, String>();
        map1.put("serverWechat","dingweixiaoshenqi");
        map1.put("faq","https://www.666sdk.com/faq.html");
        map1.put("bottomText","产品遇到问题，咨询微信客服");
        map1.put("faq_heixia","https://www.666sdk.com/heixiafaq.html");
        map1.put("pointout","本应用不支持王者荣耀等系列游戏，请注意封号风险");
        map1.put("Alert64bit","您的手机暂不支持64位版本微信位置修改，请到官网免费下载32位版本，覆盖安装后重试");
        map1.put("banner","https://ncov.dxy.cn/ncovh5/view/pneumonia?scene=1&amp;clicktime=1579582559&amp;enterid=1579582559&amp;from=timeline&amp;isappinstalled=0&amp;entry=menu");
        map1.put("sndecimal","6,8,10,12");
        map1.put("connectwechat","https://weixin.qq.com/m");
        map1.put("zhifubao","https://cache.amap.com/activity/partner/plague-map/index.html?isjump=false&amp;fromchannel=alijk");
        map1.put("UserAppShareDownUrl","https://dingweimao.lanzous.com/icnpz4j");
        map1.put("UserAppShareMessage","修改定位神器");
        map1.put("UserAppSharePage","http://api.666sdk.com/activity/Invite");
        map.put("data",map1);
        map.put("error", 0);
        map.put("message", "");
        map.put("type", 0);
        JSONObject json = new JSONObject(map);
        return json;
    }

    @RequestMapping("/User/AppRecord")
    @ResponseBody
    public JSONObject AppRecord(){
        Map map = new HashMap<String, String>();
        map.put("error", 0);
        map.put("message", "");
        JSONObject json = new JSONObject(map);
        return json;
    }

    @RequestMapping("/config/AppBlackList")
    @ResponseBody
    public JSONObject AppBlackList(@RequestBody String  body){
        Map<String, String[]> cookies = HttpContext.getRequest().getParameterMap();
        String av = null;
        String appName = null;
        for (Map.Entry<String, String[]> m : cookies.entrySet()) {
            if (m.getKey().equals("av")){
                av = String.join("", m.getValue());
            }
            if (m.getKey().equals("n")){
                appName = String.join("", m.getValue());
            }
        }
        JSONObject jbb = JSONObject.parseObject(body);
        String pkg = jbb.getString("apppackagename");
        String version = jbb.getString("appversionname");
        String token = HttpContext.getRequest().getParameter("c");
        String application = HttpContext.getRequest().getParameter("bundleid");
        String sign;
        String applicationName = null;
        if (StringUtils.isEmpty(token)){
            return null;
        }else {
            String deSign = CustomEnAndDe.deCrypto(token);
//            if (StringUtils.isNotEmpty(application)){
//                applicationName = CustomEnAndDe.deCrypto(application);
//            }
            sign = deSign.substring(0,deSign.length()-8);
        }
        boolean whetherLegal = appPowerService.whetherLegalBySignAndAppCode(sign,applicationName,CustomEnAndDe.enCrypto(appName),"dingweimao172");
        if (StringUtils.isNotEmpty(pkg)&&StringUtils.isNotEmpty(av)){
            if (pkg.equals("com.alibaba.android.rimet")&&av.equals("1.7.2")){

                List<Dict> dicts = dictService.listDictsByCode("dingweimao");
                boolean isHave = false;
                for (Dict dict : dicts){
                    if (dict.getName().equals(version)){
                        isHave = true;
                    }
                }
                if (whetherLegal){
                    Map map = new HashMap<String, String>();
                    List<String> a = new ArrayList<>();
                    Map map1 = new HashMap<String, String>();
                    map1.put("Level",2);
                    map1.put("Content", ConstantsContext.getPirateOpenText());
                    map.put("data",map1);
                    map.put("error", 0);
                    map.put("message", "");
                    map.put("type", 0);
                    JSONObject json = new JSONObject(map);
                    return json;
                }
                if (!isHave){
                    Map map = new HashMap<String, String>();
                    List<String> a = new ArrayList<>();
                    Map map1 = new HashMap<String, String>();
                    map1.put("Level",2);
                    map1.put("Content","您当前钉钉版本为："+version+",系统未做防检测处理！请不要打卡！请不要打卡！请不要打卡！钉钉签到正常操作！为防止此提示再次出现，请务必关闭钉钉自动更新，具体操作在手机应用商店里把软件自动更新关闭！");
                    map.put("data",map1);
                    map.put("error", 0);
                    map.put("message", "");
                    map.put("type", 0);
                    JSONObject json = new JSONObject(map);
                    return json;
                }
            }
        }


        Map map = new HashMap<String, String>();
        List<String> a = new ArrayList<>();
        Map map1 = new HashMap<String, String>();
        map1.put("Level",0);
        map1.put("Content",null);
        map.put("data",map1);
        map.put("error", 0);
        map.put("message", "");
        map.put("type", 0);
        JSONObject json = new JSONObject(map);
        return json;
    }

    @RequestMapping("/config/Getlocations")
    @ResponseBody
    public JSONObject Getlocations(@RequestBody String  body) throws Exception {
        Map<String, String[]> cookies = HttpContext.getRequest().getParameterMap();
        String appName = null;
        for (Map.Entry<String, String[]> m : cookies.entrySet()) {
            if (m.getKey().equals("n")){
                appName = String.join("", m.getValue());
            }
        }

        String token = HttpContext.getRequest().getParameter("c");
        String application = HttpContext.getRequest().getParameter("bundleid");
        String sign;
        String applicationName = null;
        if (StringUtils.isEmpty(token)){
            return null;
        }else {
            String deSign = CustomEnAndDe.deCrypto(token);
//            if (StringUtils.isNotEmpty(application)){
//                applicationName = CustomEnAndDe.deCrypto(application);
//            }
            sign = deSign.substring(0,deSign.length()-8);
        }
        boolean whetherLegal = appPowerService.whetherLegalBySignAndAppCode(sign,applicationName,CustomEnAndDe.enCrypto(appName),"dingweimao172");
        if (StringUtils.isNotEmpty(appName)){
//            boolean isHave = true;
//            List<Dict> dicts = dictService.listDictsByCode("DINGWEIMAOAPP");
//            for (Dict dict : dicts){
//                if (dict.getCode().equals(appName)){
//                    isHave = true;
//                }
//            }
            if (ConstantsContext.getPirateOpenLocation()&&whetherLegal){
                Map map = new HashMap<String, String>();
                Map map1 = new HashMap<String, String>();
                map1.put("x", "76AE7193806A4E04F6D2EAE0D9488F7CA4B9BCBA8E0C8F97BDAB4E2FC885A74D6F43F43BE6C8C0C725414B6C5797C17D66454611D7F34EC40C1C724BA7555C6D");
                map.put("data",map1);
                map.put("error", 0);
                map.put("message", "");
                map.put("type", 0);
                JSONObject json = new JSONObject(map);
                return json;
            }
        }else {
            Map map = new HashMap<String, String>();
            Map map1 = new HashMap<String, String>();
            map1.put("x", "76AE7193806A4E04F6D2EAE0D9488F7CA4B9BCBA8E0C8F97BDAB4E2FC885A74D6F43F43BE6C8C0C725414B6C5797C17D66454611D7F34EC40C1C724BA7555C6D");
            map.put("data",map1);
            map.put("error", 0);
            map.put("message", "");
            map.put("type", 0);
            JSONObject json = new JSONObject(map);
            return json;
        }
        String aaa = null;
        JSONObject jbb = JSONObject.parseObject(body);
        String p = jbb.getString("p");
        String a = CreateNamePicture.decrypt(p, Charset.forName("utf8"), "00122897");
        JSONObject jb = JSONObject.parseObject(a);
        String pkg = jb.getString("pkg");
        String version = jb.getString("vn");
        if (StringUtils.isNotEmpty(pkg)){
            if (pkg.equals("com.alibaba.android.rimet")){
                GPS aps = GPSConverterUtils.Bd09ToGcj02(jb.getDoubleValue("latitude"),jb.getDoubleValue("longitude"));
                List<Dict> dicts = dictService.listDictsByCode("dingweimao");
                List<Dict> dicts1 = dictService.listDictsByCode("dingweimao1");
                for (Dict dict : dicts){
                    if (dict.getName().equals(version)){
                        aps.setW(dict.getCode());
                        aps.setT(dict.getCreateTime().getTime());
                    }
                }
                for (Dict dict : dicts1){
//                        if (dict.getName().equals(version)){
                    aps.setW1(dict.getCode());
                    aps.setT1(dict.getCreateTime().getTime());
//                        }
                }
                aaa = CreateNamePicture.encrypt(JSON.toJSONString(aps), Charset.forName("utf8"), "00122897");
            }else{
                GPS aps = GPSConverterUtils.Bd09ToGcj02(jb.getDoubleValue("latitude"),jb.getDoubleValue("longitude"));
                aaa = CreateNamePicture.encrypt(JSON.toJSONString(aps), Charset.forName("utf8"), "00122897");
            }
        }else {
            GPS aps = GPSConverterUtils.Bd09ToGcj02(jb.getDoubleValue("latitude"),jb.getDoubleValue("longitude"));
            aaa = CreateNamePicture.encrypt(JSON.toJSONString(aps), Charset.forName("utf8"), "00122897");
        }
        Map map = new HashMap<String, String>();
        Map map1 = new HashMap<String, String>();
        map1.put("x", aaa);
        map.put("data",map1);
        map.put("error", 0);
        map.put("message", "");
        map.put("type", 0);
        JSONObject json = new JSONObject(map);
        return json;
    }
}
