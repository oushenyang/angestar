package cn.stylefeng.guns.webApi.huanying;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.base.consts.ConstantsContext;
import cn.stylefeng.guns.modular.appPower.service.AppPowerService;
import cn.stylefeng.guns.sys.core.auth.util.RedisUtil;
import cn.stylefeng.guns.sys.core.constant.state.RedisType;
import cn.stylefeng.guns.sys.core.util.CustomEnAndDe;
import cn.stylefeng.guns.sys.modular.system.service.DictService;
import cn.stylefeng.guns.webApi.huanying.entity.HyApp;
import cn.stylefeng.guns.webApi.huanying.model.result.HyAppResult;
import cn.stylefeng.guns.webApi.huanying.service.HyAppService;
import cn.stylefeng.roses.core.util.HttpContext;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
@RequestMapping("api/v3")
public class NewHuanYingV3Controller {

    @Autowired
    private DictService dictService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private AppPowerService appPowerService;

    @Autowired
    private HyAppService hyAppService;

    @RequestMapping("/user")
    @ResponseBody
    public String user(@RequestHeader(value = "User-Token", required = false) String token){
        //应用名称
        String virtualId = HttpContext.getRequest().getParameter("virtual_id");
        String application = HttpContext.getRequest().getParameter("an");
        String appversioncode = HttpContext.getRequest().getParameter("appversioncode");
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
        boolean whetherLegal = appPowerService.whetherLegalBySignAndAppCodeNoInsert(sign,applicationName,virtualId);
        Map<String, Object> map = new HashMap<>();
        if (appversioncode.equals("129")){
            map.put("data", "I1tNlF6QauI+wyM6yJ37N0ZpgY1P+3RAr0XAfDZ5TnFU5Tl+PkcyEY1FdZjafD5nH0etFxacHpaBLEiPSf6DcEGVq4zC7CVEkx6iO1FnzM9P/kTTXVJJm6VQ5ZLT88uNRZn//F5VhIcQaFIiPzKJopk1XcN76geXoDdSVKYaUMZiVtCjLgzLfifeFcVWjpRwTn2C2eK8GlrC/ahMeaFo2FXCG2pWsnEtTx+7bMVho1iavEDQ8855qZolu7ZAN6BIXsui0JwaQbco5DlegOOY0vQv4jKZ7urBOPzXwuSe9rL1imfaHEiXcWjf5Bgo+hM/TOmuKpZzEMB/BhDWAXjSBnuYneLQv0EtBENuPGHrcgsBGXZ8pJXR4tRXlhsBV2ZWYNVfa1jCutlHH4kwSIbg9+JLjVdOwDC/CvaeKf2Ra4cC5o89b3oVnu3qQ0louPYnZnZzCX6CAOsI2T9vN+XPnT1EO8ZlWdROpgrSFIRBbsm6L970vyl02H8tz6mCI9BbYRhp8u0taUrF31svJ8RHnJjBlpPnGqtPIEti2Im3BnTdxteszTMvBZRfA2x9IK8EafZyFmkxr3pEIC7gpPdwlbIeQt3Gpx1q9KMi4iQMsbi5wAMOJPWrBf3jSOFVsSJP7ypMVWnxGhubz1M/+y3paNp7Tiru0wK4Sg3F8NlAIyNX42BoEjjv4LjUrZIrX3u+42SXxOeNa7lp6+0e4Lh4lsTxVzxR43NGOtDP8fclE22cfdhQVQPg2I0l+RaNFdifrtx8tpnNnu3CmNB+Gw/426CX/sCawoOZqVIoiwVE1PmIN0zch44gccCq/Ss2x4NqVBakZ/xSb0IHob8efqvLFnLb8CjbZ18/RrcJg+v8ukhYedxqtPky1XvfgD3zYIQBCacO2uxdk2Rnac2FIfrSZiAns1z7UARN58/y2bDQgv1pQEQawzqWw8LfgxXXDD8d8mCMAWgFhf0AV8Jx6PwLag==");
        }else {
            map.put("data","h9M9kQsr/d+/mG43m8PfkKl1eJGN4Jrr2/XvSj2zVs/aZpXPZgg73oJcUjlpK33BVIbdFMBuuRMr82QrX3uRBvXozAZVWs2k95uAzFGJmFNlbYXQTvhr+vzOtYaWOW0FOFrNgHV0JZDO28Fmcb8DY+l9n44wZN7syIaHl/SDFA58FBGMVZVTm9jM+ApjKInmchgxljBCEOcxyo7YsvjMFT1ILzrgMzvoaaIMuqP7B4NpPp4x2Hbf2tbKNT55CvLuL8E4ctWmIDHNmzefT7gvWgFEvQImldioI4UWdxaBcwV4Q8vv4/xv0aMBHXyuvkW1yg83i01pCEBaUDszCm7uq4ptk0GD1grdysRgW+NWsPIXSLSsmhvQTkJzMUW7jpJZN35gzerV9kUxMMSaJunU/fzGnfR1iJ/VGBw00HS0kiJoX3cZEoN15VBwYbvIIcaKepeyR9SCiVXQLGPOdxbqOEaAdbwQiMbb1l5ZELRyaeeGTirbVVlVxMnk8Zi3j91rs5Jfjka9dLQIcI3sc5vFpt57vQOX1hffOLq7U1t5FxNq7S/qIom1/tqXr03cvMLtCYjayE8CFxiplfNk2EdfkLHhvFaSLJwei6MGAWG3meGQs8TUUb3P1UUAEOWALAoFEPV0YRIAP7Cs86hapIKjy1W6NE4NZoj2xaLKs2oobUHzMKEJ02GHCXwLT7z9EJ6dTdtnJ9tht4OnUIm+tS5GLPAIX7zjxhnKnbKP3A4v7JZ6QIhUjDCAJfnWuDjtTqwXhg31f6XtRoyarn5LyiXDZFy824TweySPrSCk9NnwoteJQ/J1gtKcQgSlgqA1bcEIV3vLzd1gpeHd4zZJF0esIamdzMumIEBYCwmuSvpgRFt+siLJ6kMdKi3f90oRu4mILVWGG7mVAdWboraKa+qIgKz9Xfuo6ajmMEAB+lzkufgCXRkg+sZsWDhDkuQrRJWYw1G07puNpN0G/+gM0nWwCQ==");
        }
        if (whetherLegal){
            map.put("message", ConstantsContext.getPirateContact());
            map.put("code", 141);
        }else {
            map.put("message", "success");
            map.put("code", 200);
        }

        JSONObject json = new JSONObject(map);
        return json.toString();
    }

    @RequestMapping("/now")
    @ResponseBody
    public String now(){
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("now", new Date().getTime());
        map.put("data",map1);
        map.put("message", "ok");
        map.put("code", 0);
        JSONObject json = new JSONObject(map);
        return json.toString();
    }

    @RequestMapping("/recell")
    @ResponseBody
    public String recell(){
        Map<String, Object> map = new HashMap<>();
        map.put("data","");
        map.put("message", "some error in database");
        map.put("code", 141);
        JSONObject json = new JSONObject(map);
        return json.toString();
    }

    @RequestMapping("/appactive")
    @ResponseBody
    public String appactive(@RequestHeader(value = "User-Token", required = false) String token){
        //应用名称
        String virtualId = HttpContext.getRequest().getParameter("virtual_id");
        String application = HttpContext.getRequest().getParameter("an");
        String sign;
        String applicationName = null;
        if (StringUtils.isEmpty(virtualId)||StringUtils.isEmpty(token)){
            return null;
        }else {
            String deSign = CustomEnAndDe.deCrypto(token);
            if (StringUtils.isNotEmpty(application)){
                applicationName = CustomEnAndDe.deCrypto(application);
            }
//            String time =  deSign.substring(deSign.length() -8);
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
        boolean whetherLegal = appPowerService.whetherLegalBySignAndAppCodeNoInsert(sign,applicationName,virtualId);
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("content", ConstantsContext.getPirateContact());
        map1.put("title", "重要通知");
        map1.put("type", "");
        if (whetherLegal){
            map1.put("show", 1);
        }else {
            map1.put("show", 0);
        }

        map.put("data",map1);
        map.put("message", "ok");
        map.put("code", 0);
        JSONObject json = new JSONObject(map);
        return json.toString();
    }

    @RequestMapping("/stepinfo")
    @ResponseBody
    public String stepinfo(){
        Map<String, Object> map = new HashMap<>();
        map.put("data","");
        map.put("message", "no data in database");
        map.put("code", 199);
        JSONObject json = new JSONObject(map);
        return json.toString();
    }

    @RequestMapping("/vipprice")
    @ResponseBody
    public String vipprice(){
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> map1 = new HashMap<>();
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

    //1.2.9版本新增
    @RequestMapping("/appdatainfo")
    @ResponseBody
    public String appdatainfo(@RequestHeader(value = "User-Token", required = false) String token,@RequestHeader(value = "X-UT-DID", required = false) String utDid){
        String packAge = HttpContext.getRequest().getParameter("package");
        String appuserid = HttpContext.getRequest().getParameter("appuserid");
        String name = HttpContext.getRequest().getParameter("name");
        String model = HttpContext.getRequest().getParameter("ut_did");
        if (StringUtils.isEmpty(model)){
            model = utDid;
        }
        //应用名称
        String virtualId = HttpContext.getRequest().getParameter("virtual_id");
        String application = HttpContext.getRequest().getParameter("an");
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
            //说明盗版
//            if (!time.equals(newTime)){
//                sign = deSign;
//            }else {
            //从数据库里查是否正版
            //去除最后七位
            sign = deSign.substring(0,deSign.length()-8);
//            }
        }

        boolean isShow = appPowerService.whetherShowBySignAndAppCode(sign,applicationName,virtualId);
        boolean pirateOpen2 = ConstantsContext.getPirateOpen2();
        boolean whetherLegal = appPowerService.whetherLegalBySignAndAppCode(sign,applicationName,virtualId,"huanyin129");

        if (StringUtils.isNotEmpty(packAge)){
            QueryWrapper<HyApp> wrapper = new QueryWrapper<>();
            wrapper.eq("ut_did", model);
            wrapper.eq("appuserid", appuserid);
            wrapper.eq("package", packAge);
            wrapper.eq("app_code", virtualId);
            wrapper.eq("sign", sign);
            if(StringUtils.isNotEmpty(applicationName)){
                wrapper.eq("application_name", applicationName);
            }
            HyApp app = hyAppService.getOne(wrapper);
            if (ObjectUtil.isEmpty(app)){
                HyApp hyApp = new HyApp();
                hyApp.setFakedata(0);
                hyApp.setAppuserid(Integer.valueOf(appuserid));
                try {
                    hyApp.setName(URLDecoder.decode(name, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                hyApp.setAppCode(virtualId);
                hyApp.setAppName(CustomEnAndDe.deCrypto(virtualId));
                hyApp.setPackAge(packAge);
                hyApp.setUtDid(model);
                hyApp.setSign(sign);
                if(StringUtils.isNotEmpty(applicationName)){
                    hyApp.setApplicationName(applicationName);
                }
                hyApp.setCreateTime(new Date());
                //如果开关没开
                if (!pirateOpen2){
                    hyAppService.save(hyApp);
                    redisUtil.del(RedisType.HUANYIN.getCode() + model +"-"+ sign +"-"+ virtualId);
                }
                //如果开关打开且没有制裁
                if (pirateOpen2&&!whetherLegal){
                    hyAppService.save(hyApp);
                    redisUtil.del(RedisType.HUANYIN.getCode() + model +"-"+ sign +"-"+ virtualId);
                }

            }
            Map<String, Object> map = new HashMap<>();
            Map<String, Object> map1 = new HashMap<>();
            map1.put("username", "15156061423");
            try {
                if (whetherLegal){
                    map1.put("name", ConstantsContext.getPirateOpenText());
                }else {
                    map1.put("name", URLDecoder.decode(name, "UTF-8"));
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            map1.put("package",packAge);
            map1.put("packName",name);
            map1.put("fakedata", 0);
            map1.put("appuserid", appuserid);
            map.put("data",map1);
            map.put("message", "ok");
            map.put("code", 0);
            JSONObject json = new JSONObject(map);
            String aa = json.toString();
            aa = aa.replaceAll("packAge", "package");
            return aa;
        }
        List<HyAppResult> hyAppResults = hyAppService.findListByModelAndSignAndAppName(model,sign,virtualId);
        if (ConstantsContext.getPirateOpen()&&isShow){
            if (CollectionUtil.isNotEmpty(hyAppResults)){
                hyAppResults.forEach(hyAppResult -> {
                    hyAppResult.setName(ConstantsContext.getPirateOpenText());
                });
            }
        }
        Map<String, Object> map = new HashMap<>();
        if (whetherLegal){
            map.put("data",null);
            map.put("message", "ok");
            map.put("code", 141);
        }else{
            map.put("data",hyAppResults);
            map.put("message", "ok");
            map.put("code", 0);
        }

        JSONObject json = new JSONObject(map);
        String aa = json.toString();
        aa = aa.replaceAll("packAge", "package");
        return aa;
    }
}
