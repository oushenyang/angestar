package cn.stylefeng.guns.webApi.card;

import cn.hutool.core.collection.CollectionUtil;
import cn.stylefeng.guns.modular.apiManage.service.ApiManageService;
import cn.stylefeng.guns.modular.card.service.CardInfoService;
import cn.stylefeng.guns.modular.demos.service.AsyncService;
import cn.stylefeng.guns.modular.device.entity.Device;
import cn.stylefeng.guns.modular.device.service.DeviceService;
import cn.stylefeng.guns.sys.core.auth.util.RedisUtil;
import cn.stylefeng.guns.sys.core.constant.state.RedisType;
import cn.stylefeng.guns.sys.core.exception.CommonException;
import cn.stylefeng.guns.sys.core.exception.apiResult.ApiManageApi;
import cn.stylefeng.guns.sys.core.exception.apiResult.CardInfoApi;
import cn.stylefeng.guns.sys.core.exception.enums.ApiExceptionEnum;
import cn.stylefeng.guns.sys.core.util.IpUtils;
import cn.stylefeng.guns.webApi.common.param.CardUnbindParam;
import cn.stylefeng.guns.webApi.common.util.RequestUtil;
import cn.stylefeng.roses.core.util.HttpContext;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * <p>卡密解绑</p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2020/5/28
 * @since JDK 1.8
 */
@Controller
@RequestMapping("/cardUnbind")
public class CardUnbindController {
    private final ApiManageService apiManageService;
    private final CardInfoService cardInfoService;
    private final DeviceService deviceService;
    private final RedisUtil redisUtil;
    private final AsyncService asyncService;

    public CardUnbindController(ApiManageService apiManageService, CardInfoService cardInfoService, DeviceService deviceService, RedisUtil redisUtil, AsyncService asyncService) {
        this.apiManageService = apiManageService;
        this.cardInfoService = cardInfoService;
        this.deviceService = deviceService;
        this.redisUtil = redisUtil;
        this.asyncService = asyncService;
    }

    @RequestMapping("/{callCode}")
    @ResponseBody
    public Object cardUnbind(@PathVariable String callCode, @RequestBody(required=false) String body) {
        //获取接口信息
        ApiManageApi apiManage = apiManageService.getApiManageByRedis("cardUnbind",callCode);
        //获取参数
        CardUnbindParam param = RequestUtil.cardUnbindParameter(apiManage,body);
        CardInfoApi cardInfoApi = cardInfoService.getCardInfoApiByAppIdAndCardCode(apiManage.getAppId(),param.getCard(),param.getTimestamp(),apiManage,false);
        List<Device> devices = deviceService.list(new QueryWrapper<Device>().eq("card_or_user_id", cardInfoApi.getCardId()));
        if (CollectionUtil.isEmpty(devices)){
            //卡密已经解绑，不可重复操作
            throw new CommonException(ApiExceptionEnum.CARD_UNBIND_ALREADY.getCode(),param.getTimestamp(),apiManage,false);
        }
        //删除卡密缓存
        redisUtil.del(RedisType.CARD_INFO.getCode() + param.getCard());
        deviceService.remove(new QueryWrapper<Device>().eq("card_or_user_id", cardInfoApi.getCardId()));
        if (StringUtils.isNotEmpty(param.getMac())){
            Date date = new Date();
            Device device = new Device();
            device.setDeviceId(IdWorker.getId());
            device.setAppId(apiManage.getAppId());
            device.setCardOrUserId(cardInfoApi.getCardId());
            device.setCardType(1);
            device.setMac(param.getMac());
            device.setIp(IpUtils.getIpAddr(HttpContext.getRequest()));
//            device.setModel(model);
//        device.setIpAddress(IpToRegionUtil.ipToRegion(getIp()));
            device.setLoginNum(1);
            device.setCreateTime(date);
            device.setUpdateTime(date);
            //异步调用插入
            asyncService.insertDevice(device);
        }
        //卡密解绑成功
        throw new CommonException(ApiExceptionEnum.CARD_UNBIND_SUCCESS.getCode(),param.getTimestamp(),apiManage,true);
    }

}
