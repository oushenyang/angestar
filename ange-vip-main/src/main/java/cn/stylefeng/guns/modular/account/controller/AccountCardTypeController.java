package cn.stylefeng.guns.modular.account.controller;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.account.entity.AccountCardType;
import cn.stylefeng.guns.modular.account.model.params.AccountCardTypeParam;
import cn.stylefeng.guns.modular.account.service.AccountCardTypeService;
import cn.stylefeng.guns.modular.app.model.params.AppInfoParam;
import cn.stylefeng.guns.modular.app.service.AppInfoService;
import cn.stylefeng.guns.modular.card.entity.CodeCardType;
import cn.stylefeng.guns.modular.card.model.params.CodeCardTypeParam;
import cn.stylefeng.guns.modular.card.service.CodeCardTypeService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


/**
 * 账号卡类列表控制器
 *
 * @author shenyang.ou
 * @Date 2020-04-16 16:15:46
 */
@Controller
@RequestMapping("/accountCardType")
public class AccountCardTypeController extends BaseController {

    private String PREFIX = "/modular/account";

    private final AccountCardTypeService accountCardTypeService;

    private final AppInfoService appInfoService;

    public AccountCardTypeController(AccountCardTypeService accountCardTypeService, AppInfoService appInfoService) {
        this.accountCardTypeService = accountCardTypeService;
        this.appInfoService = appInfoService;
    }

    /**
     * 跳转到主页面
     *
     * @author shenyang.ou
     * @Date 2020-04-16
     */
    @RequestMapping("")
    public String index(Model model) {
        //获取当前用户应用列表
        List<AppInfoParam> appInfoParams = appInfoService.getAppInfoList(LoginContextHolder.getContext().getUserId());
        model.addAttribute("appInfoParams", appInfoParams);
        model.addAttribute("appId", 0);
        return PREFIX + "/accountCardType.html";
    }

    /**
     * 新增页面
     *
     * @author shenyang.ou
     * @Date 2020-04-16
     */
    @RequestMapping("/add")
    public String add(Model model) {
        //获取当前用户应用列表
        List<AppInfoParam> appInfoParams = appInfoService.getAppInfoList(LoginContextHolder.getContext().getUserId());
        model.addAttribute("appInfoParams", appInfoParams);
        return PREFIX + "/accountCardType_add.html";
    }

    /**
     * 编辑页面
     *
     * @author shenyang.ou
     * @Date 2020-04-16
     */
    @RequestMapping("/edit")
    public String edit(Model model) {
        //获取当前用户应用列表
        List<AppInfoParam> appInfoParams = appInfoService.getAppInfoList(LoginContextHolder.getContext().getUserId());
        model.addAttribute("appInfoParams", appInfoParams);
        return PREFIX + "/accountCardType_edit.html";
    }

    /**
     * 新增接口
     *
     * @author shenyang.ou
     * @Date 2020-04-16
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(AccountCardTypeParam accountCardTypeParam) {
        this.accountCardTypeService.add(accountCardTypeParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     *
     * @author shenyang.ou
     * @Date 2020-04-16
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(AccountCardTypeParam accountCardTypeParam) {
        this.accountCardTypeService.update(accountCardTypeParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author shenyang.ou
     * @Date 2020-04-16
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(AccountCardTypeParam accountCardTypeParam) {
        this.accountCardTypeService.delete(accountCardTypeParam);
        return ResponseData.success();
    }

    /**
     * 批量删除接口
     *
     * @author shenyang.ou
     * @Date 2020-04-12
     */
    @RequestMapping("/batchRemove")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public ResponseData batchRemove(String ids) {
        this.accountCardTypeService.batchRemove(ids);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author shenyang.ou
     * @Date 2020-04-16
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(AccountCardTypeParam accountCardTypeParam) {
        AccountCardType detail = this.accountCardTypeService.getById(accountCardTypeParam.getAccountCardTypeId());
        return ResponseData.success(detail);
    }

    /**
     * 冻结
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/freeze")
    @ResponseBody
    public ResponseData freeze(@RequestParam Long accountCardTypeId) {
        AccountCardTypeParam param = new AccountCardTypeParam();
        param.setAccountCardTypeId(accountCardTypeId);
        param.setStatus(false);
        this.accountCardTypeService.update(param);
        return ResponseData.success();
    }

    /**
     * 解除冻结
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/unfreeze")
    @ResponseBody
    public ResponseData unfreeze(@RequestParam Long accountCardTypeId) {
        AccountCardTypeParam param = new AccountCardTypeParam();
        param.setAccountCardTypeId(accountCardTypeId);
        param.setStatus(true);
        this.accountCardTypeService.update(param);
        return ResponseData.success();
    }

    /**
     * 查询列表
     *
     * @author shenyang.ou
     * @Date 2020-04-16
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(AccountCardTypeParam accountCardTypeParam) {
//        return this.codeCardTypeService.findPageBySpec(codeCardTypeParam);
        //获取分页参数
        Page page = LayuiPageFactory.defaultPage();
        accountCardTypeParam.setCreateUser(LoginContextHolder.getContext().getUserId());
        //根据条件查询操作日志
        List<Map<String, Object>> result = accountCardTypeService.findListBySpec(page, accountCardTypeParam);

        page.setRecords(result);

        return LayuiPageFactory.createPageInfo(page);
    }

}


