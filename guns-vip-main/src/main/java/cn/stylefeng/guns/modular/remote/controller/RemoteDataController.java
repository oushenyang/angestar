package cn.stylefeng.guns.modular.remote.controller;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.app.model.params.AppInfoParam;
import cn.stylefeng.guns.modular.app.service.AppInfoService;
import cn.stylefeng.guns.modular.remote.entity.RemoteData;
import cn.stylefeng.guns.modular.remote.model.params.RemoteDataParam;
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
 * 远程数据控制器
 *
 * @author shenyang.ou
 * @Date 2020-05-12 12:35:59
 */
@Controller
@RequestMapping("/remoteData")
public class RemoteDataController extends BaseController {

    private String PREFIX = "/modular/remote";

    @Autowired
    private RemoteDataService remoteDataService;

    @Autowired
    private AppInfoService appInfoService;

    /**
     * 跳转到主页面
     *
     * @author shenyang.ou
     * @Date 2020-05-12
     */
    @RequestMapping("")
    public String index(Model model) {
        //获取当前用户应用列表
        List<AppInfoParam> appInfoParams = appInfoService.getAppInfoList(LoginContextHolder.getContext().getUserId());
        model.addAttribute("appInfoParams", appInfoParams);
        return PREFIX + "/remoteData.html";
    }

    /**
     * 新增页面
     *
     * @author shenyang.ou
     * @Date 2020-05-12
     */
    @RequestMapping("/add")
    public String add(Model model) {
        //获取当前用户应用列表
        List<AppInfoParam> appInfoParams = appInfoService.getAppInfoList(LoginContextHolder.getContext().getUserId());
        model.addAttribute("appInfoParams", appInfoParams);
        return PREFIX + "/remoteData_add.html";
    }

    /**
     * 编辑页面
     *
     * @author shenyang.ou
     * @Date 2020-05-12
     */
    @RequestMapping("/edit")
    public String edit(Model model) {
        //获取当前用户应用列表
        List<AppInfoParam> appInfoParams = appInfoService.getAppInfoList(LoginContextHolder.getContext().getUserId());
        model.addAttribute("appInfoParams", appInfoParams);
        return PREFIX + "/remoteData_edit.html";
    }

    /**
     * 新增接口
     *
     * @author shenyang.ou
     * @Date 2020-05-12
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(RemoteDataParam remoteDataParam) {
        this.remoteDataService.add(remoteDataParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     *
     * @author shenyang.ou
     * @Date 2020-05-12
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(RemoteDataParam remoteDataParam) {
        this.remoteDataService.update(remoteDataParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author shenyang.ou
     * @Date 2020-05-12
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(RemoteDataParam remoteDataParam) {
        this.remoteDataService.delete(remoteDataParam);
        return ResponseData.success();
    }

    /**
     * 批量删除接口
     *
     * @author shenyang.ou
     * @Date 2020-05-12
     */
    @RequestMapping("/batchRemove")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public ResponseData batchRemove(String ids) {
        this.remoteDataService.batchRemove(ids);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author shenyang.ou
     * @Date 2020-05-12
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(RemoteDataParam remoteDataParam) {
        RemoteData detail = this.remoteDataService.getById(remoteDataParam.getDataId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author shenyang.ou
     * @Date 2020-05-12
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(RemoteDataParam remoteDataParam) {
        return this.remoteDataService.findPageBySpec(remoteDataParam);
    }

}


