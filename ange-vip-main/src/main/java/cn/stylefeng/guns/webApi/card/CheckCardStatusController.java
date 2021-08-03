package cn.stylefeng.guns.webApi.card;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
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
import cn.stylefeng.guns.sys.core.exception.apiResult.ApiManageApi;
import cn.stylefeng.guns.sys.core.exception.apiResult.AppInfoApi;
import cn.stylefeng.guns.sys.core.exception.apiResult.CardInfoApi;
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
import java.util.Optional;

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

        CardInfoApi cardInfoApi = cardInfoService.getCardInfoApiByAppIdAndCardCode(apiManage.getAppId(),param.getSingleCode(),param.getHoldCheck(),apiManage,false);
        // 获取卡密的所有token信息
        List<Token> tokens = tokenService.getTokenListByCardAndRedis(param.getSingleCode());
        if (CollectionUtil.isNotEmpty(tokens)){
            //判断token是否存在
            Optional<Token> tokenOptional = tokens.stream().filter(token -> token.getToken().equals(param.getStatusCode())).findAny();
           if (!tokenOptional.isPresent()){
               //卡密在别的设备上登录
               throw new CommonException(ApiExceptionEnum.CARD_LOGIN_OTHER.getCode(),param.getHoldCheck(),apiManage,false);
           }
            //校验是否已过清理时间
           if (DateUtil.offsetMinute(tokenOptional.get().getCreateTime(), CardDateUtil.getClearSpace(appInfoApi.getCodeClearSpace())).compareTo(DateUtil.date())<0) {
               //卡密登录过期，请重新登录
               throw new CommonException(ApiExceptionEnum.TOKEN_EXPIRE.getCode(),param.getHoldCheck(),apiManage,false);
           }
           if (cardInfoApi.getCardStatus()==1){
               if (cardInfoApi.getExpireTime().compareTo(DateUtil.date())<0) {
                   //更新卡密和删除缓存
                   CardInfo cardInfo1 = new CardInfo();
                   cardInfo1.setCardId(cardInfoApi.getCardId());
                   cardInfo1.setCardStatus(CardStatus.EXPIRED.getCode());
                   cardInfoService.updateCardAndRedis(apiManage.getAppId(),cardInfo1,param.getSingleCode());
                   //卡密已过期
                   throw new CommonException(ApiExceptionEnum.CARD_EXPIRE.getCode(),param.getHoldCheck(),apiManage,false);
               }else {
                   //更新校验时间
                   tokenOptional.get().setCheckTime(new Date());
                   tokenOptional.get().setUpdateTime(new Date());
                   //异步调用更新token
                   asyncService.updateTokenAndRedis(param.getSingleCode(),tokenOptional.get());
                   //卡密用户状态正常
                   throw new CommonException(ApiExceptionEnum.CARD_STATE_NORMAL.getCode(),param.getHoldCheck(),apiManage,true);
               }
           }
        }else {
            //卡密在别的设备上登录
            throw new CommonException(ApiExceptionEnum.CARD_LOGIN_OTHER.getCode(),param.getHoldCheck(),apiManage,false);
        }
    }

}
