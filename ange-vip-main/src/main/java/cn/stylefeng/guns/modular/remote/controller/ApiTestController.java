package cn.stylefeng.guns.modular.remote.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.stylefeng.guns.sys.core.constant.state.RedisType;
import cn.stylefeng.guns.modular.app.service.AppInfoService;
import cn.stylefeng.guns.modular.device.entity.Token;
import cn.stylefeng.guns.modular.remote.entity.RemoteData;
import cn.stylefeng.guns.modular.remote.service.RemoteDataService;
import cn.stylefeng.guns.sys.core.auth.util.RedisUtil;
import cn.stylefeng.roses.core.util.HttpContext;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

import static cn.stylefeng.roses.core.util.HttpContext.getIp;

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
        String tokenStr = "1";
        Date date = new Date();
        Token token = new Token();
        token.setAppId(11L);
        token.setCardOrUserId(11L);
        token.setCardOrUserCode("22");
        token.setCardType(1);
        token.setMac("mac");
        token.setModel("model");
        token.setIp(getIp());
        token.setLoginNum(1);
        token.setToken(tokenStr);
        token.setLoginTime(date);
        token.setCheckTime(date);
        token.setCreateTime(date);
        redisUtil.hset(RedisType.TOKEN.getCode(),tokenStr+"1",token,3000);
        redisUtil.hset(RedisType.TOKEN.getCode(),tokenStr+"2",token,2000);
        redisUtil.hset(RedisType.TOKEN.getCode(),tokenStr+"3",token,1000);
//        redisUtil.hdel(RedisType.TOKEN.getCode(),"11","12","13","113");

        Map<Object, Object> objects = redisUtil.hmget(RedisType.TOKEN.getCode());
        List<Token> tokens = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(objects)){
            for (Map.Entry<Object, Object> m : objects.entrySet()) {
                tokens.add((Token)m.getValue());
            }
        }
        for (int i = 0; i < 2; i++) {
            redisUtil.hdel(RedisType.TOKEN.getCode(), tokens.get(i).getToken()+i);
        }
        return tokens;
    }
}
