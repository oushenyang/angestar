package cn.stylefeng.guns.modular.remote.controller;

import cn.stylefeng.guns.sys.core.auth.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 *
 * @Author liuzongqiang
 * @Date 2019/7/25 0025 15:54
 * @Version 1.0
 **/
@Controller
@RequestMapping("/ap")
public class TestRedisController {
    private final RedisUtil redisUtil;

    private final RedisTemplate<String, String> redisTemplate;// 通过构造方法注入该对象

    public TestRedisController(RedisUtil redisUtil, RedisTemplate<String, String> redisTemplate) {
        this.redisUtil = redisUtil;
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/getData")
    public Object getData(){
        //测试redis

        redisUtil.set("name","小猫");
        System.out.println(redisUtil.getExpire("name"));
        return redisUtil.get("name");

    }
}
