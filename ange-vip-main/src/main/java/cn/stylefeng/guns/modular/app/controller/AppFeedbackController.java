package cn.stylefeng.guns.modular.app.controller;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.app.model.params.AppInfoParam;
import cn.stylefeng.guns.modular.app.model.result.AppFeedbackResult;
import cn.stylefeng.guns.modular.app.service.AppInfoService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.stylefeng.guns.modular.app.entity.AppFeedback;
import cn.stylefeng.guns.modular.app.model.params.AppFeedbackParam;
import cn.stylefeng.guns.modular.app.service.AppFeedbackService;
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
 * 应用反馈列表控制器
 *
 * @author shenyang.ou
 * @Date 2021-08-06 14:39:47
 */
@Controller
@RequestMapping("/appFeedback")
public class AppFeedbackController extends BaseController {

    private String PREFIX = "/modular/appFeedback";

    @Autowired
    private AppFeedbackService appFeedbackService;

    @Autowired
    private AppInfoService appInfoService;

    /**
     * 跳转到主页面
     *
     * @author shenyang.ou
     * @Date 2021-08-06
     */
    @RequestMapping("")
    public String index(Model model) {
        //获取当前用户应用列表
        List<AppInfoParam> appInfoParams = appInfoService.getAppInfoList(LoginContextHolder.getContext().getUserId());
        model.addAttribute("appInfoParams", appInfoParams);
        return PREFIX + "/appFeedback.html";
    }

    /**
     * 删除接口
     *
     * @author shenyang.ou
     * @Date 2021-08-06
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(AppFeedbackParam appFeedbackParam) {
        this.appFeedbackService.delete(appFeedbackParam);
        return ResponseData.success();
    }

    /**
     * 批量删除接口
     *
     * @author shenyang.ou
     * @Date 2021-08-06
     */
    @RequestMapping("/batchRemove")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public ResponseData batchRemove(String ids) {
        this.appFeedbackService.batchRemove(ids);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author shenyang.ou
     * @Date 2021-08-06
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(AppFeedbackParam appFeedbackParam) {
        AppFeedback detail = this.appFeedbackService.getById(appFeedbackParam.getFeedbackId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author shenyang.ou
     * @Date 2021-08-06
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(AppFeedbackParam param) {
        Page page = LayuiPageFactory.defaultPage();
        param.setCreateUser(LoginContextHolder.getContext().getUserId());
        List<AppFeedbackResult> result = appFeedbackService.findListPage(page, param);
        page.setRecords(result);
        return LayuiPageFactory.createPageInfo(page);
    }

}


