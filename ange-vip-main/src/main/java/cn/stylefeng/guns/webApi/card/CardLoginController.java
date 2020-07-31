package cn.stylefeng.guns.webApi.card;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.core.constant.state.RedisType;
import cn.stylefeng.guns.modular.apiManage.entity.ApiManage;
import cn.stylefeng.guns.modular.apiManage.model.result.ApiManageApi;
import cn.stylefeng.guns.modular.apiManage.service.ApiManageService;
import cn.stylefeng.guns.modular.card.entity.CardInfo;
import cn.stylefeng.guns.modular.card.service.CardInfoService;
import cn.stylefeng.guns.sys.core.auth.util.RedisUtil;
import cn.stylefeng.guns.sys.core.exception.CardApiException;
import cn.stylefeng.guns.sys.core.exception.SystemApiException;
import cn.stylefeng.roses.core.util.HttpContext;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    private final RedisUtil redisUtil;

    public CardLoginController(ApiManageService apiManageService, CardInfoService cardInfoService, RedisUtil redisUtil) {
        this.apiManageService = apiManageService;
        this.cardInfoService = cardInfoService;
        this.redisUtil = redisUtil;
    }

    @RequestMapping("/{callCode}")
    @ResponseBody
    public Object cardLogin(@PathVariable String callCode) {
        Map<String, String[]> cookies = HttpContext.getRequest().getParameterMap();
        //获取接口信息
        ApiManageApi apiManage = apiManageService.getApiManageByRedis("cardLogin",callCode);
        String singleCode = HttpContext.getRequest().getParameter(apiManage.getParameterOne());
        String edition = HttpContext.getRequest().getParameter(apiManage.getParameterTwo());
        String mac = HttpContext.getRequest().getParameter(apiManage.getParameterThree());
        String model = HttpContext.getRequest().getParameter(apiManage.getParameterFour());
        String appCode = HttpContext.getRequest().getParameter(apiManage.getParameterFive());
        String sgin = HttpContext.getRequest().getParameter(apiManage.getParameterSix());
        if (StringUtils.isEmpty(singleCode)||StringUtils.isEmpty(edition)||StringUtils.isEmpty(mac)){
            throw new SystemApiException(-2, "必传参数存在空值");
        }

        CardInfo cardInfo = cardInfoService.getCardInfoByAppIdAndCardCode(apiManage.getAppId(),singleCode);
        //如果卡密查不到，从易游查
        if (ObjectUtil.isNull(cardInfo)){
            throw new SystemApiException(500, "创建多租户-执行sql出现问题！");
        }
        System.out.println(cookies);
        System.out.println(singleCode);
        System.out.println(edition);
        System.out.println(mac);
        System.out.println(model);

        return singleCode;
    }
}
