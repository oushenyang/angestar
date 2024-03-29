package cn.stylefeng.guns.webApi.appInfo;

import cn.stylefeng.guns.modular.apiManage.service.ApiManageService;
import cn.stylefeng.guns.modular.app.service.AppInfoService;
import cn.stylefeng.guns.sys.core.exception.*;
import cn.stylefeng.guns.sys.core.exception.apiResult.ApiManageApi;
import cn.stylefeng.guns.sys.core.exception.apiResult.AppInfoApi;
import cn.stylefeng.guns.sys.core.exception.enums.ApiExceptionEnum;
import cn.stylefeng.guns.webApi.common.param.GetAppInfoParam;
import cn.stylefeng.guns.webApi.common.util.RequestUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>获取应用信息</p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2020/5/28
 * @since JDK 1.8
 */
@Controller
@RequestMapping("/getAppInfo")
public class GetAppInfoController {
    private final ApiManageService apiManageService;
    private final AppInfoService appInfoService;

    public GetAppInfoController(ApiManageService apiManageService,AppInfoService appInfoService) {
        this.apiManageService = apiManageService;
        this.appInfoService = appInfoService;
    }

    @RequestMapping("/{callCode}")
    @ResponseBody
    public Object getAppInfo(@PathVariable String callCode, @RequestBody(required=false) String body) {
        //获取接口信息
        ApiManageApi apiManage = apiManageService.getApiManageByRedis("getAppInfo",callCode);
        //获取参数
        GetAppInfoParam param = RequestUtil.getAppInfoParameter(apiManage,body);
        AppInfoApi appInfoApi =  appInfoService.getAppInfoByRedis(callCode);
        if (appInfoApi.getCydiaFlag()==2){
            //应用已关闭
            throw new AppInfoException(ApiExceptionEnum.APP_CLOSED.getCode(),param.getTimestamp(),apiManage,appInfoApi,false);
        }
        //获取应用信息成功
        throw new AppInfoException(ApiExceptionEnum.APPINFO_SUCCESS.getCode(),param.getTimestamp(),apiManage,appInfoApi,true);
    }

}
