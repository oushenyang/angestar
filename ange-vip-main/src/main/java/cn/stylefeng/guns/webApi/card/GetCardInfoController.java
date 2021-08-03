package cn.stylefeng.guns.webApi.card;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.sys.core.exception.*;
import cn.stylefeng.guns.modular.apiManage.service.ApiManageService;
import cn.stylefeng.guns.sys.core.exception.apiResult.ApiManageApi;
import cn.stylefeng.guns.sys.core.exception.apiResult.CardInfoApi;
import cn.stylefeng.guns.modular.card.service.CardInfoService;
import cn.stylefeng.guns.sys.core.exception.enums.ApiExceptionEnum;
import cn.stylefeng.guns.webApi.common.param.GetCardInfoParam;
import cn.stylefeng.guns.webApi.common.util.RequestUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * <p>获取单码用户信息</p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2020/5/28
 * @since JDK 1.8
 */
@Controller
@RequestMapping("/getCardInfo")
public class GetCardInfoController {
    private final ApiManageService apiManageService;
    private final CardInfoService cardInfoService;

    public GetCardInfoController(ApiManageService apiManageService, CardInfoService cardInfoService) {
        this.apiManageService = apiManageService;
        this.cardInfoService = cardInfoService;
    }

    @RequestMapping("/{callCode}")
    @ResponseBody
    public Object getCardInfo(@PathVariable String callCode, @RequestBody(required=false) String body) {
        //获取接口信息
        ApiManageApi apiManage = apiManageService.getApiManageByRedis("getCardInfo",callCode);
        //获取参数
        GetCardInfoParam param = RequestUtil.getCardInfoParameter(apiManage,body);
        CardInfoApi cardInfoApi = cardInfoService.getCardInfoApiByAppIdAndCardCode(apiManage.getAppId(),param.getCard(),param.getTimestamp(),apiManage,false);
        throw new GetCardInfoException(ApiExceptionEnum.GET_CARD_SUCCESS.getCode(),param.getTimestamp(),apiManage,cardInfoApi,true);
    }

}
