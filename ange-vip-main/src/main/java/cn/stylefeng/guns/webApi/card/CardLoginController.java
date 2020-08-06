package cn.stylefeng.guns.webApi.card;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.core.constant.state.CardStatus;
import cn.stylefeng.guns.modular.apiManage.model.result.ApiManageApi;
import cn.stylefeng.guns.modular.apiManage.service.ApiManageService;
import cn.stylefeng.guns.modular.app.model.result.AppInfoApi;
import cn.stylefeng.guns.modular.app.service.AppInfoService;
import cn.stylefeng.guns.modular.card.entity.CardInfo;
import cn.stylefeng.guns.modular.card.model.result.CardInfoApi;
import cn.stylefeng.guns.modular.card.service.CardInfoService;
import cn.stylefeng.guns.modular.device.service.DeviceService;
import cn.stylefeng.guns.modular.device.service.TokenService;
import cn.stylefeng.guns.sys.core.auth.util.RedisUtil;
import cn.stylefeng.guns.sys.core.exception.CardLoginException;
import cn.stylefeng.guns.sys.core.exception.SystemApiException;
import cn.stylefeng.guns.sys.core.util.CardDateUtil;
import cn.stylefeng.roses.core.util.HttpContext;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>单码用户登录</p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2020/5/28
 * @since JDK 1.8
 */
@Controller
@RequestMapping("/cardLogin")
public class CardLoginController {
    private final ApiManageService apiManageService;
    private final CardInfoService cardInfoService;
    private final AppInfoService appInfoService;
    private final DeviceService deviceService;
    private final TokenService tokenService;

    private final RedisUtil redisUtil;

    public CardLoginController(ApiManageService apiManageService, CardInfoService cardInfoService, AppInfoService appInfoService, DeviceService deviceService, TokenService tokenService, RedisUtil redisUtil) {
        this.apiManageService = apiManageService;
        this.cardInfoService = cardInfoService;
        this.appInfoService = appInfoService;
        this.deviceService = deviceService;
        this.tokenService = tokenService;
        this.redisUtil = redisUtil;
    }

    @RequestMapping("/{callCode}")
    @ResponseBody
    public Object cardLogin(@PathVariable String callCode) {
        //获取接口信息
        ApiManageApi apiManage = apiManageService.getApiManageByRedis("cardLogin",callCode);
        AppInfoApi appInfoApi =  appInfoService.getAppInfoByRedis(apiManage.getAppId());
        String data = HttpContext.getRequest().getParameter("data");
        String singleCode = HttpContext.getRequest().getParameter(apiManage.getParameterOne());
        String edition = HttpContext.getRequest().getParameter(apiManage.getParameterTwo());
        String mac = HttpContext.getRequest().getParameter(apiManage.getParameterThree());
        String model = HttpContext.getRequest().getParameter(apiManage.getParameterFour());
        String holdCheck = HttpContext.getRequest().getParameter(apiManage.getParameterFive());
        String sign = HttpContext.getRequest().getParameter(apiManage.getParameterSix());
        if (StringUtils.isEmpty(singleCode)||StringUtils.isEmpty(edition)||StringUtils.isEmpty(mac)){
            throw new SystemApiException(-2, "必传参数存在空值","",false);
        }
        if (appInfoApi.getCydiaFlag()==2){
            //应用已关闭
            throw new CardLoginException(-100, apiManage.getAppId(),"",false);
        }else if (appInfoApi.getCydiaFlag()==1){
            Map map = new HashMap<String, String>();
//            map.put("expireTime", "2099-12-09");
//            map.put("token", IdUtil.simpleUUID());
            JSONObject json = new JSONObject(map);
            //该应用免费
            throw new CardLoginException(101, apiManage.getAppId(),json,true);
        }
        CardInfoApi cardInfoApi = cardInfoService.getCardInfoApiByAppIdAndCardCode(apiManage.getAppId(),singleCode);
        //如果卡密查不到，从易游查
        if (ObjectUtil.isNull(cardInfoApi)){
            throw new SystemApiException(500, "创建多租户-执行sql出现问题！","",false);
        }
        //如果未激活
        switch (cardInfoApi.getCardStatus()){
            //未激活
            case 0:
                //当前时间
                Date date = DateUtil.date();
                CardInfo cardInfo = new CardInfo();
                cardInfo.setCardId(cardInfoApi.getCardId());
                cardInfo.setCardStatus(CardStatus.ACTIVATED.getCode());
                //激活时间
                cardInfo.setActiveTime(DateUtil.date());
                //到期时间
                Date expireTime = null;
                //如果加时间
                if (cardInfoApi.getWhetherAddTime()){
                    //如果直接加时间值
                    if (cardInfoApi.getAddTime()!=null){
                        expireTime = cardInfoApi.getAddTime();
                    }else {
                        //原来到期时间
                        Date originalExpireTime = CardDateUtil.getExpireTime(date,cardInfoApi.getCardTimeType(),cardInfoApi.getCardTypeData());
                        expireTime = CardDateUtil.getAddExpireTime(originalExpireTime,cardInfoApi.getAddDayNum(),cardInfoApi.getAddHourNum(),cardInfoApi.getAddMinuteNum());
                    }
                }
                //如果自定义时间
                if (cardInfoApi.getIsCustomTime()){
                    expireTime = CardDateUtil.getAddExpireTime(date,cardInfoApi.getCustomTimeNum(),0,0);
                    cardInfoApi.setWhetherAddTime(false);
                    cardInfoApi.setAddDayNum(null);
                    cardInfoApi.setAddHourNum(null);
                    cardInfoApi.setAddMinuteNum(null);
                }
                //如果自定义绑机
                if (cardInfoApi.getCardBindType()!=0){
                    //不绑机，直接生成token，返回成功信息
                    if (cardInfoApi.getCardBindType()==1){
                        //生成token
                        boolean successful = tokenService.createToken(apiManage,cardInfoApi,appInfoApi,mac,model);
                    }else {
                        boolean successful = deviceService.getDeviceApiAndHandleByCardOrUserId(apiManage.getAppId(),cardInfoApi.getCardId(),cardInfoApi.getCardBindType()-1,cardInfoApi.getCardBindNum(),mac,model);
                        if (successful){
                            //返回成功信息
                        }else {
                            switch (cardInfoApi.getCardBindType()-1){
                                case 1:
                                    throw new SystemApiException(-201, "卡密未在绑定的设备上登录！","",false);
                                case 2:
                                    throw new SystemApiException(-202, "卡密未在绑定的ip上登录！","",false);
                                case 3:
                                    throw new SystemApiException(-203, "卡密未在绑定的设备或ip上登录！","",false);
                            }
                        }
                    }
                    //从应用信息里取
                }else {
                    //不绑机，直接生成token，返回成功信息
                    if (appInfoApi.getCodeBindType()==0){
                        //生成token
                        boolean successful = tokenService.createToken(apiManage,cardInfoApi,appInfoApi,mac,model);
                    }else {
                        boolean successful = deviceService.getDeviceApiAndHandleByCardOrUserId(apiManage.getAppId(),cardInfoApi.getCardId(),cardInfoApi.getCardBindType(),cardInfoApi.getCardBindNum(),mac,model);
                        if (successful){
                            //返回成功信息
                        }else {
                            switch (cardInfoApi.getCardBindType()-1){
                                case 1:
                                    throw new SystemApiException(-201, "卡密未在绑定的设备上登录","",false);
                                case 2:
                                    throw new SystemApiException(-202, "卡密未在绑定的ip上登录","",false);
                                case 3:
                                    throw new SystemApiException(-203, "卡密未在绑定的设备或ip上登录","",false);
                            }
                        }
                    }
                }
                break;
            //已激活
            case 1:
                break;
            //已过期
            case 2:
                throw new SystemApiException(-205, "卡密已过期","",false);
            //已禁用
            case 3:
                throw new SystemApiException(-204, "卡密已被禁用","",false);
        }
        return singleCode;
    }
}
