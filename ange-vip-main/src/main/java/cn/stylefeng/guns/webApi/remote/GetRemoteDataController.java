package cn.stylefeng.guns.webApi.remote;

import cn.stylefeng.guns.modular.app.service.AppInfoService;
import cn.stylefeng.guns.modular.remote.model.result.RemoteDataApi;
import cn.stylefeng.guns.modular.remote.service.RemoteDataService;
import cn.stylefeng.guns.sys.core.exception.AppInfoApi;
import cn.stylefeng.guns.sys.core.exception.SystemApiException;
import cn.stylefeng.roses.core.util.HttpContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;

/**
 * <p>检测单码用户状态</p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2020/5/28
 * @since JDK 1.8
 */
@Controller
@RequestMapping("/getRemoteData")
public class GetRemoteDataController {
//    private final ApiManageService apiManageService;
    private final RemoteDataService remoteDataService;
    private final AppInfoService appInfoService;

    public GetRemoteDataController(RemoteDataService remoteDataService, AppInfoService appInfoService) {
//        this.apiManageService = apiManageService;
        this.remoteDataService = remoteDataService;
        this.appInfoService = appInfoService;
    }

//    @RequestMapping("/{callCode}")
    @RequestMapping(value = "/{callCode}/**")
    @ResponseBody
    public Object getRemoteData(@PathVariable String callCode) {
        //获取接口信息
//        ApiManageApi apiManage = apiManageService.getApiManageByRedis("getRemoteData",callCode);
        AppInfoApi appInfoApi =  appInfoService.getAppInfoByRedis(callCode);
        String dataCode = (String) HttpContext.getRequest().getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        dataCode = dataCode.replace("/getRemoteData/"+callCode+"/","");
        //获取参数
        if (StringUtils.isEmpty(dataCode)){
            throw new SystemApiException(2, "必传参数存在空值","",false);
        }
        RemoteDataApi remoteData =  remoteDataService.getRemoteDataByRedis(appInfoApi.getAppId(),dataCode);
        return remoteData.getDataValue();

    }

}
