package cn.stylefeng.guns.webApi.card;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.core.constant.state.CardStatus;
import cn.stylefeng.guns.core.constant.state.CardTimeType;
import cn.stylefeng.guns.sys.core.exception.*;
import cn.stylefeng.guns.modular.apiManage.service.ApiManageService;
import cn.stylefeng.guns.modular.app.service.AppInfoService;
import cn.stylefeng.guns.modular.card.entity.CardInfo;
import cn.stylefeng.guns.modular.card.model.result.CardInfoApi;
import cn.stylefeng.guns.modular.card.service.CardInfoService;
import cn.stylefeng.guns.modular.card.service.CodeCardTypeService;
import cn.stylefeng.guns.modular.demos.service.AsyncService;
import cn.stylefeng.guns.modular.device.service.DeviceService;
import cn.stylefeng.guns.modular.device.service.TokenService;
import cn.stylefeng.guns.sys.core.exception.enums.ApiExceptionEnum;
import cn.stylefeng.guns.sys.core.util.CardDateUtil;
import cn.stylefeng.guns.sys.core.util.HttpClientUtil;
import cn.stylefeng.guns.sys.core.util.SnowflakeUtil;
import cn.stylefeng.guns.webApi.common.util.RequestUtil;
import cn.stylefeng.guns.webApi.common.param.CardLoginParam;
import cn.stylefeng.roses.core.util.ToolUtil;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

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
    private final CodeCardTypeService codeCardTypeService;
    private final AppInfoService appInfoService;
    private final DeviceService deviceService;
    private final TokenService tokenService;
    private final AsyncService asyncService;
    public CardLoginController(ApiManageService apiManageService, CardInfoService cardInfoService, CodeCardTypeService codeCardTypeService, AppInfoService appInfoService, DeviceService deviceService, TokenService tokenService, AsyncService asyncService) {
        this.apiManageService = apiManageService;
        this.cardInfoService = cardInfoService;
        this.codeCardTypeService = codeCardTypeService;
        this.appInfoService = appInfoService;
        this.deviceService = deviceService;
        this.tokenService = tokenService;
        this.asyncService = asyncService;
    }

    @RequestMapping("/{callCode}")
    @ResponseBody
    public String cardLogin(@PathVariable String callCode, @RequestBody(required=false) String body) {
        //获取接口信息
        ApiManageApi apiManage = apiManageService.getApiManageByRedis("cardLogin",callCode);
        AppInfoApi appInfoApi =  appInfoService.getAppInfoByRedis(callCode);
        //获取参数
        CardLoginParam param = RequestUtil.getCardLoginParameter(apiManage,body);
        if (appInfoApi.getCydiaFlag()==2){
            //应用已关闭
            throw new AppInfoException(ApiExceptionEnum.APP_CLOSED.getCode(),param.getHoldCheck(),apiManage,appInfoApi,false);
        }else if (appInfoApi.getCydiaFlag()==1){
            //该应用免费
            throw new CardLoginException(2000, apiManage.getAppId(),IdUtil.simpleUUID(),new Date(),param.getHoldCheck(),apiManage,true);
        }
        CardInfoApi cardInfoApi = cardInfoService.getCardInfoApiByAppIdAndCardCode(apiManage.getAppId(),param.getSingleCode());
        //如果卡密查不到
        if (ObjectUtil.isNull(cardInfoApi)){
            //从易游查
            if (appInfoApi.getOtherSign()==1&&param.getSingleCode().length()>=32){
                getYiYouByCard(apiManage,appInfoApi,param.getSingleCode(),param.getMac(),param.getModel(),param.getHoldCheck());
                //从万捷查
            }else {
                //卡密不存在
                throw new CardLoginException(ApiExceptionEnum.CARD_NO.getCode(), apiManage.getAppId(),"",new Date(),param.getHoldCheck(),apiManage,false);
            }
        }
        //如果未激活
        switch (cardInfoApi.getCardStatus()){
            //未激活
            case 0:
                CardInfo cardInfo = new CardInfo();
                cardInfo.setCardId(cardInfoApi.getCardId());
                cardInfo.setLoginNum(1);
                cardInfo.setCardStatus(CardStatus.ACTIVATED.getCode());
                //激活时间
                cardInfo.setActiveTime(DateUtil.date());
                //到期时间
                Date expireTime = getExpireTime(cardInfoApi);
                cardInfo.setExpireTime(expireTime);
                //异步更新卡密和删除缓存
                asyncService.updateCardAndRedis(apiManage.getAppId(),cardInfo,param.getSingleCode());
//                cardInfoService.updateCardAndRedis(apiManage.getAppId(),cardInfo,singleCode);
                createTokenAndDevice(cardInfoApi, apiManage, appInfoApi, param.getMac(), param.getModel(),param.getHoldCheck(), expireTime);
                break;
            //已激活
            case 1:
                //已经过期
                if (cardInfoApi.getExpireTime().compareTo(DateUtil.date())<0) {
                    //更新卡密和删除缓存
                    CardInfo cardInfo1 = new CardInfo();
                    cardInfo1.setCardId(cardInfoApi.getCardId());
                    cardInfo1.setCardStatus(CardStatus.EXPIRED.getCode());
                    //异步更新卡密和删除缓存
                    asyncService.updateCardAndRedis(apiManage.getAppId(),cardInfo1,param.getSingleCode());
                    //卡密已过期
                    throw new CardLoginException(ApiExceptionEnum.CARD_EXPIRE.getCode(), apiManage.getAppId(),ApiExceptionEnum.CARD_EXPIRE.getMessage(),new Date(),param.getHoldCheck(),apiManage,false);
                }
                createTokenAndDevice(cardInfoApi, apiManage, appInfoApi, param.getMac(), param.getModel(),param.getHoldCheck(), cardInfoApi.getExpireTime());
                break;
            //已过期
            case 2:
                //卡密已过期
                throw new CardLoginException(ApiExceptionEnum.CARD_EXPIRE.getCode(), apiManage.getAppId(),ApiExceptionEnum.CARD_EXPIRE.getMessage(),new Date(),param.getHoldCheck(),apiManage,false);
            //已禁用
            case 3:
                //卡密已被禁用
                throw new CardLoginException(ApiExceptionEnum.CARD_DISABLED.getCode(), apiManage.getAppId(),ApiExceptionEnum.CARD_DISABLED.getMessage(),new Date(),param.getHoldCheck(),apiManage,false);
        }
        return param.getSingleCode();
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
                boolean successful = deviceService.getDeviceApiAndHandleByCardOrUserId(apiManage.getAppId(),cardInfoApi.getCardId(),cardInfoApi.getCardCode(),cardInfoApi.getCardBindType()-1,cardInfoApi.getCardBindNum(),mac,model,expireTime);
                if (successful){
                    //返回成功信息
                    //生成token
                    tokenService.createToken(apiManage,cardInfoApi,appInfoApi,mac,model,holdCheck,expireTime);
                }else {
                    switch (cardInfoApi.getCardBindType()-1){
                        case 1:
                            throw new CardLoginException(2002, apiManage.getAppId(),"卡密未在绑定的设备上登录！",new Date(),holdCheck,apiManage,false);
                        case 2:
                            throw new CardLoginException(2003, apiManage.getAppId(),"卡密未在绑定的ip上登录！",new Date(),holdCheck,apiManage,false);
                        case 3:
                            throw new CardLoginException(2004, apiManage.getAppId(),"卡密未在绑定的设备或ip上登录！",new Date(),holdCheck,apiManage,false);
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
                boolean successful = deviceService.getDeviceApiAndHandleByCardOrUserId(apiManage.getAppId(),cardInfoApi.getCardId(),cardInfoApi.getCardCode(),appInfoApi.getCodeBindType(),appInfoApi.getCodeBindNum(),mac,model,expireTime);
                if (successful){
                    //返回成功信息
                    tokenService.createToken(apiManage,cardInfoApi,appInfoApi,mac,model,holdCheck,expireTime);
                }else {
                    switch (appInfoApi.getCodeBindType()){
                        case 1:
                            throw new CardLoginException(2002, apiManage.getAppId(),"卡密未在绑定的设备上登录",new Date(),holdCheck,apiManage,false);
                        case 2:
                            throw new CardLoginException(2003, apiManage.getAppId(),"卡密未在绑定的ip上登录",new Date(),holdCheck,apiManage,false);
                        case 3:
                            throw new CardLoginException(2004, apiManage.getAppId(),"卡密未在绑定的设备或ip上登录",new Date(),holdCheck,apiManage,false);
                    }
                }
            }
        }
    }

    /**
     * 从易游查询卡密信息
     */
    public void getYiYouByCard(ApiManageApi apiManage,AppInfoApi appInfoApi, String singleCode,String mac,String model,String holdCheck){
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("k", singleCode);
        String result= HttpClientUtil.postParams(appInfoApi.getProvingUrl(), paramMap,appInfoApi.getAppId(),holdCheck,apiManage);
        if(StringUtils.contains(result, "无此卡密")){
            //卡密不存在
            throw new CardLoginException(ApiExceptionEnum.CARD_NO.getCode(), apiManage.getAppId(),ApiExceptionEnum.CARD_NO.getCode(),new Date(),holdCheck,apiManage,false);
        }
        Document doc = Jsoup.parse(result);
        Elements rows = doc.select("form").get(0).select("span");
        //说明已激活
        if(StringUtils.contains(result, "到期时间")){
            //卡密类型
            String cardTypeName = rows.select("span").get(1).text();
            //激活时间
            String activeTime = rows.select("span").get(2).text();
            //到期时间
            String expireTime = rows.select("span").get(3).text();
            //先创建卡密
            CardInfo cardInfo = new CardInfo();
            cardInfo.setAppId(appInfoApi.getAppId());
            setCardTypeId(cardTypeName,cardInfo,appInfoApi.getCreateUser());
            cardInfo.setCardTypeName(cardTypeName);
            cardInfo.setUserId(appInfoApi.getCreateUser());
            cardInfo.setCreateUser(appInfoApi.getCreateUser());
            cardInfo.setUserName("易游");
            cardInfo.setBatchNo(SnowflakeUtil.getInstance().nextIdStr());
            cardInfo.setCreateTime(DateUtil.date());
            cardInfo.setCardCode(singleCode);
            cardInfo.setCardStatus(CardStatus.ACTIVATED.getCode());
            cardInfo.setCardBindType(0);
            cardInfo.setCardSignType(1);
            cardInfo.setCardOpenRange(0);
            cardInfo.setActiveTime(DateUtil.parse(activeTime));
            cardInfo.setExpireTime(DateUtil.parse(expireTime));
            cardInfo.setCardRemark("从易游导入");
            //已经过期
            if (DateUtil.parse(expireTime).compareTo(DateUtil.date())<0) {
                cardInfo.setCardStatus(CardStatus.EXPIRED.getCode());
                cardInfoService.save(cardInfo);
                throw new CardLoginException(2006, apiManage.getAppId(),"卡密已过期",new Date(),holdCheck,apiManage,false);
            }
            cardInfoService.save(cardInfo);
            //生成token
            CardInfoApi cardInfoApi = new CardInfoApi();
            ToolUtil.copyProperties(cardInfo, cardInfoApi);
            tokenService.createToken(apiManage,cardInfoApi,appInfoApi,mac,model,holdCheck,DateUtil.parse(expireTime));
        }
        //说明未激活
        if(StringUtils.contains(result, "未激活")){
            //卡密类型
            String cardTypeName = rows.select("span").get(1).text();
            Date date = DateUtil.date();
            //到期时间
            Date expireTime = CardDateUtil.getExpireTimeByCardTypeName(cardTypeName,date);
            //先创建卡密
            CardInfo cardInfo = new CardInfo();
            cardInfo.setAppId(appInfoApi.getAppId());
            setCardTypeId(cardTypeName,cardInfo,appInfoApi.getCreateUser());
            cardInfo.setCardTypeName(cardTypeName);
            cardInfo.setUserId(appInfoApi.getCreateUser());
            cardInfo.setUserName("易游");
            cardInfo.setCreateUser(appInfoApi.getCreateUser());
            cardInfo.setCreateTime(DateUtil.date());
            cardInfo.setCardCode(singleCode);
            cardInfo.setCardStatus(CardStatus.ACTIVATED.getCode());
            cardInfo.setCardBindType(0);
            cardInfo.setCardSignType(1);
            cardInfo.setCardOpenRange(0);
            cardInfo.setActiveTime(date);
            cardInfo.setExpireTime(expireTime);
            cardInfo.setCardRemark("从易游导入");
            cardInfoService.save(cardInfo);
            //返回成功信息
            CardInfoApi cardInfoApi = new CardInfoApi();
            ToolUtil.copyProperties(cardInfo, cardInfoApi);
            //异步调用单码登录接口
            asyncService.activationYyCard(appInfoApi.getCardLoginUrl(),singleCode);
            tokenService.createToken(apiManage,cardInfoApi,appInfoApi,mac,model,holdCheck,expireTime);
        }
    }


    public void setCardTypeId(String cardTypeName, CardInfo cardInfo,Long createUser){
        if ("小时卡".equals(cardTypeName)){
            Long cardTypeId = codeCardTypeService.findByCardTimeTypeAndCardTypeData(createUser,CardTimeType.HOUR.getCode(),1);
            cardInfo.setCardTypeId(cardTypeId);
        }else if ("六时卡".equals(cardTypeName)){
            Long cardTypeId = codeCardTypeService.findByCardTimeTypeAndCardTypeData(createUser,CardTimeType.HOUR.getCode(),6);
            cardInfo.setCardTypeId(cardTypeId);
        }else if ("天卡".equals(cardTypeName)){
            Long cardTypeId = codeCardTypeService.findByCardTimeTypeAndCardTypeData(createUser,CardTimeType.DAY.getCode(),1);
            cardInfo.setCardTypeId(cardTypeId);
        }else if ("周卡".equals(cardTypeName)){
            Long cardTypeId = codeCardTypeService.findByCardTimeTypeAndCardTypeData(createUser,CardTimeType.WEEK.getCode(),1);
            cardInfo.setCardTypeId(cardTypeId);
        }else if ("半月卡".equals(cardTypeName)){
            Long cardTypeId = codeCardTypeService.findByCardTimeTypeAndCardTypeData(createUser,CardTimeType.DAY.getCode(),15);
            cardInfo.setCardTypeId(cardTypeId);
        }else if ("月卡".equals(cardTypeName)){
            Long cardTypeId = codeCardTypeService.findByCardTimeTypeAndCardTypeData(createUser,CardTimeType.MONTH.getCode(),1);
            cardInfo.setCardTypeId(cardTypeId);
        }else if ("季卡".equals(cardTypeName)){
            Long cardTypeId = codeCardTypeService.findByCardTimeTypeAndCardTypeData(createUser,CardTimeType.MONTH.getCode(),3);
            cardInfo.setCardTypeId(cardTypeId);
        }else if ("半年卡".equals(cardTypeName)){
            Long cardTypeId = codeCardTypeService.findByCardTimeTypeAndCardTypeData(createUser,CardTimeType.MONTH.getCode(),6);
            cardInfo.setCardTypeId(cardTypeId);
        }else if ("年卡".equals(cardTypeName)){
            Long cardTypeId = codeCardTypeService.findByCardTimeTypeAndCardTypeData(createUser,CardTimeType.YEAR.getCode(),1);
            cardInfo.setCardTypeId(cardTypeId);
        }else if ("永久卡".equals(cardTypeName)){
            Long cardTypeId = codeCardTypeService.findByCardTimeTypeAndCardTypeData(createUser,CardTimeType.YEAR.getCode(),99);
            cardInfo.setCardTypeId(cardTypeId);
        }
    }


}
