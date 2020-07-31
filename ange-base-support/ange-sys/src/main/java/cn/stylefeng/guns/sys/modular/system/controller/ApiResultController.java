package cn.stylefeng.guns.sys.modular.system.controller;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.sys.modular.system.entity.ApiResult;
import cn.stylefeng.guns.sys.modular.system.entity.Dict;
import cn.stylefeng.guns.sys.modular.system.model.params.ApiResultParam;
import cn.stylefeng.guns.sys.modular.system.service.ApiResultService;
import cn.stylefeng.guns.sys.modular.system.service.DictService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


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

    public ApiResultController(ApiResultService apiResultService, DictService dictService) {
        this.apiResultService = apiResultService;
        this.dictService = dictService;
    }

    /**
     * 跳转到主页面
     *
     * @author shenyang.ou
     * @Date 2020-07-31
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/apiResult.html";
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
        return this.apiResultService.findPageBySpec(apiResultParam);
    }

}


