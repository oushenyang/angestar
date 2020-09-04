package cn.stylefeng.guns.modular.apiManage.controller;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.app.model.params.AppInfoParam;
import cn.stylefeng.guns.modular.app.service.AppInfoService;
import cn.stylefeng.guns.sys.modular.system.entity.ApiResult;
import cn.stylefeng.guns.sys.modular.system.entity.Dict;
import cn.stylefeng.guns.modular.apiManage.model.params.ApiResultParam;
import cn.stylefeng.guns.modular.apiManage.service.ApiResultService;
import cn.stylefeng.guns.sys.modular.system.service.DictService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


/**
 * 接口自定义返回控制器
 *
 * @author shenyang.ou
 * @Date 2020-07-31 13:36:51
 */
@Controller
@RequestMapping("/apiResult")
public class ApiResultController extends BaseController {

    private String PREFIX = "/modular/system/apiResult";

    private final ApiResultService apiResultService;

    private final DictService dictService;

    private final AppInfoService appInfoService;

    public ApiResultController(ApiResultService apiResultService, DictService dictService, AppInfoService appInfoService) {
        this.apiResultService = apiResultService;
        this.dictService = dictService;
        this.appInfoService = appInfoService;
    }

    /**
     * 跳转到主页面
     *
     * @author shenyang.ou
     * @Date 2020-07-31
     */
    @RequestMapping("")
    public String index(Model model,Integer type) {
        model.addAttribute("type", type);
        //获取当前用户应用列表
        List<AppInfoParam> appInfoParams = appInfoService.getAppInfoList(LoginContextHolder.getContext().getUserId());
        if (CollectionUtils.isNotEmpty(appInfoParams)){
            model.addAttribute("firstAppId", appInfoParams.get(0).getAppId());
        }else {
            model.addAttribute("firstAppId", 0);
        }
        model.addAttribute("appInfoParams", appInfoParams);
        if (type==0){
            return PREFIX + "/apiResult.html";
        }else {
            return PREFIX + "/appApiResult.html";
        }
    }

    /**
     * 新增页面
     *
     * @author shenyang.ou
     * @Date 2020-07-31
     */
    @RequestMapping("/add")
    public String add(Model model) {
        List<Dict> dicts =  dictService.listDictsByCode("result_type");
        model.addAttribute("dicts", dicts);
        return PREFIX + "/apiResult_add.html";
    }

    /**
     * 编辑页面
     *
     * @author shenyang.ou
     * @Date 2020-07-31
     */
    @RequestMapping("/edit")
    public String edit(Model model) {
        List<Dict> dicts =  dictService.listDictsByCode("result_type");
        model.addAttribute("dicts", dicts);
        return PREFIX + "/apiResult_edit.html";
    }

    /**
     * 新增接口
     *
     * @author shenyang.ou
     * @Date 2020-07-31
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(ApiResultParam apiResultParam) {
        this.apiResultService.add(apiResultParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     *
     * @author shenyang.ou
     * @Date 2020-07-31
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(ApiResultParam apiResultParam) {
        this.apiResultService.update(apiResultParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author shenyang.ou
     * @Date 2020-07-31
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(ApiResultParam apiResultParam) {
        this.apiResultService.delete(apiResultParam);
        return ResponseData.success();
    }

    /**
     * 批量删除接口
     *
     * @author shenyang.ou
     * @Date 2020-07-31
     */
    @RequestMapping("/batchRemove")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public ResponseData batchRemove(String ids) {
        this.apiResultService.batchRemove(ids);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author shenyang.ou
     * @Date 2020-07-31
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(ApiResultParam apiResultParam) {
        ApiResult detail = this.apiResultService.getById(apiResultParam.getApiResultId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author shenyang.ou
     * @Date 2020-07-31
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(ApiResultParam apiResultParam) {
//        return this.apiResultService.findPageBySpec(apiResultParam);

        //获取分页参数
        Page page = LayuiPageFactory.defaultPage();
        apiResultParam.setCreateUser(LoginContextHolder.getContext().getUserId());
        //根据条件查询操作日志
        List<Map<String, Object>> result = apiResultService.findListBySpec(page, apiResultParam);
        page.setRecords(result);
        return LayuiPageFactory.createPageInfo(page);
    }

}


