package cn.stylefeng.guns.webApi.jiaotu;

import cn.stylefeng.guns.sys.core.util.CreateNamePicture;
import cn.stylefeng.guns.sys.modular.system.model.params.DictParam;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/jt/locating")
public class LocatingController {

    @RequestMapping("/User/login")
    @ResponseBody
    public JSONObject userLogin() {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> map1 = new HashMap<>();
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

    @RequestMapping("/User/Info")
    @ResponseBody
    public JSONObject Info(){
        Map<String, Object> map = new HashMap<>();
        List<String> a = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("id",122897);
        map1.put("name","Qrg93844473");
        map1.put("header","");
        map1.put("ph",0);
        map1.put("etm",1878183608);
        map1.put("inv",0);
        map1.put("ifc",1);
        map1.put("ul",0);
        map1.put("closedviptry","");
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
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("Id",11);
        map1.put("Title","春节放假");
        map1.put("Content","春节放假：2021年2月9日至3月1日，期间无客服，给您造成的不便深表歉意");
        map1.put("StartTime","2020-10-23T00:00:00");
        map1.put("EndTime","2023-10-04T00:00:00");
        map1.put("Enable",true);
        map1.put("CreateTime","2020-10-23T14:15:15Z");
        map1.put("UpdateTime","2021-02-09T18:40:45Z");
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
        Map<String, Object> map = new HashMap<>();
        List<String> a = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("rechargeTopText","特别提示：如果首次使用请先测试，以免给您造成不必要的麻烦和损失。");
        map1.put("firstChargePrice","5");
        map1.put("js","javascript:(function() {  try{document.getElementById('alipaysubmit').style.display='none';}catch(errr){}})()");
        map1.put("appstartip","用微信登录，也要将微信添加进来");
        map1.put("Alert64bit","此应用的当前应用版本不支持");
        map1.put("pointout","感谢您选择本软件.如果发现无法使用，你可以更新到最新版本尝试");
        map1.put("sndecimal","6,8,10,12");
        map1.put("connectwechat","https://weixin.qq.com/m");
        map1.put("Topbanner","");
        map1.put("mssptype","2,2,2,2");
        map1.put("isWakeUpThe","5");
        map.put("data",map1);
        map.put("error", 0);
        map.put("message", "");
        map.put("type", 0);
        JSONObject json = new JSONObject(map);
        return json;
    }

    @RequestMapping("/config/appmenus")
    @ResponseBody
    public JSONObject appmenus(){
        Map<String, Object> map = new HashMap<>();
        map.put("data",null);
        map.put("error", 0);
        map.put("message", "");
        map.put("type", 0);
        JSONObject json = new JSONObject(map);
        return json;
    }

    @RequestMapping("/config/AdBanners")
    @ResponseBody
    public JSONObject adBanners(){
        Map<String, Object> map = new HashMap<>();
        map.put("data",null);
        map.put("error", 0);
        map.put("message", "");
        map.put("type", 0);
        JSONObject json = new JSONObject(map);
        return json;
    }

    @RequestMapping("/Config/Update")
    @ResponseBody
    public JSONObject update(){
        Map<String, Object> map = new HashMap<>();
        List<String> a = new ArrayList<>();
        map.put("error", 0);
        map.put("message", "没有更新");
        JSONObject json = new JSONObject(map);
        return json;
    }

    @RequestMapping("/user/GetTurnPlateRewardSetting")
    @ResponseBody
    public JSONObject getTurnPlateRewardSetting(){
        Map<String, Object> map = new HashMap<>();
        map.put("data",null);
        map.put("error", 0);
        map.put("message", "");
        map.put("type", 0);
        JSONObject json = new JSONObject(map);
        return json;
    }

}
