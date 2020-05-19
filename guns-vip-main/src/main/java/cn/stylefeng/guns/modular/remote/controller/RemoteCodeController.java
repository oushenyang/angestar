package cn.stylefeng.guns.modular.remote.controller;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.app.model.params.AppInfoParam;
import cn.stylefeng.guns.modular.app.service.AppInfoService;
import cn.stylefeng.guns.modular.remote.entity.RemoteCode;
import cn.stylefeng.guns.modular.remote.model.params.RemoteCodeParam;
import cn.stylefeng.guns.modular.remote.service.RemoteCodeService;
import cn.stylefeng.guns.modular.remote.service.RemoteDataService;
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
 * 远程代码控制器
 *
 * @author shenyang.ou
 * @Date 2020-05-19 10:28:24
 */
@Controller
@RequestMapping("/remoteCode")
public class RemoteCodeController extends BaseController {

    private String PREFIX = "/modular/remote";

    private final RemoteCodeService remoteCodeService;

    private final AppInfoService appInfoService;

    public RemoteCodeController(AppInfoService appInfoService, RemoteCodeService remoteCodeService) {
        this.appInfoService = appInfoService;
        this.remoteCodeService = remoteCodeService;
    }

    /**
     * 跳转到主页面
     *
     * @author shenyang.ou
     * @Date 2020-05-19
     */
    @RequestMapping("")
    public String index(Model model) {
        //获取当前用户应用列表
        List<AppInfoParam> appInfoParams = appInfoService.getAppInfoList(LoginContextHolder.getContext().getUserId());
        model.addAttribute("appInfoParams", appInfoParams);
        return PREFIX + "/remoteCode.html";
    }

    /**
     * 新增页面
     *
     * @author shenyang.ou
     * @Date 2020-05-19
     */
    @RequestMapping("/add")
    public String add(Model model) {
        //获取当前用户应用列表
        List<AppInfoParam> appInfoParams = appInfoService.getAppInfoList(LoginContextHolder.getContext().getUserId());
        model.addAttribute("appInfoParams", appInfoParams);
        return PREFIX + "/remoteCode_add.html";
    }

    /**
     * 编辑页面
     *
     * @author shenyang.ou
     * @Date 2020-05-19
     */
    @RequestMapping("/edit")
    public String edit(Model model) {
        //获取当前用户应用列表
        List<AppInfoParam> appInfoParams = appInfoService.getAppInfoList(LoginContextHolder.getContext().getUserId());
        model.addAttribute("appInfoParams", appInfoParams);
        return PREFIX + "/remoteCode_edit.html";
    }

    /**
     * 新增接口
     *
     * @author shenyang.ou
     * @Date 2020-05-19
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(RemoteCodeParam remoteCodeParam) {
        this.remoteCodeService.add(remoteCodeParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     *
     * @author shenyang.ou
     * @Date 2020-05-19
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(RemoteCodeParam remoteCodeParam) {
        this.remoteCodeService.update(remoteCodeParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author shenyang.ou
     * @Date 2020-05-19
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(RemoteCodeParam remoteCodeParam) {
        this.remoteCodeService.delete(remoteCodeParam);
        return ResponseData.success();
    }

    /**
     * 批量删除接口
     *
     * @author shenyang.ou
     * @Date 2020-05-19
     */
    @RequestMapping("/batchRemove")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public ResponseData batchRemove(String ids) {
        this.remoteCodeService.batchRemove(ids);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author shenyang.ou
     * @Date 2020-05-19
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(RemoteCodeParam remoteCodeParam) {
        RemoteCode detail = this.remoteCodeService.getById(remoteCodeParam.getCodeId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author shenyang.ou
     * @Date 2020-05-19
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(RemoteCodeParam remoteCodeParam) {
        return this.remoteCodeService.findPageBySpec(remoteCodeParam);
    }

}


