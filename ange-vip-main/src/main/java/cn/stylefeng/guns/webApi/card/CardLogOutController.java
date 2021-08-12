package cn.stylefeng.guns.webApi.card;

import cn.hutool.core.collection.CollectionUtil;
import cn.stylefeng.guns.modular.apiManage.service.ApiManageService;
import cn.stylefeng.guns.modular.demos.service.AsyncService;
import cn.stylefeng.guns.modular.device.entity.Token;
import cn.stylefeng.guns.modular.device.service.TokenService;
import cn.stylefeng.guns.sys.core.auth.util.RedisUtil;
import cn.stylefeng.guns.sys.core.constant.state.RedisExpireTime;
import cn.stylefeng.guns.sys.core.constant.state.RedisType;
import cn.stylefeng.guns.sys.core.exception.CommonException;
import cn.stylefeng.guns.sys.core.exception.apiResult.ApiManageApi;
import cn.stylefeng.guns.sys.core.exception.enums.ApiExceptionEnum;
import cn.stylefeng.guns.webApi.common.param.CardLogOutParam;
import cn.stylefeng.guns.webApi.common.util.RequestUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

/**
 * <p>卡密退出登录</p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2020/5/28
 * @since JDK 1.8
 */
@Controller
@RequestMapping("/cardLogOut")
public class CardLogOutController {
    private final ApiManageService apiManageService;
    private final AsyncService asyncService;
    private final TokenService tokenService;
    private final RedisUtil redisUtil;

    public CardLogOutController(ApiManageService apiManageService, AsyncService asyncService, TokenService tokenService, RedisUtil redisUtil) {
        this.apiManageService = apiManageService;
        this.asyncService = asyncService;
        this.tokenService = tokenService;
        this.redisUtil = redisUtil;
    }

    @RequestMapping("/{callCode}")
    @ResponseBody
    public Object cardLogOut(@PathVariable String callCode, @RequestBody(required=false) String body) {
        //获取接口信息
        ApiManageApi apiManage = apiManageService.getApiManageByRedis("cardLogOut",callCode);
        //获取参数
        CardLogOutParam param = RequestUtil.cardLogOutParameter(apiManage,body);
        // 获取卡密的所有token信息
        List<Token> tokens = tokenService.getTokenListByCardAndRedis(param.getCard());
        if (CollectionUtil.isNotEmpty(tokens)) {
            //判断token是否存在
            Optional<Token> tokenOptional = tokens.stream().filter(token -> token.getToken().equals(param.getToken())).findAny();
            if (!tokenOptional.isPresent()) {
                //卡密在别的设备上登录
                throw new CommonException(ApiExceptionEnum.CARD_LOGIN_OTHER.getCode(),param.getTimestamp(),apiManage,false);
            }else {
                //异步调用先删除
                asyncService.delToken(tokenOptional.get().getTokenId());
                tokens.remove(tokenOptional.get());
                redisUtil.hset(RedisType.CARD_INFO.getCode() + param.getCard(),RedisType.TOKEN.getCode(), JSON.toJSONString(tokens), RedisExpireTime.DAY.getCode());
                //卡密退出登录成功
                throw new CommonException(ApiExceptionEnum.CARD_LOGOUT_SUCCESS.getCode(),param.getTimestamp(),apiManage,true);
            }
        } else {
            //卡密未登录
            throw new CommonException(ApiExceptionEnum.CARD_NOT_LOGIN.getCode(),param.getTimestamp(),apiManage,false);
        }
    }

}
