package cn.stylefeng.guns.webApi.card;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.core.constant.state.CardStatus;
import cn.stylefeng.guns.modular.app.model.result.AppInfoApi;
import cn.stylefeng.guns.modular.app.service.AppInfoService;
import cn.stylefeng.guns.modular.card.entity.CardInfo;
import cn.stylefeng.guns.modular.demos.service.AsyncService;
import cn.stylefeng.guns.sys.core.constant.state.RedisType;
import cn.stylefeng.guns.modular.apiManage.model.result.ApiManageApi;
import cn.stylefeng.guns.modular.apiManage.service.ApiManageService;
import cn.stylefeng.guns.modular.card.model.result.CardInfoApi;
import cn.stylefeng.guns.modular.card.service.CardInfoService;
import cn.stylefeng.guns.modular.device.entity.Token;
import cn.stylefeng.guns.sys.core.auth.util.RedisUtil;
import cn.stylefeng.guns.sys.core.exception.CardLoginException;
import cn.stylefeng.guns.sys.core.exception.SystemApiException;
import cn.stylefeng.roses.core.util.HttpContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * <p>检测单码用户状态</p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2020/5/28
 * @since JDK 1.8
 */
@Controller
@RequestMapping("/checkCardStatus")
public class CheckCardStatusController {
    private final ApiManageService apiManageService;
    private final CardInfoService cardInfoService;
    private final RedisUtil redisUtil;
    private final AsyncService asyncService;
    private final AppInfoService appInfoService;

    public CheckCardStatusController(ApiManageService apiManageService, CardInfoService cardInfoService, RedisUtil redisUtil, AsyncService asyncService, AppInfoService appInfoService) {
        this.apiManageService = apiManageService;
        this.cardInfoService = cardInfoService;
        this.redisUtil = redisUtil;
        this.asyncService = asyncService;
        this.appInfoService = appInfoService;
    }

    @RequestMapping("/{callCode}")
    @ResponseBody
    public void checkCardStatus(@PathVariable String callCode) {
        //获取接口信息
        ApiManageApi apiManage = apiManageService.getApiManageByRedis("checkCardStatus",callCode);
        AppInfoApi appInfoApi =  appInfoService.getAppInfoByRedis(callCode);
        String statusCode = HttpContext.getRequest().getParameter(apiManage.getParameterOne());
        String singleCode = HttpContext.getRequest().getParameter(apiManage.getParameterTwo());
        String holdCheck = HttpContext.getRequest().getParameter(apiManage.getParameterFive());
        String sign = HttpContext.getRequest().getParameter(apiManage.getParameterSix());
        if (StringUtils.isEmpty(singleCode)||StringUtils.isEmpty(statusCode)){
            throw new SystemApiException(-2, "必传参数存在空值","",false);
        }
        CardInfoApi cardInfoApi = cardInfoService.getCardInfoApiByAppIdAndCardCode(apiManage.getAppId(),singleCode);
        //如果卡密查不到
        if (ObjectUtil.isNull(cardInfoApi)){
            throw new CardLoginException(-200, apiManage.getAppId(),"卡密不存在！",new Date(),holdCheck,false);
        }
        //判断是否存在
        Token token = (Token)redisUtil.hget(RedisType.TOKEN.getCode() + cardInfoApi.getCardId(),statusCode);
        if (ObjectUtil.isNotNull(token)){
            switch (cardInfoApi.getCardStatus()){
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
                    }else {
                        //更新校验时间
                        token.setCheckTime(new Date());
                        //异步调用更新token
                        asyncService.updateTokenAndRedis(cardInfoApi.getCardId(),token,appInfoApi.getCodeClearSpace(),cardInfoApi.getExpireTime());
                        throw new CardLoginException(200, apiManage.getAppId(),"状态正常！",new Date(),holdCheck,true);
                    }
                //已过期
                case 2:
                    throw new CardLoginException(-205, apiManage.getAppId(),"卡密已过期",new Date(),holdCheck,false);
                    //已禁用
                case 3:
                    throw new CardLoginException(-204, apiManage.getAppId(),"卡密已被禁用",new Date(),holdCheck,false);
            }

        }else {
            throw new CardLoginException(-206, apiManage.getAppId(),"卡密在别的设备上登录！",new Date(),holdCheck,false);
        }
    }

}
