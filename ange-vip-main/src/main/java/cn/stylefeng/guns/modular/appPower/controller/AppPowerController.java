package cn.stylefeng.guns.modular.appPower.controller;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.app.model.params.AppInfoParam;
import cn.stylefeng.guns.modular.appPower.entity.AppPower;
import cn.stylefeng.guns.modular.appPower.model.params.AppPowerParam;
import cn.stylefeng.guns.modular.appPower.model.result.AppPowerTypeResult;
import cn.stylefeng.guns.modular.appPower.service.AppPowerService;
import cn.stylefeng.guns.modular.appPower.service.AppPowerTypeService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 应用授权表控制器
 *
 * @author shenyang.ou
 * @Date 2020-10-29 15:20:09
 */
@Controller
@RequestMapping("/appPower")
public class AppPowerController extends BaseController {

    private String PREFIX = "/modular/appPower";

    @Autowired
    private AppPowerService appPowerService;

    @Autowired
    private AppPowerTypeService appPowerTypeService;

    /**
     * 跳转到主页面
     *
     * @author shenyang.ou
     * @Date 2020-10-29
     */
    @RequestMapping("")
    public String index(Model model) {
        //获取应用类型列表
        List<AppPowerTypeResult> appPowerTypeResults = appPowerTypeService.getTypeList();
        model.addAttribute("appPowerTypeResults", appPowerTypeResults);
        return PREFIX + "/appPower.html";
    }

    /**
     * 新增页面
     *
     * @author shenyang.ou
     * @Date 2020-10-29
     */
    @RequestMapping("/add")
    public String add(Model model) {
        //获取应用类型列表
        List<AppPowerTypeResult> appPowerTypeResults = appPowerTypeService.getTypeList();
        model.addAttribute("appPowerTypeResults", appPowerTypeResults);
        return PREFIX + "/appPower_add.html";
    }

    /**
     * 编辑页面
     *
     * @author shenyang.ou
     * @Date 2020-10-29
     */
    @RequestMapping("/edit")
    public String edit(Model model) {
        //获取应用类型列表
        List<AppPowerTypeResult> appPowerTypeResults = appPowerTypeService.getTypeList();
        model.addAttribute("appPowerTypeResults", appPowerTypeResults);
        return PREFIX + "/appPower_edit.html";
    }

    /**
     * 新增接口
     *
     * @author shenyang.ou
     * @Date 2020-10-29
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(AppPowerParam appPowerParam) {
        this.appPowerService.add(appPowerParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     *
     * @author shenyang.ou
     * @Date 2020-10-29
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(AppPowerParam appPowerParam) {
        if (appPowerParam.getWhetherSanction()){
            appPowerParam.setSanctionTime(new Date());
        }
        this.appPowerService.update(appPowerParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author shenyang.ou
     * @Date 2020-10-29
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(AppPowerParam appPowerParam) {
        this.appPowerService.delete(appPowerParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author shenyang.ou
     * @Date 2020-10-29
     */
    @RequestMapping("/deleteUser")
    @ResponseBody
    public ResponseData deleteUser(AppPowerParam appPowerParam) {
        this.appPowerService.deleteUser(appPowerParam);
        return ResponseData.success();
    }

    /**
     * 制裁接口
     *
     * @author shenyang.ou
     * @Date 2020-10-29
     */
    @RequestMapping("/sanction")
    @ResponseBody
    public ResponseData sanction(AppPowerParam appPowerParam) {
        this.appPowerService.sanction(appPowerParam);
        return ResponseData.success();
    }

    /**
     * 批量删除接口
     *
     * @author shenyang.ou
     * @Date 2020-10-29
     */
    @RequestMapping("/batchRemove")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public ResponseData batchRemove(String ids) {
        this.appPowerService.batchRemove(ids);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author shenyang.ou
     * @Date 2020-10-29
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(AppPowerParam appPowerParam) {
        AppPower detail = this.appPowerService.getById(appPowerParam.getAppPowerId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author shenyang.ou
     * @Date 2020-10-29
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(AppPowerParam appPowerParam) {
//        return this.appPowerService.findPageBySpec(appPowerParam);

        //获取分页参数
        Page page = LayuiPageFactory.defaultPage();
        //根据条件查询操作日志
        List<Map<String, Object>> result = appPowerService.findListBySpec(page, appPowerParam);
        page.setRecords(result);
        return LayuiPageFactory.createPageInfo(page);
    }

}


