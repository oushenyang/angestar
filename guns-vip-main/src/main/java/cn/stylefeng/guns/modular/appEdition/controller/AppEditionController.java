package cn.stylefeng.guns.modular.appEdition.controller;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.app.model.params.AppInfoParam;
import cn.stylefeng.guns.modular.app.service.AppInfoService;
import cn.stylefeng.guns.modular.appEdition.entity.AppEdition;
import cn.stylefeng.guns.modular.appEdition.model.params.AppEditionParam;
import cn.stylefeng.guns.modular.appEdition.service.AppEditionService;
import cn.stylefeng.guns.sys.core.exception.enums.BizExceptionEnum;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


/**
 * 应用版本表控制器
 *
 * @author shenyang.ou
 * @Date 2020-04-12 21:03:38
 */
@Controller
@RequestMapping("/appEdition")
public class AppEditionController extends BaseController {

    private String PREFIX = "/modular/appEdition";

    @Autowired
    private AppEditionService appEditionService;

    @Autowired
    private AppInfoService appInfoService;

    /**
     * 跳转到主页面
     *
     * @author shenyang.ou
     * @Date 2020-04-12
     */
    @RequestMapping("")
    public String index(Model model) {
        //获取当前用户应用列表
        List<AppInfoParam> appInfoParams = appInfoService.getAppInfoList(LoginContextHolder.getContext().getUserId());
        model.addAttribute("appInfoParams", appInfoParams);
        model.addAttribute("appId", 0);
        return PREFIX + "/appEdition.html";
    }

    /**
     * 新增页面
     *
     * @author shenyang.ou
     * @Date 2020-04-12
     */
    @RequestMapping("/add")
    public String add(Model model) {
        //获取当前用户应用列表
        List<AppInfoParam> appInfoParams = appInfoService.getAppInfoList(LoginContextHolder.getContext().getUserId());
        model.addAttribute("appInfoParams", appInfoParams);
        return PREFIX + "/appEdition_add.html";
    }

    /**
     * 编辑页面
     *
     * @author shenyang.ou
     * @Date 2020-04-12
     */
    @RequestMapping("/edit")
    public String edit(Model model) {
        //获取当前用户应用列表
        List<AppInfoParam> appInfoParams = appInfoService.getAppInfoList(LoginContextHolder.getContext().getUserId());
        model.addAttribute("appInfoParams", appInfoParams);
        return PREFIX + "/appEdition_edit.html";
    }

    /**
     * 新增接口
     *
     * @author shenyang.ou
     * @Date 2020-04-12
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(AppEditionParam appEditionParam) {
        this.appEditionService.add(appEditionParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     *
     * @author shenyang.ou
     * @Date 2020-04-12
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(AppEditionParam appEditionParam) {
        this.appEditionService.update(appEditionParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author shenyang.ou
     * @Date 2020-04-12
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(AppEditionParam appEditionParam) {
        this.appEditionService.delete(appEditionParam);
        return ResponseData.success();
    }

    /**
     * 强制更新开关
     *
     * @author angedata
     * @Date 2019-07-25
     */
    @RequestMapping("/needUpdate")
    @ResponseBody
    public ResponseData needUpdate(@RequestParam Long editionId, @RequestParam Boolean needUpdate) {
        if (ToolUtil.isEmpty(editionId) && ToolUtil.isEmpty(needUpdate)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        AppEdition detail = this.appEditionService.getById(editionId);
        detail.setNeedUpdate(needUpdate);
        this.appEditionService.updateById(detail);
        return SUCCESS_TIP;
    }

    /**
     * 查看详情接口
     *
     * @author shenyang.ou
     * @Date 2020-04-12
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(AppEditionParam appEditionParam) {
        AppEdition detail = this.appEditionService.getById(appEditionParam.getEditionId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author shenyang.ou
     * @Date 2020-04-12
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(AppEditionParam appEditionParam) {
        //获取分页参数
        Page page = LayuiPageFactory.defaultPage();

        //根据条件查询操作日志
        List<Map<String, Object>> result = appEditionService.getAppEditions(page, appEditionParam.getAppId(), appEditionParam.getEditionName(),LoginContextHolder.getContext().getUserId());

        page.setRecords(result);

        return LayuiPageFactory.createPageInfo(page);
    }

}


