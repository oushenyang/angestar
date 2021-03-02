package cn.stylefeng.guns.modular.appPower.controller;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.appPower.entity.AppPowerType;
import cn.stylefeng.guns.modular.appPower.model.params.AppPowerTypeParam;
import cn.stylefeng.guns.modular.appPower.service.AppPowerTypeService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 应用类型表控制器
 *
 * @author shenyang.ou
 * @Date 2020-10-29 15:20:09
 */
@Controller
@RequestMapping("/appPowerType")
public class AppPowerTypeController extends BaseController {

    private String PREFIX = "/modular/appPowerType";

    @Autowired
    private AppPowerTypeService appPowerTypeService;

    /**
     * 跳转到主页面
     *
     * @author shenyang.ou
     * @Date 2020-10-29
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/appPowerType.html";
    }

    /**
     * 新增页面
     *
     * @author shenyang.ou
     * @Date 2020-10-29
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/appPowerType_add.html";
    }

    /**
     * 编辑页面
     *
     * @author shenyang.ou
     * @Date 2020-10-29
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/appPowerType_edit.html";
    }

    /**
     * 新增接口
     *
     * @author shenyang.ou
     * @Date 2020-10-29
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(AppPowerTypeParam appPowerTypeParam) {
        this.appPowerTypeService.add(appPowerTypeParam);
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
    public ResponseData editItem(AppPowerTypeParam appPowerTypeParam) {
        this.appPowerTypeService.update(appPowerTypeParam);
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
    public ResponseData delete(AppPowerTypeParam appPowerTypeParam) {
        this.appPowerTypeService.delete(appPowerTypeParam);
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
        this.appPowerTypeService.batchRemove(ids);
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
    public ResponseData detail(AppPowerTypeParam appPowerTypeParam) {
        AppPowerType detail = this.appPowerTypeService.getById(appPowerTypeParam.getAppPowerTypeId());
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
    public LayuiPageInfo list(AppPowerTypeParam appPowerTypeParam) {
        return this.appPowerTypeService.findPageBySpec(appPowerTypeParam);
    }

}


