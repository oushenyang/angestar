package cn.stylefeng.guns.modular.app.controller;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.log.BussinessLog;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.core.constant.dictmap.AppInfoMap;
import cn.stylefeng.guns.modular.app.entity.AppInfo;
import cn.stylefeng.guns.modular.app.model.params.AppInfoParam;
import cn.stylefeng.guns.modular.app.service.AppInfoService;
import cn.stylefeng.guns.sys.core.constant.dictmap.NoticeMap;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Date;


/**
 * 软件表控制器
 *
 * @author shenyang.ou
 * @Date 2020-04-01 21:52:12
 */
@Controller
@RequestMapping("/appInfo")
public class AppInfoController extends BaseController {

    private String PREFIX = "/modular/appInfo";

    @Autowired
    private AppInfoService appInfoService;

    /**
     * 跳转到主页面
     *
     * @author shenyang.ou
     * @Date 2020-04-01
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/appInfo.html";
    }

    /**
     * 新增页面
     *
     * @author shenyang.ou
     * @Date 2020-04-01
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/appInfo_add.html";
    }

    /**
     * 编辑页面
     *
     * @author shenyang.ou
     * @Date 2020-04-01
     */
    @RequestMapping("/edit")
    public String edit(@RequestParam("event") String event, Model model) {
        model.addAttribute("event", event);
        return PREFIX + "/appInfo_edit_new.html";
    }

    /**
     * 新增接口
     *
     * @author shenyang.ou
     * @Date 2020-04-01
     */
    @RequestMapping("/addItem")
    @ResponseBody
    @BussinessLog(value = "新增应用", key = "appName", dict = AppInfoMap.class)
    public ResponseData addItem(AppInfoParam appInfoParam) {
        try {
            appInfoParam.setCreateUser(LoginContextHolder.getContext().getUserId());
            appInfoParam.setCreateTime(new Date());
            this.appInfoService.add(appInfoParam);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseData.error("生成头像失败");
        }
        return ResponseData.success();
    }

    /**
     * 编辑接口
     *
     * @author shenyang.ou
     * @Date 2020-04-01
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(AppInfoParam appInfoParam) {
        this.appInfoService.update(appInfoParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author shenyang.ou
     * @Date 2020-04-01
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(AppInfoParam appInfoParam) {
        this.appInfoService.delete(appInfoParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author shenyang.ou
     * @Date 2020-04-01
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(AppInfoParam appInfoParam) {
        AppInfo detail = this.appInfoService.getById(appInfoParam.getAppId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author shenyang.ou
     * @Date 2020-04-01
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(AppInfoParam appInfoParam) {
        return this.appInfoService.findPageBySpec(appInfoParam);
    }

}


