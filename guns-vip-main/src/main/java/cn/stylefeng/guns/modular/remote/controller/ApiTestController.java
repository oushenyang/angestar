package cn.stylefeng.guns.modular.remote.controller;

import cn.stylefeng.guns.modular.app.service.AppInfoService;
import cn.stylefeng.guns.modular.remote.entity.RemoteData;
import cn.stylefeng.guns.modular.remote.service.RemoteDataService;
import cn.stylefeng.guns.sys.core.auth.util.RedisUtil;
import cn.stylefeng.roses.core.util.HttpContext;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Map;

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
    private final RemoteDataService remoteDataService;

    private final AppInfoService appInfoService;

    private final RedisUtil redisUtil;

    public ApiTestController(RemoteDataService remoteDataService, AppInfoService appInfoService,RedisUtil redisUtil) {
        this.remoteDataService = remoteDataService;
        this.appInfoService = appInfoService;
        this.redisUtil = redisUtil;
    }

    @RequestMapping("/{ownerId}")
    @ResponseBody
    public Object test(@PathVariable String ownerId) {
        Map<String, String[]> cookies = HttpContext.getRequest().getParameterMap();
        System.out.println(cookies);
        RemoteData remoteData = remoteDataService.getById(1260547698754760706L);
        boolean isJson = true;
        try {
            JSON.parse("-1");
            isJson = true;
        } catch (Exception e) {
            isJson = false;

        }
//        if (isJson){
//            return JSON.parse("-1");
//        }else {
//            return remoteData.getDataValue();
//        }
        return ownerId;
    }

    @RequestMapping("/getData")
    @ResponseBody
    public Object getData(){
        //测试redis

        redisUtil.set("name","小猫");
        System.out.println(redisUtil.getExpire("name"));
        return redisUtil.get("name");

    }
}
