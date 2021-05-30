package cn.stylefeng.guns.sys.core.exception.aop;

import cn.stylefeng.guns.sys.core.auth.util.RedisUtil;
import cn.stylefeng.guns.sys.core.exception.SystemApiException;
import cn.stylefeng.guns.sys.core.exception.inter.AccessLimit;
import cn.stylefeng.guns.sys.core.util.IpUtils;
import cn.stylefeng.guns.sys.core.util.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * <p></p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2021/3/2
 * @since JDK 1.8
 */
public class AccessLimitInterceptor  implements HandlerInterceptor {
    @Resource
    private RedisTemplate<String, Integer> redisTemplate;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try{
            // Handler 是否为 HandlerMethod 实例
            if(handler instanceof HandlerMethod){
                // 强转
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                // 获取方法
                Method method = handlerMethod.getMethod();
                // 是否有AccessLimit注解
                if(!method.isAnnotationPresent(AccessLimit.class)){
                    return true;
                }
                // 获取注解内容信息
                AccessLimit accessLimit = method.getAnnotation(AccessLimit.class);
                if(accessLimit == null){
                    return true;
                }
                int times = accessLimit.times();//请求次数
                int second = accessLimit.second();//请求时间范围
                //根据 IP + API 限流
                String key = IpUtils.getIpAddr(request) + request.getRequestURI();
                //根据key获取已请求次数
                Integer maxTimes = (Integer) redisUtil.get(key);
                if(maxTimes == null){
                    //set时一定要加过期时间
                    redisUtil.set(key, 1, second);
                }else if(maxTimes < times){
                    redisUtil.set(key, maxTimes+1, second);
//                    redisTemplate.opsForValue().set(key, maxTimes+1, second, TimeUnit.SECONDS);
                }else{
                    // 30405 API_REQUEST_TOO_MUCH  请求过于频繁
//                    RequestUtils.out(response, ResultUtils.error(Code.API_REQUEST_TOO_MUCH));
//                    return false;
                    throw new SystemApiException(2, "请求过于频繁","",false);
                }
            }
        }catch (Exception e){
            throw new Exception(e.getMessage());
//            log.error("API请求限流拦截异常,请检查Redis是否开启!",e);
//            throw new GlobalException(Code.BAD_REQUEST,e.getMessage());
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
