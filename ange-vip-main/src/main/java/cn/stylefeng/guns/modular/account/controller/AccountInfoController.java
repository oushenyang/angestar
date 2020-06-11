package cn.stylefeng.guns.modular.account.controller;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.account.entity.AccountInfo;
import cn.stylefeng.guns.modular.account.model.params.AccountInfoParam;
import cn.stylefeng.guns.modular.account.service.AccountInfoService;
import cn.stylefeng.guns.modular.app.model.params.AppInfoParam;
import cn.stylefeng.guns.modular.app.service.AppInfoService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 用户账号表控制器
 *
 * @author shenyang.ou
 * @Date 2020-05-14 16:45:20
 */
@Controller
@RequestMapping("/accountInfo")
public class AccountInfoController extends BaseController {

    private String PREFIX = "/modular/account";

    private final AccountInfoService accountInfoService;
    private final AppInfoService appInfoService;

    public AccountInfoController(AccountInfoService accountInfoService, AppInfoService appInfoService) {
        this.accountInfoService = accountInfoService;
        this.appInfoService = appInfoService;
    }

    /**
     * 跳转到主页面
     *
     * @author shenyang.ou
     * @Date 2020-05-14
     */
    @RequestMapping("")
    public String index(Model model) {
        //获取当前用户应用列表
        List<AppInfoParam> appInfoParams = appInfoService.getAppInfoList(LoginContextHolder.getContext().getUserId());
        model.addAttribute("appInfoParams", appInfoParams);
        return PREFIX + "/accountInfo.html";
    }

    /**
     * 新增页面
     *
     * @author shenyang.ou
     * @Date 2020-05-14
     */
    @RequestMapping("/add")
    public String add(Model model) {
        //获取当前用户应用列表
        List<AppInfoParam> appInfoParams = appInfoService.getAppInfoList(LoginContextHolder.getContext().getUserId());
        model.addAttribute("appInfoParams", appInfoParams);
        return PREFIX + "/accountInfo_add.html";
    }

    /**
     * 编辑页面
     *
     * @author shenyang.ou
     * @Date 2020-05-14
     */
    @RequestMapping("/edit")
    public String edit(Model model) {
        //获取当前用户应用列表
        List<AppInfoParam> appInfoParams = appInfoService.getAppInfoList(LoginContextHolder.getContext().getUserId());
        model.addAttribute("appInfoParams", appInfoParams);
        return PREFIX + "/accountInfo_edit.html";
    }

    /**
     * 新增接口
     *
     * @author shenyang.ou
     * @Date 2020-05-14
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(AccountInfoParam accountInfoParam) {
        accountInfoParam.setCreateUser(LoginContextHolder.getContext().getUserId());
        accountInfoParam.setCreateTime(new Date());
        accountInfoParam.setRegistrationTime(new Date());
        this.accountInfoService.add(accountInfoParam);
        return ResponseData.success();
    }

    /**
     * 新增判断账号是否存在接口
     *
     * @author shenyang.ou
     * @Date 2020-04-12
     */
    @RequestMapping("/addAccountWhetherAlready")
    @ResponseBody
    public ResponseData addAccountWhetherAlready(Long appId,String account) {
        boolean isAlready = this.accountInfoService.addAccountWhetherAlready(appId,account);
        return ResponseData.success(isAlready);
    }

    /**
     * 编辑接口
     *
     * @author shenyang.ou
     * @Date 2020-05-14
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(AccountInfoParam accountInfoParam) {
        this.accountInfoService.update(accountInfoParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author shenyang.ou
     * @Date 2020-05-14
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(AccountInfoParam accountInfoParam) {
        this.accountInfoService.delete(accountInfoParam);
        return ResponseData.success();
    }

    /**
     * 批量删除接口
     *
     * @author shenyang.ou
     * @Date 2020-05-14
     */
    @RequestMapping("/batchRemove")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public ResponseData batchRemove(String ids) {
        this.accountInfoService.batchRemove(ids);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author shenyang.ou
     * @Date 2020-05-14
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(AccountInfoParam accountInfoParam) {
        AccountInfo detail = this.accountInfoService.getById(accountInfoParam.getAccountId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author shenyang.ou
     * @Date 2020-05-14
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(AccountInfoParam accountInfoParam) {
        //获取分页参数
        Page page = LayuiPageFactory.defaultPage();
        accountInfoParam.setCreateUser(LoginContextHolder.getContext().getUserId());
        //根据条件查询操作日志
        List<Map<String, Object>> result = accountInfoService.findListBySpec(page, accountInfoParam);
        page.setRecords(result);
        return LayuiPageFactory.createPageInfo(page);
    }

}


