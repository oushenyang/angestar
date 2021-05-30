package cn.stylefeng.guns.webApi.trial;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.SecureUtil;
import cn.stylefeng.guns.modular.apiManage.model.result.ApiManageApi;
import cn.stylefeng.guns.modular.apiManage.service.ApiManageService;
import cn.stylefeng.guns.modular.app.service.AppInfoService;
import cn.stylefeng.guns.modular.trial.entity.Trial;
import cn.stylefeng.guns.modular.trial.service.TrialService;
import cn.stylefeng.guns.sys.core.exception.AppInfoApi;
import cn.stylefeng.guns.sys.core.exception.SystemApiException;
import cn.stylefeng.guns.sys.core.exception.TrialException;
import cn.stylefeng.guns.sys.core.util.CardDateUtil;
import cn.stylefeng.guns.sys.core.util.IpUtils;
import cn.stylefeng.guns.sys.core.util.ip2region.IpToRegionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static cn.stylefeng.roses.core.util.HttpContext.getRequest;

/**
 * <p>设备试用</p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2020/5/28
 * @since JDK 1.8
 */
@Controller
@RequestMapping("/api/trial")
public class GetTrialController {
    private final ApiManageService apiManageService;
    private final AppInfoService appInfoService;
    private final TrialService trialService;

    public GetTrialController(ApiManageService apiManageService, AppInfoService appInfoService, TrialService trialService) {
        this.apiManageService = apiManageService;
        this.appInfoService = appInfoService;
        this.trialService = trialService;
    }

    @RequestMapping("/{callCode}")
    @ResponseBody
    public Object getTrial(@PathVariable String callCode) {
        //获取接口信息
        ApiManageApi apiManage = apiManageService.getApiManageByRedis("trial",callCode);
        AppInfoApi appInfoApi =  appInfoService.getAppInfoByRedis(callCode);
        String mac = getRequest().getParameter(apiManage.getParameterOne());
        String model = getRequest().getParameter(apiManage.getParameterTwo());
        String holdCheck = getRequest().getParameter(apiManage.getParameterThree());
        String sign = getRequest().getParameter(apiManage.getParameterFour());
        if (StringUtils.isEmpty(mac)){
            throw new SystemApiException(2, "必传参数存在空值","",false);
        }
        if (appInfoApi.getSignFlag()&&StringUtils.isEmpty(sign)){
            throw new SystemApiException(2, "签名不正确","",false);
        }else if (appInfoApi.getSignFlag()&&StringUtils.isNotEmpty(sign)&&sign.length()!=32){
            throw new SystemApiException(2, "签名不正确","",false);
        }else if(StringUtils.isNotEmpty(sign)&&sign.length()==32){
            String md5 = SecureUtil.md5(mac+StringUtils.trimToEmpty(model)+StringUtils.trimToEmpty(holdCheck));
            if (!md5.equals(sign)){
                throw new SystemApiException(2, "签名不正确","",false);
            }
        }
        if (appInfoApi.getCodeTryType()==0){
            throw new TrialException(-301, apiManage.getAppId(),"试用功能已关闭","",holdCheck,false);
        }else if (appInfoApi.getCodeTryType()==1){
            Trial trial = trialService.getOne(new QueryWrapper<Trial>().eq("mac",mac).eq("app_id",appInfoApi.getAppId()));
            if (ObjectUtil.isNotNull(trial)){
                if (trial.getTrialTime().compareTo(DateUtil.date())<0) {
                    if (!trial.getExpire()){
                        trial.setExpire(true);
                        trialService.updateById(trial);
                    }
                    throw new TrialException(-302, apiManage.getAppId(),"试用已过期","",holdCheck,false);
                }else {
                    throw new TrialException(200, apiManage.getAppId(),"试用成功",DateUtil.between(new Date(), trial.getTrialTime(), DateUnit.MINUTE)+"分钟",holdCheck,true);
                }
            }else {
                HttpServletRequest request = getRequest();
                String ip = IpUtils.getIpAddr(request);
                Trial trial1 = new Trial();
                trial1.setAppId(appInfoApi.getAppId());
                trial1.setIp(ip);
//                trial1.setIpAddress(IpToRegionUtil.ipToRegion(ip));
                trial1.setMac(mac);
                trial1.setModel(model);
                trial1.setTrialType(appInfoApi.getCodeTryType());
                trial1.setTrialTime(CardDateUtil.getExpireTime(new Date(),0,appInfoApi.getCodeTryTime()));
                trial1.setCreateTime(new Date());
                trialService.save(trial1);
                throw new TrialException(200, apiManage.getAppId(),"试用成功", DateUtil.between(new Date(), trial1.getTrialTime(), DateUnit.MINUTE)+"分钟",holdCheck,true);
            }
        }else {
            Trial trial = trialService.getOne(new QueryWrapper<Trial>().eq("mac",mac).eq("app_id",appInfoApi.getAppId()));
            if (ObjectUtil.isNotNull(trial)){
                if (trial.getTrialNum()<=0) {
                    if (!trial.getExpire()){
                        trial.setExpire(true);
                        trialService.updateById(trial);
                    }
                    throw new TrialException(-302, apiManage.getAppId(),"试用已过期","",holdCheck,false);
                }else {
                    trial.setTrialNum(trial.getTrialNum()-1);
                    trialService.updateById(trial);
                    throw new TrialException(200, apiManage.getAppId(),"试用成功",trial.getTrialNum()+"次",holdCheck,true);
                }
            }else {
                HttpServletRequest request = getRequest();
                String ip = IpUtils.getIpAddr(request);
                Trial trial1 = new Trial();
                trial1.setAppId(appInfoApi.getAppId());
                trial1.setIp(ip);
//                trial1.setIpAddress(IpToRegionUtil.ipToRegion(ip));
                trial1.setMac(mac);
                trial1.setModel(model);
                trial1.setTrialType(appInfoApi.getCodeTryType());
                trial1.setTrialNum(appInfoApi.getCodeTryTime()-1);
                trial1.setCreateTime(new Date());
                trialService.save(trial1);
                throw new TrialException(200, apiManage.getAppId(),"试用成功",trial1.getTrialNum()+"次",holdCheck,true);
            }
        }

    }

}
