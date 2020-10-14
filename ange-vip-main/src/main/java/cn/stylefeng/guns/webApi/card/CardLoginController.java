package cn.stylefeng.guns.webApi.card;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
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
            throw new CardLoginException(-100, apiManage.getAppId(),"",new Date(),holdCheck,false);
        }else if (appInfoApi.getCydiaFlag()==1){
            //该应用免费
            throw new CardLoginException(200, apiManage.getAppId(),IdUtil.simpleUUID(),new Date(),holdCheck,true);
        }
        CardInfoApi cardInfoApi = cardInfoService.getCardInfoApiByAppIdAndCardCode(apiManage.getAppId(),singleCode);
        //如果卡密查不到，从易游查
        if (ObjectUtil.isNull(cardInfoApi)){
            throw new CardLoginException(-200, apiManage.getAppId(),"卡密不存在！",new Date(),holdCheck,false);
        }
        //如果未激活
        switch (cardInfoApi.getCardStatus()){
            //未激活
            case 0:
                CardInfo cardInfo = new CardInfo();
                cardInfo.setCardId(cardInfoApi.getCardId());
                cardInfo.setCardStatus(CardStatus.ACTIVATED.getCode());
                //激活时间
                cardInfo.setActiveTime(DateUtil.date());
                //到期时间
                Date expireTime = getExpireTime(cardInfoApi);
                cardInfo.setExpireTime(expireTime);
                //更新卡密和删除缓存
                cardInfoService.updateCardAndRedis(apiManage.getAppId(),cardInfo,singleCode);
                createTokenAndDevice(cardInfoApi, apiManage, appInfoApi, mac, model,holdCheck, expireTime);
                break;
            //已激活
            case 1:
                //已经过期
                if (cardInfoApi.getExpireTime().compareTo(DateUtil.date())<0) {
                    //更新卡密和删除缓存
                    CardInfo cardInfo1 = new CardInfo();
                    cardInfo1.setCardId(cardInfoApi.getCardId());
                    cardInfo1.setCardStatus(CardStatus.EXPIRED.getCode());
                    cardInfoService.updateCardAndRedis(apiManage.getAppId(),cardInfo1,singleCode);
                    throw new CardLoginException(-205, apiManage.getAppId(),"卡密已过期",new Date(),holdCheck,false);
                }
                createTokenAndDevice(cardInfoApi, apiManage, appInfoApi, mac, model,holdCheck, cardInfoApi.getExpireTime());
                break;
            //已过期
            case 2:
                throw new CardLoginException(-205, apiManage.getAppId(),"卡密已过期",new Date(),holdCheck,false);
            //已禁用
            case 3:
                throw new CardLoginException(-204, apiManage.getAppId(),"卡密已被禁用",new Date(),holdCheck,false);
        }
        return singleCode;
    }

    //获取到期时间
    public Date getExpireTime(CardInfoApi cardInfoApi){
        //当前时间
        Date date = DateUtil.date();
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
        if (!cardInfoApi.getWhetherAddTime()&&!cardInfoApi.getIsCustomTime()){
            //原来到期时间
            expireTime = CardDateUtil.getExpireTime(date,cardInfoApi.getCardTimeType(),cardInfoApi.getCardTypeData());
        }
        return expireTime;
    }

    //创建token和设备信息
    public void createTokenAndDevice(CardInfoApi cardInfoApi,ApiManageApi apiManage,AppInfoApi appInfoApi,String mac,String model,String holdCheck,Date expireTime){
        //如果自定义绑机
        if (cardInfoApi.getCardBindType()!=0){
            //不绑机，直接生成token，返回成功信息
            if (cardInfoApi.getCardBindType()==1){
                //生成token
                tokenService.createToken(apiManage,cardInfoApi,appInfoApi,mac,model,holdCheck,expireTime);
            }else {
                boolean successful = deviceService.getDeviceApiAndHandleByCardOrUserId(apiManage.getAppId(),cardInfoApi.getCardId(),cardInfoApi.getCardBindType()-1,cardInfoApi.getCardBindNum(),mac,model);
                if (successful){
                    //返回成功信息
                    //生成token
                    tokenService.createToken(apiManage,cardInfoApi,appInfoApi,mac,model,holdCheck,expireTime);
                }else {
                    switch (cardInfoApi.getCardBindType()-1){
                        case 1:
                            throw new CardLoginException(-201, apiManage.getAppId(),"卡密未在绑定的设备上登录！",new Date(),holdCheck,false);
                        case 2:
                            throw new CardLoginException(-202, apiManage.getAppId(),"卡密未在绑定的ip上登录！",new Date(),holdCheck,false);
                        case 3:
                            throw new CardLoginException(-203, apiManage.getAppId(),"卡密未在绑定的设备或ip上登录！",new Date(),holdCheck,false);
                    }
                }
            }
            //从应用信息里取
        }else {
            //不绑机，直接生成token，返回成功信息
            if (appInfoApi.getCodeBindType()==0){
                //生成token
                tokenService.createToken(apiManage,cardInfoApi,appInfoApi,mac,model,holdCheck,expireTime);
            }else {
                boolean successful = deviceService.getDeviceApiAndHandleByCardOrUserId(apiManage.getAppId(),cardInfoApi.getCardId(),cardInfoApi.getCardBindType(),cardInfoApi.getCardBindNum(),mac,model);
                if (successful){
                    //返回成功信息
                    tokenService.createToken(apiManage,cardInfoApi,appInfoApi,mac,model,holdCheck,expireTime);
                }else {
                    switch (cardInfoApi.getCardBindType()-1){
                        case 1:
                            throw new CardLoginException(-201, apiManage.getAppId(),"卡密未在绑定的设备上登录",new Date(),holdCheck,false);
                        case 2:
                            throw new CardLoginException(-202, apiManage.getAppId(),"卡密未在绑定的ip上登录",new Date(),holdCheck,false);
                        case 3:
                            throw new CardLoginException(-203, apiManage.getAppId(),"卡密未在绑定的设备或ip上登录",new Date(),holdCheck,false);
                    }
                }
            }
        }
    }
}
