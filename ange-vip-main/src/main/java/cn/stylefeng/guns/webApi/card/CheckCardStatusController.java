package cn.stylefeng.guns.webApi.card;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    public CheckCardStatusController(ApiManageService apiManageService, CardInfoService cardInfoService, RedisUtil redisUtil) {
        this.apiManageService = apiManageService;
        this.cardInfoService = cardInfoService;
        this.redisUtil = redisUtil;
    }

    @RequestMapping("/{callCode}")
    @ResponseBody
    public Object checkCardStatus(@PathVariable String callCode) {
        //获取接口信息
        ApiManageApi apiManage = apiManageService.getApiManageByRedis("cardLogin",callCode);
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
        Map<Object, Object> objects = redisUtil.hmget(RedisType.TOKEN.getCode() + String.valueOf(cardInfoApi.getCardId()));
        List<Token> tokens = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(objects)) {
            for (Map.Entry<Object, Object> m : objects.entrySet()) {
                tokens.add((Token) m.getValue());
            }
        }
        boolean isHave = false;
        if (CollectionUtil.isNotEmpty(tokens)){
            for (Token token : tokens) {
                if (token.getToken().equals(statusCode)) {
                    isHave = true;
                    break;
                }
            }
        }
        if (isHave){
            throw new CardLoginException(200, apiManage.getAppId(),"状态正常！",new Date(),holdCheck,true);
        }else {
            throw new CardLoginException(-206, apiManage.getAppId(),"卡密在别的设备上登录！",new Date(),holdCheck,false);
        }

    }

}
