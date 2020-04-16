package cn.stylefeng.guns.modular.card.controller;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.app.model.params.AppInfoParam;
import cn.stylefeng.guns.modular.app.service.AppInfoService;
import cn.stylefeng.guns.modular.card.entity.CodeCardType;
import cn.stylefeng.guns.modular.card.model.params.CodeCardTypeParam;
import cn.stylefeng.guns.modular.card.service.CodeCardTypeService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * 单码卡类列表控制器
 *
 * @author shenyang.ou
 * @Date 2020-04-16 16:15:46
 */
@Controller
@RequestMapping("/codeCardType")
public class CodeCardTypeController extends BaseController {

    private String PREFIX = "/modular/card";

    @Autowired
    private CodeCardTypeService codeCardTypeService;

    @Autowired
    private AppInfoService appInfoService;

    /**
     * 跳转到主页面
     *
     * @author shenyang.ou
     * @Date 2020-04-16
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/codeCardType.html";
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
        return PREFIX + "/codeCardType_add.html";
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
        return PREFIX + "/codeCardType_edit.html";
    }

    /**
     * 新增接口
     *
     * @author shenyang.ou
     * @Date 2020-04-16
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(CodeCardTypeParam codeCardTypeParam) {
        this.codeCardTypeService.add(codeCardTypeParam);
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
    public ResponseData editItem(CodeCardTypeParam codeCardTypeParam) {
        this.codeCardTypeService.update(codeCardTypeParam);
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
    public ResponseData delete(CodeCardTypeParam codeCardTypeParam) {
        this.codeCardTypeService.delete(codeCardTypeParam);
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
    public ResponseData detail(CodeCardTypeParam codeCardTypeParam) {
        CodeCardType detail = this.codeCardTypeService.getById(codeCardTypeParam.getCardTypeId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author shenyang.ou
     * @Date 2020-04-16
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(CodeCardTypeParam codeCardTypeParam) {
        return this.codeCardTypeService.findPageBySpec(codeCardTypeParam);
    }

}


