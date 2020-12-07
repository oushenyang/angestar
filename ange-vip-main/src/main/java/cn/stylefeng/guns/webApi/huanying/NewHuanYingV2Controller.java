package cn.stylefeng.guns.webApi.huanying;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.base.consts.ConstantsContext;
import cn.stylefeng.guns.modular.appPower.service.AppPowerService;
import cn.stylefeng.guns.modular.device.entity.Token;
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
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("api1/v2")
public class NewHuanYingV2Controller {
    @Autowired
    private HyAppService hyAppService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private DictService dictService;

    @Autowired
    private AppPowerService appPowerService;

    @RequestMapping("/appdatainfo")
    @ResponseBody
    public String appdatainfo(@RequestHeader(value = "User-Token", required = false) String token){
        String packAge = HttpContext.getRequest().getParameter("package");
        String appuserid = HttpContext.getRequest().getParameter("appuserid");
        String name = HttpContext.getRequest().getParameter("name");
        String model = HttpContext.getRequest().getParameter("ut_did");
        //应用名称
        String virtualId = HttpContext.getRequest().getParameter("virtual_id");
        String sign;
        if (StringUtils.isEmpty(virtualId)||StringUtils.isEmpty(token)){
            return null;
        }else {
            String deSign = CustomEnAndDe.deCrypto(token);
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

        boolean isShow = appPowerService.whetherShowBySignAndAppCode(sign,virtualId);
        boolean pirateOpen2 = ConstantsContext.getPirateOpen2();
        boolean whetherLegal = appPowerService.whetherLegalBySignAndAppCode(sign,virtualId,"huanyin125");

        if (StringUtils.isNotEmpty(packAge)){
            QueryWrapper<HyApp> wrapper = new QueryWrapper<>();
            wrapper.eq("ut_did", model);
            wrapper.eq("appuserid", appuserid);
            wrapper.eq("package", packAge);
            wrapper.eq("app_code", virtualId);
            wrapper.eq("sign", sign);
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
                    map1.put("name", "盗版应用,请立即卸载!正版微信:angestar88888");
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
                    hyAppResult.setName("盗版应用,正版微信:angestar88888");
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

    @RequestMapping("/deviceinfo")
    @ResponseBody
    public String deviceinfo(){
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("isphone", 1);
        map1.put("sdkversionname", "10");
        map1.put("abis", "WvMZuYLQ0W4DAArXTsQddFXj");
        map1.put("phonenum", "");
        map1.put("versioncode", "1.2.0,120");
        map1.put("phonetype", 1);
        map1.put("deviceid", "");
        map1.put("imei", "00000000-023e-0e94-ffff-ffffef05ac4a");
        map1.put("model", "RedmiK20ProPremiumEdition");
        map1.put("manufacturer", "Xiaomi");
        map1.put("sn", "unknown");
        map.put("data",map1);
        map.put("message", "ok");
        map.put("code", 0);
        JSONObject json = new JSONObject(map);
        return json.toString();
    }

    @RequestMapping("/appmanager")
    @ResponseBody
    public String appmanager(){
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("website", "api.hyer.vip");
        map1.put("onlylife", 0);
        map1.put("storevisible", 0);
        map1.put("usediscount", 1);
        map1.put("discounttip", "（全场会员9折！仅限618一天！）");
        map1.put("newusediscount", 0);
        map1.put("newversioncode", 0);
        map1.put("discount", "0.9");
        map1.put("usehelp", "");
        map1.put("usemypay", 0);
        map.put("data",map1);
        map.put("message", "ok");
        map.put("code", 0);
        JSONObject json = new JSONObject(map);
        return json.toString();
    }

    @RequestMapping("/admanager")
    @ResponseBody
    public String admanager(){
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("pointwall", 1);
        map1.put("googleshowsplash", "3008091951");
        map1.put("splashad", "huan90s");
        map1.put("exchangepoint", 1200);
        map1.put("showchina", 2);
        map.put("data",map1);
        map.put("message", "ok");
        map.put("code", 0);
        JSONObject json = new JSONObject(map);
        return json.toString();
    }
}
