package cn.stylefeng.guns.modular.card.controller;

import cn.stylefeng.guns.base.auth.annotion.Permission;
import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.log.BussinessLog;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.app.model.params.AppInfoParam;
import cn.stylefeng.guns.modular.app.service.AppInfoService;
import cn.stylefeng.guns.modular.card.entity.CodeCardType;
import cn.stylefeng.guns.modular.card.model.params.CodeCardTypeParam;
import cn.stylefeng.guns.modular.card.service.CodeCardTypeService;
import cn.stylefeng.guns.sys.core.constant.Const;
import cn.stylefeng.guns.sys.core.constant.dictmap.UserDict;
import cn.stylefeng.guns.sys.core.constant.state.ManagerStatus;
import cn.stylefeng.guns.sys.core.exception.enums.BizExceptionEnum;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


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

    private final CodeCardTypeService codeCardTypeService;

    private final AppInfoService appInfoService;

    public CodeCardTypeController(CodeCardTypeService codeCardTypeService, AppInfoService appInfoService) {
        this.codeCardTypeService = codeCardTypeService;
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
     * 批量删除接口
     *
     * @author shenyang.ou
     * @Date 2020-04-12
     */
    @RequestMapping("/batchRemove")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public ResponseData batchRemove(String ids) {
        this.codeCardTypeService.batchRemove(ids);
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
     * 冻结
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/freeze")
    @ResponseBody
    public ResponseData freeze(@RequestParam Long cardTypeId) {
        CodeCardTypeParam param = new CodeCardTypeParam();
        param.setCardTypeId(cardTypeId);
        param.setStatus(false);
        this.codeCardTypeService.update(param);
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
    public ResponseData unfreeze(@RequestParam Long cardTypeId) {
        CodeCardTypeParam param = new CodeCardTypeParam();
        param.setCardTypeId(cardTypeId);
        param.setStatus(true);
        this.codeCardTypeService.update(param);
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
    public LayuiPageInfo list(CodeCardTypeParam codeCardTypeParam) {
//        return this.codeCardTypeService.findPageBySpec(codeCardTypeParam);
        //获取分页参数
        Page page = LayuiPageFactory.defaultPage();
        codeCardTypeParam.setCreateUser(LoginContextHolder.getContext().getUserId());
        //根据条件查询操作日志
        List<Map<String, Object>> result = codeCardTypeService.findListBySpec(page, codeCardTypeParam);

        page.setRecords(result);

        return LayuiPageFactory.createPageInfo(page);
    }

}


