package cn.stylefeng.guns.modular.apiManage.controller;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.apiManage.entity.ApiManage;
import cn.stylefeng.guns.modular.apiManage.model.params.ApiManageParam;
import cn.stylefeng.guns.modular.apiManage.service.ApiManageService;
import cn.stylefeng.guns.modular.app.model.params.AppInfoParam;
import cn.stylefeng.guns.modular.app.service.AppInfoService;
import cn.stylefeng.guns.sys.modular.system.entity.Dict;
import cn.stylefeng.guns.sys.modular.system.service.DictService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


/**
 * 接口管理控制器
 *
 * @author shenyang.ou
 * @Date 2020-05-21 14:26:06
 */
@Controller
@RequestMapping("/apiManage")
public class ApiManageController extends BaseController {

    private String PREFIX = "/modular/apiManage";

    private final ApiManageService apiManageService;

    private final DictService dictService;

    private final AppInfoService appInfoService;

    public ApiManageController(ApiManageService apiManageService, DictService dictService, AppInfoService appInfoService) {
        this.apiManageService = apiManageService;
        this.dictService = dictService;
        this.appInfoService = appInfoService;
    }

    /**
     * 跳转到主页面
     *
     * @author shenyang.ou
     * @Date 2020-05-21
     */
    @RequestMapping("")
    public String index(Model model,int type) {
        model.addAttribute("type", type);
        //获取当前用户应用列表
        List<AppInfoParam> appInfoParams = appInfoService.getAppInfoList(LoginContextHolder.getContext().getUserId());
        if (CollectionUtils.isNotEmpty(appInfoParams)){
            model.addAttribute("firstAppId", appInfoParams.get(0).getAppId());
        }else {
            model.addAttribute("firstAppId", 0);
        }
        model.addAttribute("appInfoParams", appInfoParams);
        if (type==1){
            return PREFIX + "/apiManageList.html";
        }else {
            return PREFIX + "/apiManage.html";
        }

    }

    /**
     * 新增页面
     *
     * @author shenyang.ou
     * @Date 2020-05-21
     */
    @RequestMapping("/add")
    public String add(Model model) {
        List<Dict> dicts =  dictService.listDictsByCode("API_TYPE");
        model.addAttribute("dicts", dicts);
        return PREFIX + "/apiManage_add.html";
    }

    /**
     * 编辑页面
     *
     * @author shenyang.ou
     * @Date 2020-05-21
     */
    @RequestMapping("/edit")
    public String edit(Model model) {
        List<Dict> dicts =  dictService.listDictsByCode("API_TYPE");
        model.addAttribute("dicts", dicts);
        return PREFIX + "/apiManage_edit.html";
    }

    /**
     * 用户编辑页面
     *
     * @author shenyang.ou
     * @Date 2020-05-21
     */
    @RequestMapping("/appEdit")
    public String appEdit(Model model) {
        List<Dict> dicts =  dictService.listDictsByCode("API_TYPE");
        model.addAttribute("dicts", dicts);
        return PREFIX + "/appApiManage_edit.html";
    }

    /**
     * 新增接口
     *
     * @author shenyang.ou
     * @Date 2020-05-21
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(ApiManageParam apiManageParam) {
        this.apiManageService.add(apiManageParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     *
     * @author shenyang.ou
     * @Date 2020-05-21
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(ApiManageParam apiManageParam) {
        this.apiManageService.update(apiManageParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author shenyang.ou
     * @Date 2020-05-21
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(ApiManageParam apiManageParam) {
        this.apiManageService.delete(apiManageParam);
        return ResponseData.success();
    }

    /**
     * 批量删除接口
     *
     * @author shenyang.ou
     * @Date 2020-05-21
     */
    @RequestMapping("/batchRemove")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public ResponseData batchRemove(String ids) {
        this.apiManageService.batchRemove(ids);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author shenyang.ou
     * @Date 2020-05-21
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(ApiManageParam apiManageParam) {
        ApiManage detail = this.apiManageService.getById(apiManageParam.getApiManageId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author shenyang.ou
     * @Date 2020-05-21
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(ApiManageParam apiManageParam) {
        //获取分页参数
        Page page = LayuiPageFactory.defaultPage();
        apiManageParam.setCreateUser(LoginContextHolder.getContext().getUserId());
        //根据条件查询操作日志
        List<Map<String, Object>> result = apiManageService.findListBySpec(page, apiManageParam);
        page.setRecords(result);
        return LayuiPageFactory.createPageInfo(page);
    }

}


