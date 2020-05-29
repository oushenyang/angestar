package cn.stylefeng.guns.modular.account.controller;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.account.entity.SerialInfo;
import cn.stylefeng.guns.modular.account.model.params.SerialInfoParam;
import cn.stylefeng.guns.modular.account.service.SerialInfoService;
import cn.stylefeng.guns.modular.app.model.params.AppInfoParam;
import cn.stylefeng.guns.modular.app.service.AppInfoService;
import cn.stylefeng.guns.modular.card.entity.CodeCardType;
import cn.stylefeng.guns.modular.card.service.CodeCardTypeService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


/**
 * 注册码表控制器
 *
 * @author shenyang.ou
 * @Date 2020-05-29 12:37:34
 */
@Controller
@RequestMapping("/serialInfo")
public class SerialInfoController extends BaseController {

    private String PREFIX = "/modular/account";

    private final SerialInfoService serialInfoService;
    private final AppInfoService appInfoService;
    private final CodeCardTypeService codeCardTypeService;

    public SerialInfoController(SerialInfoService serialInfoService, AppInfoService appInfoService, CodeCardTypeService codeCardTypeService) {
        this.serialInfoService = serialInfoService;
        this.appInfoService = appInfoService;
        this.codeCardTypeService = codeCardTypeService;
    }

    /**
     * 跳转到主页面
     *
     * @author shenyang.ou
     * @Date 2020-05-29
     */
    @RequestMapping("")
    public String index(Model model,Integer type) {
        //获取当前用户应用列表
        List<AppInfoParam> appInfoParams = appInfoService.getAppInfoList(LoginContextHolder.getContext().getUserId());
        model.addAttribute("appInfoParams", appInfoParams);
        model.addAttribute("appId", 0);
        List<CodeCardType> codeCardTypes = codeCardTypeService.getCardTypeByAppId(-1L,LoginContextHolder.getContext().getUserId());
        codeCardTypes.forEach(codeCardType->{
            if (codeCardType.getAppId() == 0){
                codeCardType.setCardTypeName(codeCardType.getCardTypeName()+" ---默认卡类");
            }else {
                codeCardType.setCardTypeName(codeCardType.getCardTypeName()+" ---"+codeCardType.getAppName());
            }
        });
        model.addAttribute("type", type);
        model.addAttribute("codeCardTypes", codeCardTypes);
        List<CodeCardType> codeCardTypeList = codeCardTypeService.getCardTypeByAppId(0L,LoginContextHolder.getContext().getUserId());
        model.addAttribute("codeCardTypeList", codeCardTypeList);
        return PREFIX + "/serialInfo.html";
    }

    /**
     * 新增页面
     *
     * @author shenyang.ou
     * @Date 2020-05-29
     */
    @RequestMapping("/add")
    public String add(Model model, Integer type) {
        //获取当前用户应用列表
        List<AppInfoParam> appInfoParams = appInfoService.getAppInfoList(LoginContextHolder.getContext().getUserId());
        model.addAttribute("appInfoParams", appInfoParams);
        model.addAttribute("appId", 0);
        model.addAttribute("type", type);
        List<CodeCardType> codeCardTypes = codeCardTypeService.getCardTypeByAppId(0L,LoginContextHolder.getContext().getUserId());
        model.addAttribute("codeCardTypes", codeCardTypes);
        return PREFIX + "/serialInfo_add.html";
    }

    /**
     * 编辑页面
     *
     * @author shenyang.ou
     * @Date 2020-05-29
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/serialInfo_edit.html";
    }

    /**
     * 新增接口
     *
     * @author shenyang.ou
     * @Date 2020-05-29
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(SerialInfoParam serialInfoParam) {
        List<String> cardInfos = this.serialInfoService.add(serialInfoParam);
        return ResponseData.success(cardInfos);
    }

    /**
     * 编辑接口
     *
     * @author shenyang.ou
     * @Date 2020-05-29
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(SerialInfoParam serialInfoParam) {
        this.serialInfoService.update(serialInfoParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author shenyang.ou
     * @Date 2020-05-29
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(SerialInfoParam serialInfoParam) {
        this.serialInfoService.delete(serialInfoParam);
        return ResponseData.success();
    }

    /**
     * 批量删除接口
     *
     * @author shenyang.ou
     * @Date 2020-05-29
     */
    @RequestMapping("/batchRemove")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public ResponseData batchRemove(String ids) {
        this.serialInfoService.batchRemove(ids);
        return ResponseData.success();
    }

    /**
     * 查看详情接口0
     *
     * @author shenyang.ou
     * @Date 2020-05-29
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(SerialInfoParam serialInfoParam) {
        SerialInfo detail = this.serialInfoService.getById(serialInfoParam.getSerialId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author shenyang.ou
     * @Date 2020-05-29
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(SerialInfoParam serialInfoParam) {
        //获取分页参数
        Page page = LayuiPageFactory.defaultPage();
        serialInfoParam.setCreateUser(LoginContextHolder.getContext().getUserId());
        //根据条件查询操作日志
        List<Map<String, Object>> result = serialInfoService.findListBySpec(page, serialInfoParam);
        page.setRecords(result);
        return LayuiPageFactory.createPageInfo(page);
    }

}


