package cn.stylefeng.guns.webApi.card;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.core.constant.state.CardStatus;
import cn.stylefeng.guns.modular.apiManage.service.ApiManageService;
import cn.stylefeng.guns.modular.app.service.AppInfoService;
import cn.stylefeng.guns.modular.card.entity.CardInfo;
import cn.stylefeng.guns.modular.card.service.CardInfoService;
import cn.stylefeng.guns.modular.demos.service.AsyncService;
import cn.stylefeng.guns.modular.device.entity.Token;
import cn.stylefeng.guns.modular.device.service.TokenService;
import cn.stylefeng.guns.sys.core.auth.util.RedisUtil;
import cn.stylefeng.guns.sys.core.constant.state.RedisType;
import cn.stylefeng.guns.sys.core.exception.CardLoginException;
import cn.stylefeng.guns.sys.core.exception.CommonException;
import cn.stylefeng.guns.sys.core.exception.apiResult.ApiManageApi;
import cn.stylefeng.guns.sys.core.exception.apiResult.AppInfoApi;
import cn.stylefeng.guns.sys.core.exception.apiResult.CardInfoApi;
import cn.stylefeng.guns.sys.core.exception.enums.ApiExceptionEnum;
import cn.stylefeng.guns.sys.core.util.CardDateUtil;
import cn.stylefeng.guns.webApi.common.param.CheckCardStatusParam;
import cn.stylefeng.guns.webApi.common.param.SetCardDataParam;
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
 * <p>设置单码数据</p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2020/5/28
 * @since JDK 1.8
 */
@Controller
@RequestMapping("/setCardData")
public class SetCardDataController {
    private final ApiManageService apiManageService;
    private final CardInfoService cardInfoService;
    private final RedisUtil redisUtil;
    private final AppInfoService appInfoService;
    private final TokenService tokenService;

    public SetCardDataController(ApiManageService apiManageService, CardInfoService cardInfoService, RedisUtil redisUtil, AppInfoService appInfoService, TokenService tokenService) {
        this.apiManageService = apiManageService;
        this.cardInfoService = cardInfoService;
        this.redisUtil = redisUtil;
        this.appInfoService = appInfoService;
        this.tokenService = tokenService;
    }

    @RequestMapping("/{callCode}")
    @ResponseBody
    public void setCardData(@PathVariable String callCode, @RequestBody(required = false) String body) {
        //获取接口信息
        ApiManageApi apiManage = apiManageService.getApiManageByRedis("setCardData", callCode);
        AppInfoApi appInfoApi = appInfoService.getAppInfoByRedis(callCode);
        //获取参数
        SetCardDataParam param = RequestUtil.setCardDataParameter(apiManage, body);

        CardInfoApi cardInfoApi = cardInfoService.getCardInfoApiByAppIdAndCardCode(apiManage.getAppId(), param.getCard());
        //如果卡密查不到
        if (ObjectUtil.isNull(cardInfoApi)) {
            //卡密不存在
            throw new CardLoginException(ApiExceptionEnum.CARD_NO.getCode(), apiManage.getAppId(), ApiExceptionEnum.CARD_NO.getMessage(), new Date(), param.getTimestamp(), apiManage, false);
        }
        if (cardInfoApi.getCardStatus() == 2) {
            throw new CardLoginException(2006, apiManage.getAppId(), "卡密已过期", new Date(), param.getTimestamp(), apiManage, false);
        }
        if (cardInfoApi.getCardStatus() == 3) {
            throw new CardLoginException(2005, apiManage.getAppId(), "卡密已被禁用", new Date(), param.getTimestamp(), apiManage, false);
        }
        Object object = redisUtil.hget(RedisType.CARD_INFO.getCode() + param.getCard(), RedisType.TOKEN.getCode());
        List<Token> tokens = new ArrayList<>();
        if (ObjectUtil.isNotNull(object)) {
            List<Token> tokenList = JSON.parseArray(object.toString(), Token.class);
            tokens.addAll(tokenList);
        } else {
            tokens = tokenService.list(new QueryWrapper<Token>().eq("card_or_user_code", param.getCard()));
        }

        if (CollectionUtil.isNotEmpty(tokens)) {
            boolean isHave = false;
            Token token = null;
            for (Token token1 : tokens) {
                if (token1.getToken().equals(param.getToken())) {
                    isHave = true;
                    token = token1;
                    break;
                }
            }
            if (!isHave) {
                throw new CardLoginException(2007, apiManage.getAppId(), "卡密在别的设备上登录", new Date(), param.getTimestamp(), apiManage, false);
                //校验是否已过清理时间
            } else {
                if (DateUtil.offsetMinute(token.getCreateTime(), CardDateUtil.getClearSpace(appInfoApi.getCodeClearSpace())).compareTo(DateUtil.date()) < 0) {
                    throw new CardLoginException(2010, apiManage.getAppId(), "卡密登录过期，请重新登录", new Date(), param.getTimestamp(), apiManage, false);
                }
            }
            if (cardInfoApi.getCardStatus() == 1 && cardInfoApi.getExpireTime().compareTo(DateUtil.date()) < 0) {
                //更新卡密和删除缓存
                CardInfo cardInfo1 = new CardInfo();
                cardInfo1.setCardId(cardInfoApi.getCardId());
                cardInfo1.setCardStatus(CardStatus.EXPIRED.getCode());
                cardInfoService.updateCardAndRedis(apiManage.getAppId(), cardInfo1, param.getCard());
                throw new CardLoginException(2006, apiManage.getAppId(), "卡密已过期", new Date(), param.getTimestamp(), apiManage, false);
            }
        } else {
            throw new CardLoginException(2007, apiManage.getAppId(), "卡密在别的设备上登录！", new Date(), param.getTimestamp(), apiManage, false);
        }
        cardInfoApi.setCardData(param.getCardData());
        //更新卡密和删除缓存
        CardInfo cardInfo1 = new CardInfo();
        cardInfo1.setCardId(cardInfoApi.getCardId());
        cardInfo1.setCardData(param.getCardData());
        cardInfoService.updateCardAndRedis(apiManage.getAppId(), cardInfo1, param.getCard());
        //设置卡密数据成功
        throw new CommonException(ApiExceptionEnum.SET_CARD_DATA_SUCCESS.getCode(),param.getTimestamp(),apiManage,true);
    }

}
