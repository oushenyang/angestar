package cn.stylefeng.guns.webApi.card;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.core.constant.state.CardStatus;
import cn.stylefeng.guns.modular.app.service.AppInfoService;
import cn.stylefeng.guns.modular.card.entity.CardInfo;
import cn.stylefeng.guns.modular.demos.service.AsyncService;
import cn.stylefeng.guns.modular.device.service.TokenService;
import cn.stylefeng.guns.sys.core.constant.state.RedisType;
import cn.stylefeng.guns.sys.core.exception.*;
import cn.stylefeng.guns.modular.apiManage.service.ApiManageService;
import cn.stylefeng.guns.modular.card.model.result.CardInfoApi;
import cn.stylefeng.guns.modular.card.service.CardInfoService;
import cn.stylefeng.guns.modular.device.entity.Token;
import cn.stylefeng.guns.sys.core.auth.util.RedisUtil;
import cn.stylefeng.guns.sys.core.exception.enums.ApiExceptionEnum;
import cn.stylefeng.guns.sys.core.util.CardDateUtil;
import cn.stylefeng.guns.webApi.common.param.CheckCardStatusParam;
import cn.stylefeng.guns.webApi.common.util.RequestUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private final TokenService tokenService;

    public CheckCardStatusController(ApiManageService apiManageService, CardInfoService cardInfoService, RedisUtil redisUtil, AsyncService asyncService, AppInfoService appInfoService, TokenService tokenService) {
        this.apiManageService = apiManageService;
        this.cardInfoService = cardInfoService;
        this.redisUtil = redisUtil;
        this.asyncService = asyncService;
        this.appInfoService = appInfoService;
        this.tokenService = tokenService;
    }

    @RequestMapping("/{callCode}")
    @ResponseBody
    public void checkCardStatus(@PathVariable String callCode, @RequestBody(required=false) String body) {
        //获取接口信息
        ApiManageApi apiManage = apiManageService.getApiManageByRedis("checkCardStatus",callCode);
        AppInfoApi appInfoApi =  appInfoService.getAppInfoByRedis(callCode);
        //获取参数
        CheckCardStatusParam param = RequestUtil.getCheckCardStatusParameter(apiManage,body);

        CardInfoApi cardInfoApi = cardInfoService.getCardInfoApiByAppIdAndCardCode(apiManage.getAppId(),param.getSingleCode());
        //如果卡密查不到
        if (ObjectUtil.isNull(cardInfoApi)){
            //卡密不存在
            throw new CardLoginException(ApiExceptionEnum.CARD_NO.getCode(), apiManage.getAppId(),ApiExceptionEnum.CARD_NO.getMessage(),new Date(),param.getHoldCheck(),apiManage,false);
        }
        if (cardInfoApi.getCardStatus()==2){
            throw new CardLoginException(2006, apiManage.getAppId(),"卡密已过期",new Date(),param.getHoldCheck(),apiManage,false);
        }
        if (cardInfoApi.getCardStatus()==3){
            throw new CardLoginException(2005, apiManage.getAppId(),"卡密已被禁用",new Date(),param.getHoldCheck(),apiManage,false);
        }
        Object object = redisUtil.hget(RedisType.CARD_INFO.getCode() + param.getSingleCode(),RedisType.TOKEN.getCode());
        List<Token> tokens = new ArrayList<>();
        if (ObjectUtil.isNotNull(object)) {
            List<Token> tokenList = JSON.parseArray(object.toString(),Token.class);
            tokens.addAll(tokenList);
        }else {
            tokens = tokenService.list(new QueryWrapper<Token>().eq("card_or_user_code", param.getSingleCode()));
        }

        if (CollectionUtil.isNotEmpty(tokens)){
            boolean isHave = false;
            Token token = null;
            for (Token token1 : tokens){
                if (token1.getToken().equals(param.getStatusCode())) {
                    isHave = true;
                    token = token1;
                    break;
                }
            }
           if (!isHave){
               throw new CardLoginException(2007, apiManage.getAppId(),"卡密在别的设备上登录",new Date(),param.getHoldCheck(),apiManage,false);
               //校验是否已过清理时间
           }else {
               if (DateUtil.offsetMinute(token.getCreateTime(), CardDateUtil.getClearSpace(appInfoApi.getCodeClearSpace())).compareTo(DateUtil.date())<0) {
                   throw new CardLoginException(2010, apiManage.getAppId(),"卡密登录过期，请重新登录",new Date(),param.getHoldCheck(),apiManage,false);
               }
           }
           if (cardInfoApi.getCardStatus()==1){
               if (cardInfoApi.getExpireTime().compareTo(DateUtil.date())<0) {
                   //更新卡密和删除缓存
                   CardInfo cardInfo1 = new CardInfo();
                   cardInfo1.setCardId(cardInfoApi.getCardId());
                   cardInfo1.setCardStatus(CardStatus.EXPIRED.getCode());
                   cardInfoService.updateCardAndRedis(apiManage.getAppId(),cardInfo1,param.getSingleCode());
                   throw new CardLoginException(2006, apiManage.getAppId(),"卡密已过期",new Date(),param.getHoldCheck(),apiManage,false);
               }else {
                   //更新校验时间
                   token.setCheckTime(new Date());
                   token.setUpdateTime(new Date());
                   //异步调用更新token
                   asyncService.updateTokenAndRedis(param.getSingleCode(),token);
                   throw new CommonException(ApiExceptionEnum.CARD_STATE_NORMAL.getCode(),param.getHoldCheck(),apiManage,false);
               }
           }
        }else {
            throw new CardLoginException(2007, apiManage.getAppId(),"卡密在别的设备上登录！",new Date(),param.getHoldCheck(),apiManage,false);
        }
    }

}
