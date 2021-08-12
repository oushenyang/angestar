package cn.stylefeng.guns.webApi.card;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.core.constant.state.CardStatus;
import cn.stylefeng.guns.modular.apiManage.service.ApiManageService;
import cn.stylefeng.guns.modular.app.service.AppInfoService;
import cn.stylefeng.guns.modular.card.entity.CardInfo;
import cn.stylefeng.guns.modular.card.service.CardInfoService;
import cn.stylefeng.guns.modular.demos.service.AsyncService;
import cn.stylefeng.guns.modular.device.entity.Token;
import cn.stylefeng.guns.modular.device.service.DeviceService;
import cn.stylefeng.guns.modular.device.service.TokenService;
import cn.stylefeng.guns.sys.core.auth.util.RedisUtil;
import cn.stylefeng.guns.sys.core.exception.CommonException;
import cn.stylefeng.guns.sys.core.exception.GetCardDataException;
import cn.stylefeng.guns.sys.core.exception.apiResult.ApiManageApi;
import cn.stylefeng.guns.sys.core.exception.apiResult.AppInfoApi;
import cn.stylefeng.guns.sys.core.exception.apiResult.CardInfoApi;
import cn.stylefeng.guns.sys.core.exception.enums.ApiExceptionEnum;
import cn.stylefeng.guns.sys.core.util.CardDateUtil;
import cn.stylefeng.guns.webApi.common.param.CardDisableParam;
import cn.stylefeng.guns.webApi.common.util.RequestUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

/**
 * <p>卡密封禁</p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2020/5/28
 * @since JDK 1.8
 */
@Controller
@RequestMapping("/cardDisable")
public class CardDisableController {
    private final ApiManageService apiManageService;
    private final CardInfoService cardInfoService;
    private final AppInfoService appInfoService;
    private final TokenService tokenService;

    public CardDisableController(ApiManageService apiManageService, CardInfoService cardInfoService, AppInfoService appInfoService, TokenService tokenService) {
        this.apiManageService = apiManageService;
        this.cardInfoService = cardInfoService;
        this.appInfoService = appInfoService;
        this.tokenService = tokenService;
    }

    @RequestMapping("/{callCode}")
    @ResponseBody
    public Object cardDisable(@PathVariable String callCode, @RequestBody(required=false) String body) {
        //获取接口信息
        ApiManageApi apiManage = apiManageService.getApiManageByRedis("cardDisable",callCode);
        //获取应用信息
        AppInfoApi appInfoApi = appInfoService.getAppInfoByRedis(callCode);
        //获取参数
        CardDisableParam param = RequestUtil.cardDisableParameter(apiManage,body);
        //获取卡密信息
        CardInfoApi cardInfoApi = cardInfoService.getCardInfoApiByAppIdAndCardCode(apiManage.getAppId(), param.getCard(),param.getTimestamp(),apiManage,false);
        // 获取卡密的所有token信息
        List<Token> tokens = tokenService.getTokenListByCardAndRedis(param.getCard());
        if (CollectionUtil.isNotEmpty(tokens)) {
            //判断token是否存在
            Optional<Token> tokenOptional = tokens.stream().filter(token -> token.getToken().equals(param.getToken())).findAny();
            if (!tokenOptional.isPresent()) {
                //卡密在别的设备上登录
                throw new CommonException(ApiExceptionEnum.CARD_LOGIN_OTHER.getCode(),param.getTimestamp(),apiManage,false);
            }
            //校验是否已过清理时间
            if (DateUtil.offsetMinute(tokenOptional.get().getCreateTime(), CardDateUtil.getClearSpace(appInfoApi.getCodeClearSpace())).compareTo(DateUtil.date()) < 0) {
                //卡密登录过期，请重新登录
                throw new CommonException(ApiExceptionEnum.TOKEN_EXPIRE.getCode(),param.getTimestamp(),apiManage,false);
            }
            if (cardInfoApi.getCardStatus() == 1 && cardInfoApi.getExpireTime().compareTo(DateUtil.date()) < 0) {
                //更新卡密和删除缓存
                CardInfo cardInfo1 = new CardInfo();
                cardInfo1.setCardId(cardInfoApi.getCardId());
                cardInfo1.setCardStatus(CardStatus.EXPIRED.getCode());
                cardInfoService.updateCardAndRedis(apiManage.getAppId(), cardInfo1, param.getCard());
                //卡密已过期
                throw new CommonException(ApiExceptionEnum.CARD_EXPIRE.getCode(),param.getTimestamp(),apiManage,false);
            }
        } else {
            //卡密未登录
            throw new CommonException(ApiExceptionEnum.CARD_NOT_LOGIN.getCode(),param.getTimestamp(),apiManage,false);
        }
        //更新卡密和删除缓存
        CardInfo cardInfo = new CardInfo();
        cardInfo.setCardId(cardInfoApi.getCardId());
        if (StringUtils.isNotEmpty(param.getRemark())){
            cardInfo.setProhibitRemark(param.getRemark());
        }
        cardInfo.setCardStatus(CardStatus.DISABLED.getCode());
        cardInfoService.updateCardAndRedis(apiManage.getAppId(), cardInfo, param.getCard());
        //卡密禁用成功
        throw new CommonException(ApiExceptionEnum.CARD_DISABLE_SUCCESS.getCode(),param.getTimestamp(),apiManage,true);
    }

}
