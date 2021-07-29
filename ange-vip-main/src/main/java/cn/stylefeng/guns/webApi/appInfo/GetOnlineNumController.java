package cn.stylefeng.guns.webApi.appInfo;

import cn.stylefeng.guns.modular.apiManage.service.ApiManageService;
import cn.stylefeng.guns.modular.app.service.AppInfoService;
import cn.stylefeng.guns.sys.core.exception.ApiManageApi;
import cn.stylefeng.guns.sys.core.exception.AppInfoApi;
import cn.stylefeng.guns.sys.core.exception.AppInfoException;
import cn.stylefeng.guns.sys.core.exception.OnlineNumException;
import cn.stylefeng.guns.sys.core.exception.enums.ApiExceptionEnum;
import cn.stylefeng.guns.webApi.common.param.GetAppInfoParam;
import cn.stylefeng.guns.webApi.common.param.GetOnlineNumParam;
import cn.stylefeng.guns.webApi.common.util.RequestUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>取在线人数</p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2020/5/28
 * @since JDK 1.8
 */
@Controller
@RequestMapping("/getOnlineNum")
public class GetOnlineNumController {
    private final ApiManageService apiManageService;
    private final AppInfoService appInfoService;

    public GetOnlineNumController(ApiManageService apiManageService, AppInfoService appInfoService) {
        this.apiManageService = apiManageService;
        this.appInfoService = appInfoService;
    }

    @RequestMapping("/{callCode}")
    @ResponseBody
    public Object getOnlineNum(@PathVariable String callCode, @RequestBody(required=false) String body) {
        //获取接口信息
        ApiManageApi apiManage = apiManageService.getApiManageByRedis("getAppInfo",callCode);
        //获取参数
        GetOnlineNumParam param = RequestUtil.getOnlineNum(apiManage,body);
        //取在线人数
        AppInfoApi appInfoApi =  appInfoService.getAppInfoByRedis(callCode);
        //取在线人数成功
        throw new OnlineNumException(ApiExceptionEnum.ONLINE_NUM_SUCCESS.getCode(), ApiExceptionEnum.ONLINE_NUM_SUCCESS.getMessage(),param.getTimestamp(),apiManage,0,true);
    }

}
