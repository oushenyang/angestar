package cn.stylefeng.guns.webApi.huanying;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.base.consts.ConstantsContext;
import cn.stylefeng.guns.modular.appPower.service.AppPowerService;
import cn.stylefeng.guns.sys.core.auth.util.RedisUtil;
import cn.stylefeng.guns.sys.core.constant.state.RedisType;
import cn.stylefeng.guns.sys.modular.system.entity.Dict;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
@RequestMapping("/api1/v2")
public class HuanYingV2Controller {
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
    public String appdatainfo(@RequestBody(required=false) String  body){
        Map<String, String[]> cookies = HttpContext.getRequest().getParameterMap();
        String packAge = HttpContext.getRequest().getParameter("package");
        String appuserid = HttpContext.getRequest().getParameter("appuserid");
        String name = HttpContext.getRequest().getParameter("name");
        String model = null;
        String sign = "0";
        for (Map.Entry<String, String[]> m : cookies.entrySet()) {
            if (m.getKey().equals("ut_did")){
                model = String.join("", m.getValue());
            }
            if (m.getKey().equals("sign")){
                sign = String.join("", m.getValue());
            }
        }
        if ("0".equals(sign)){
            return null;
        }
        List<HyAppResult> hyAppResults = hyAppService.findListBySpec(model,sign);
//        boolean isHave = false;
//        List<Dict> dictss = dictService.listDictsByCodeByRedis("HUANYINGAPP");
//        for (Dict dict : dictss){
//            if (dict.getCode().equals(sign)){
//                isHave = true;
//                break;
//            }
//
//        }
        boolean isHave = appPowerService.whetherShow(sign);
        boolean pirateOpen2 = ConstantsContext.getPirateOpen2();
        boolean whetherLegal = appPowerService.whetherLegal(sign);
        if (StringUtils.isNotEmpty(packAge)){
            QueryWrapper<HyApp> wrapper = new QueryWrapper<>();
            wrapper.eq("ut_did", model);
            wrapper.eq("appuserid", appuserid);
            wrapper.eq("package", packAge);
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
                hyApp.setPackAge(packAge);
                hyApp.setUtDid(model);
                hyApp.setSign(sign);
                hyApp.setCreateTime(new Date());
                //如果开关没开
                if (!pirateOpen2){
                    hyAppService.save(hyApp);
                    redisUtil.del(RedisType.HUANYIN + model + sign);
                }

                //如果开关打开且没有制裁
                if (pirateOpen2&&!whetherLegal){
                    hyAppService.save(hyApp);
                    redisUtil.del(RedisType.HUANYIN + model + sign);
                }

            }
            Map map = new HashMap<String, String>();
            Map map1 = new HashMap<String, String>();
            map1.put("username", "15156061423");
            try {
                if (pirateOpen2&&whetherLegal){
                    map1.put("name", "盗版应用,请立即卸载!正版微信:angestar88888");
                }else {
                    map1.put("name", URLDecoder.decode(name, "UTF-8"));
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            map1.put("package",packAge);
            map1.put("packName","极影");
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

        if (ConstantsContext.getPirateOpen()&&!isHave){
            if (CollectionUtil.isNotEmpty(hyAppResults)){
                hyAppResults.forEach(hyAppResult -> {
                    hyAppResult.setName("盗版应用,正版微信:angestar88888");
                });
            }
        }
        Map map = new HashMap<String, String>();
        if (pirateOpen2&&whetherLegal){
            map.put("data",null);
            map.put("message", "盗版应用,请立即卸载!正版微信:angestar88888");
            map.put("code", 500);
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
        Map map = new HashMap<String, String>();
        Map map1 = new HashMap<String, String>();
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
        Map map = new HashMap<String, String>();
        Map map1 = new HashMap<String, String>();
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
        Map map = new HashMap<String, String>();
        Map map1 = new HashMap<String, String>();
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
