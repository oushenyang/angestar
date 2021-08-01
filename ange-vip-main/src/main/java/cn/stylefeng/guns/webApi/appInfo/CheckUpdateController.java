package cn.stylefeng.guns.webApi.appInfo;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.modular.apiManage.service.ApiManageService;
import cn.stylefeng.guns.modular.app.service.AppEditionService;
import cn.stylefeng.guns.sys.core.exception.*;
import cn.stylefeng.guns.sys.core.exception.apiResult.ApiAppEdition;
import cn.stylefeng.guns.sys.core.exception.apiResult.ApiManageApi;
import cn.stylefeng.guns.sys.core.exception.enums.ApiExceptionEnum;
import cn.stylefeng.guns.webApi.common.param.GetEditionParam;
import cn.stylefeng.guns.webApi.common.util.RequestUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>检查版本更新</p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2020/5/28
 * @since JDK 1.8
 */
@Controller
@RequestMapping("/checkUpdate")
public class CheckUpdateController {
    private final ApiManageService apiManageService;
    private final AppEditionService appEditionService;

    public CheckUpdateController(ApiManageService apiManageService, AppEditionService appEditionService) {
        this.apiManageService = apiManageService;
        this.appEditionService = appEditionService;
    }

    @RequestMapping("/{callCode}")
    @ResponseBody
    public Object checkUpdate(@PathVariable String callCode, @RequestBody(required=false) String body) {
        //获取接口信息
        ApiManageApi apiManage = apiManageService.getApiManageByRedis("checkUpdate",callCode);
        //获取参数
        GetEditionParam param = RequestUtil.getEditionParameter(apiManage,body);
        ApiAppEdition apiAppEdition =  appEditionService.getNewestAppEditionByRedis(apiManage.getAppId());
        //如果版本号相等，说明没有更新
        if (ObjectUtil.isNull(apiAppEdition)||apiAppEdition.getEditionNum().equals(param.getEdition())){
            throw new CommonException(ApiExceptionEnum.ALREADY_LATEST_VERSION.getCode(),param.getTimestamp(),apiManage,false);
        }else {
            throw new CheckUpdateException(ApiExceptionEnum.CHECK_UPDATE_SUCCESS.getCode(),param.getTimestamp(),apiManage,apiAppEdition,true);
        }
    }

}
