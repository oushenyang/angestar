package cn.stylefeng.guns.webApi.huanying;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
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
@RequestMapping("/api/v3")
public class HuanYingV3Controller {

    @RequestMapping("/user")
    @ResponseBody
    public String user(){
        Map map = new HashMap<String, String>();
        map.put("data","+8QmNNNLGKt6xBXiMKrVZzaE/mvEzil5OtXpVs4FPUiN6OT71mRm9O11/+V/PqOn3VsiLYS3wmlxKMqkDKUffxRxCBPCWq+yjOVecbIc/fdxdy6xDGIBm69b1YnLlaZvO/CGMXRP14mjJN7nxpIrJv/pxXGFMrdzPqBTzNGK9Nu6lJi7B6KtsAzeWnEpVQAbHBxdlo7i63Y3bs65EjgqTNDSC4q/CHcwRHDuOPPykLomjgxgGcquqrbSpGZsV5E90miPePjMmVwSM2N0/Flg1aJhWggobIAjFzpH88rqieVvMFPZLVD9+16bV431RsU6CqvnPibLx2ZORL2PLW6Fb/WYOCHQGEir3zo6xSfyhO6GdNy75q8EzWOFyEMgOPSJcxHJp4ASbAMc9BtyQZgVFDIbWezRa6O9kUFgAQF1LHYzahkn5AjI7VJn5+u0tjxntB0qQJvkwChwiLPdondUX405GQybDuiRLMPh35UvenPCpr3IHhPlvYh0da9CZ8v5fppsNSu4uKLkR2QI9GfO8kUM0VtD9wdo+5HiJXRrtkJj9XaPrsAGFO3YRqtqAthbWS9jllwOe8a2xY//3ruVaZwq0wQQwccGxAUtSCmxxtS2tt/ENJUE4uuzeFYX+nZc5OWmETR7suQ6CANcvPbfEItOu+C/1kbyIJvCo4PPGcN4aS0heEQlCwoE28W9gcDy");
        map.put("message", "ok");
        map.put("code", 200);
        JSONObject json = new JSONObject(map);
        return json.toString();
    }

    @RequestMapping("/now")
    @ResponseBody
    public String now(){
        Map map = new HashMap<String, String>();
        Map map1 = new HashMap<String, String>();
        map1.put("now", new Date().getTime());
        map.put("data",map1);
        map.put("message", "ok");
        map.put("code", 0);
        JSONObject json = new JSONObject(map);
        return json.toString();
    }

    @RequestMapping("/appactive")
    @ResponseBody
    public String appactive(){
        Map map = new HashMap<String, String>();
        Map map1 = new HashMap<String, String>();
        map1.put("content", "某钉用户请确保幻影分身为最新版");
        map1.put("title", "紧急通知");
        map1.put("type", "");
        map.put("data",map1);
        map.put("message", "ok");
        map.put("code", 0);
        JSONObject json = new JSONObject(map);
        return json.toString();
    }

    @RequestMapping("/stepinfo")
    @ResponseBody
    public String stepinfo(){
        Map map = new HashMap<String, String>();
        map.put("data","");
        map.put("message", "no data in database");
        map.put("code", 199);
        JSONObject json = new JSONObject(map);
        return json.toString();
    }

    @RequestMapping("/vipprice")
    @ResponseBody
    public String vipprice(){
        Map map = new HashMap<String, String>();
        Map map1 = new HashMap<String, String>();
        map1.put("vip7", "-9.9");
        map1.put("vip6", 359.9);
        map1.put("vip5", 139.9);
        map1.put("vip4", 49.9);
        map1.put("vip3", 25.9);
        map1.put("vip2", -11.9);
        map1.put("vip1", 3.9);
        map1.put("vip4_5", 89.9);
        map1.put("vip9", "-39.9");
        map1.put("vip8", "-29.9");
        map1.put("vip10", "-89.9");
        map.put("data",map1);
        map.put("message", "ok");
        map.put("code", 0);
        JSONObject json = new JSONObject(map);
        return json.toString();
    }
}
