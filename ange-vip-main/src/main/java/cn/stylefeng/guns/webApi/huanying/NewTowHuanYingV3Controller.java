package cn.stylefeng.guns.webApi.huanying;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.base.consts.ConstantsContext;
import cn.stylefeng.guns.modular.appPower.service.AppPowerService;
import cn.stylefeng.guns.sys.core.auth.util.RedisUtil;
import cn.stylefeng.guns.sys.core.constant.state.RedisType;
import cn.stylefeng.guns.sys.core.util.*;
import cn.stylefeng.guns.sys.modular.system.service.DictService;
import cn.stylefeng.guns.webApi.huanying.entity.HyApp;
import cn.stylefeng.guns.webApi.huanying.model.result.GPSHyResult;
import cn.stylefeng.guns.webApi.huanying.model.result.HyAppResult;
import cn.stylefeng.guns.webApi.huanying.model.result.HyUserResult;
import cn.stylefeng.guns.webApi.huanying.service.HyAppService;
import cn.stylefeng.roses.core.util.HttpContext;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
@RequestMapping("web/api/v3")
public class NewTowHuanYingV3Controller {

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
        if (StringUtils.isEmpty(application)){
            return null;
        }
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
        boolean whetherLegal = false;
        if (appversioncode.equals("210")){
            whetherLegal = appPowerService.whetherLegalBySignAndAppCodeNoInsert(sign,applicationName,virtualId,"huanyin210");
        }else {
            whetherLegal = appPowerService.whetherLegalBySignAndAppCodeNoInsert(sign,applicationName,virtualId,"huanyin125");
        }
        Map<String, Object> map = new HashMap<>();
        if (appversioncode.equals("210")){
            HyUserResult hyUserResult = new HyUserResult();
            hyUserResult.setStatus(0);
            hyUserResult.setAppurl("www.lanzou1s.com/clone");
            hyUserResult.setLeftmoney("0");
            hyUserResult.setInchina(1);
            hyUserResult.setVersioncode("10");
            hyUserResult.setTag(0);
            hyUserResult.setOldinviteid("");
            hyUserResult.setStonetime(1546272000000L);
            hyUserResult.setValidnum(0);
            hyUserResult.setGoogleversioncode("6");
            hyUserResult.setProxyratio("0.1");
            hyUserResult.setTotalnum(0);
            hyUserResult.setTotalmoney("0");
            hyUserResult.setPoints(0);
            hyUserResult.setClosed(0);
            hyUserResult.setViptype(3);
            hyUserResult.setUsername("15156041433");
            hyUserResult.setRealname("");
//            hyUserResult.setMyappkey("31f8df9f0478eaa8");
            hyUserResult.setInviteid("5D8CLY");
            hyUserResult.setZfb("");
            hyUserResult.setImsis("");
            hyUserResult.setVirtual_id("16bee683e6b8322b1e982d28f75606e3");
            hyUserResult.setImeis("Xiaomi_M2011K2C|YAfDSMpjAHUDAK4yVkTxVJ4O,Xiaomi_Mi10Pro|X//n8lot5fkDANeuGVdI/Ja6,Xiaomi_RedmiK20ProPremiumEdition|XhAxKdCn3TwDAFwETYuwio1m");
            hyUserResult.setInvitenum(0);
            hyUserResult.setProxyid("5D8CLY");
            hyUserResult.setFatherid("0");
            hyUserResult.setUserid("Xiaomi_RedmiK20ProPremiumEdition|WvMZuYLQ0W4DAArXTsQddFXj");
//            hyUserResult.setToken("de1686230d41a96d9c1ce179b8eb8b8f");
            hyUserResult.setVipphone("None");
            hyUserResult.setChannel("china");
            hyUserResult.setStarttime(1558276363000L);
            hyUserResult.setDeadline(1930452055000L);
            String a = JSON.toJSONString(hyUserResult);
            String aaa = DESCBCUtil.encode(a);
            assert aaa != null;
            aaa = aaa.replaceAll("\r|\n", "");
            map.put("data", aaa);
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
        boolean whetherLegal = false;
        if (appversioncode.equals("210")){
            whetherLegal = appPowerService.whetherLegalBySignAndAppCodeNoInsert(sign,applicationName,virtualId,"huanyin210");
        }else {
            return null;
        }

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
    @RequestMapping("/regps")
    @ResponseBody
    public String regps(){
        double lat = Double.parseDouble(HttpContext.getRequest().getParameter("lat"));
        double lon = Double.parseDouble(HttpContext.getRequest().getParameter("lon"));
        String appversioncode = HttpContext.getRequest().getParameter("appversioncode");
        GPS aps = new GPS();
        if (appversioncode.equals("210")){
            aps.setLatitude(GPSNewUtils.f(lat,lon)[0]);
            aps.setLongitude(GPSNewUtils.f(lat,lon)[1]);
        }else{
            aps = GPSAllUtils.g(lat,lon);
        }

        GPSHyResult gpsHyResult = new GPSHyResult(aps.getLatitude(),aps.getLongitude());
        List<GPSHyResult> gpsHyResultList = new ArrayList<>();
        gpsHyResultList.add(gpsHyResult);
        String a = JSON.toJSONString(gpsHyResultList);
        String aaa = DESCBCUtil.encode(a);
        assert aaa != null;
        aaa = aaa.replaceAll("\r|\n", "");
        Map<String, Object> map = new HashMap<>();
        map.put("data",aaa);
        map.put("message", "success");
        map.put("code", 200);
        JSONObject json = new JSONObject(map);
        return json.toString();
    }
    //1.2.9版本新增
    @DeleteMapping("/appdatainfo")
    @ResponseBody
    public String deleteAppdatainfo(@RequestHeader(value = "User-Token", required = false) String token,@RequestHeader(value = "X-UT-DID", required = false) String utDid){
        String packAge = HttpContext.getRequest().getParameter("package");
//        Integer is64 = Integer.valueOf(HttpContext.getRequest().getParameter("is_64"));
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
            sign = deSign.substring(0,deSign.length()-8);
        }
        QueryWrapper<HyApp> wrapper = new QueryWrapper<>();
        wrapper.eq("ut_did", model);
        wrapper.eq("appuserid", appuserid);
        wrapper.eq("package", packAge);
        wrapper.eq("app_code", virtualId);
        wrapper.eq("sign", sign);
        if(StringUtils.isNotEmpty(applicationName)){
            wrapper.eq("application_name", applicationName);
        }
        boolean su = hyAppService.remove(wrapper);
        redisUtil.del(RedisType.HUANYIN.getCode() + model +"-"+ sign +"-"+ virtualId);
//        if (su){
            String a = "{\"message\": \"ok\", \"code\": 0, \"data\": [\"delete %s success\", \""+packAge+"\"]}";
           return a;
//        }
    }

    //1.2.9版本新增
    @RequestMapping("/appdatainfo")
    @ResponseBody
    public String appdatainfo(@RequestHeader(value = "User-Token", required = false) String token,@RequestHeader(value = "X-UT-DID", required = false) String utDid){
        String packAge = HttpContext.getRequest().getParameter("package");
//        Integer is64 = Integer.valueOf(HttpContext.getRequest().getParameter("is_64"));
        String appuserid = HttpContext.getRequest().getParameter("appuserid");
        String name = HttpContext.getRequest().getParameter("name");
        String model = HttpContext.getRequest().getParameter("ut_did");
        int appversioncode = Integer.parseInt(HttpContext.getRequest().getParameter("appversioncode"));
        if (StringUtils.isEmpty(model)){
            model = utDid;
        }
        //应用名称
        String virtualId = HttpContext.getRequest().getParameter("virtual_id");
        String application = HttpContext.getRequest().getParameter("an");
        String sign;
        String applicationName = null;
        if (StringUtils.isEmpty(virtualId)||StringUtils.isEmpty(token)||StringUtils.isEmpty(application)){
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
        boolean whetherLegal = false;
        if (appversioncode == 210){
            whetherLegal = appPowerService.whetherLegalBySignAndAppCode(sign,applicationName,virtualId,"huanyin210");
        }

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
            if (packAge.equals("com.tencent.mm")){
//                if (is64==0){
                    map1.put("appinfoid", "https://dldir1.qq.com/weixin/android/weixin806android1900.apk");
//                }
            }else if (packAge.equals("com.ss.android.ugc.aweme")){
//                if (is64==0){
                    map1.put("appinfoid", "https://www-public-static.oss-cn-beijing.aliyuncs.com/douyin.apk");
//                }
            }else {
                map1.put("packName",name);
            }
            map1.put("package",packAge);
            map1.put("fs",0);
            map1.put("fn",0);
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
                    hyAppResult.setFn(0);
                    hyAppResult.setFs(0);
                    if (packAge.equals("com.tencent.mm")){
//                if (is64==0){
                        hyAppResult.setAppinfoid("https://dldir1.qq.com/weixin/android/weixin806android1900.apk");
//                }
                    }else if (packAge.equals("com.ss.android.ugc.aweme")){
//                if (is64==0){
                        hyAppResult.setAppinfoid("https://www-public-static.oss-cn-beijing.aliyuncs.com/douyin.apk");
//                }
                    }else if (packAge.equals("com.immomo.momo")){
//                if (is64==0){
                        hyAppResult.setAppinfoid("https://imtt.dd.qq.com/16891/apk/55853E13943F2B3E489A34696097AEEB.apk?fsname=com.immomo.momo_8.32.3_7316.apk&csr=1bbd");
//                }
                    }else {
                        hyAppResult.setAppinfoid("http://data.angestar.com");
                    }

                });
            }
        }
        Map<String, Object> map = new HashMap<>();
        if (whetherLegal){
            map.put("data",null);
            map.put("message", "ok");
            map.put("code", 141);
        }else{
//            String actual = JSON.toJSONString(hyAppResults);
//            String aaa = DESCBCUtil.encode(actual);
//            assert aaa != null;
//            aaa = aaa.replaceAll("\r|\n", "");
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
