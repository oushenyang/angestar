package cn.stylefeng.guns.webApi.card;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.modular.apiManage.model.result.ApiManageApi;
import cn.stylefeng.guns.modular.apiManage.service.ApiManageService;
import cn.stylefeng.guns.modular.app.service.AppInfoService;
import cn.stylefeng.guns.modular.card.model.result.CardInfoApi;
import cn.stylefeng.guns.modular.card.service.CardInfoService;
import cn.stylefeng.guns.sys.core.auth.util.RedisUtil;
import cn.stylefeng.guns.sys.core.exception.AppInfoApi;
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
 * <p>获取单码用户到期时间</p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2020/5/28
 * @since JDK 1.8
 */
@Controller
@RequestMapping("/getCardExpired")
public class GetCardExpiredController {
    private final ApiManageService apiManageService;
    private final CardInfoService cardInfoService;
    private final AppInfoService appInfoService;
    private final RedisUtil redisUtil;

    public GetCardExpiredController(ApiManageService apiManageService, CardInfoService cardInfoService, AppInfoService appInfoService, RedisUtil redisUtil) {
        this.apiManageService = apiManageService;
        this.cardInfoService = cardInfoService;
        this.appInfoService = appInfoService;
        this.redisUtil = redisUtil;
    }

    @RequestMapping("/{callCode}")
    @ResponseBody
    public Object getCardExpired(@PathVariable String callCode) {
        //获取接口信息
        ApiManageApi apiManage = apiManageService.getApiManageByRedis("cardLogin",callCode);
        String singleCode = HttpContext.getRequest().getParameter(apiManage.getParameterTwo());
        String holdCheck = HttpContext.getRequest().getParameter(apiManage.getParameterFive());
        String sign = HttpContext.getRequest().getParameter(apiManage.getParameterSix());
        if (StringUtils.isEmpty(singleCode)){
            throw new SystemApiException(2, "必传参数存在空值","",false);
        }
        CardInfoApi cardInfoApi = cardInfoService.getCardInfoApiByAppIdAndCardCode(apiManage.getAppId(),singleCode);
        AppInfoApi appInfoApi =  appInfoService.getAppInfoByRedis(callCode);
        //如果卡密查不到
        if (ObjectUtil.isNull(cardInfoApi)){
            throw new CardLoginException(2001, apiManage.getAppId(),"卡密不存在！",new Date(),holdCheck,appInfoApi,false);
        }else {
            throw new CardLoginException(2000, apiManage.getAppId(),"成功！",new Date(),holdCheck,appInfoApi,false);
        }
    }

}
