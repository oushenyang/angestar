package cn.stylefeng.guns.modular.agent.controller;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.agent.entity.AgentApp;
import cn.stylefeng.guns.modular.agent.model.params.AgentAppParam;
import cn.stylefeng.guns.modular.agent.model.params.AgentAppRechargeParam;
import cn.stylefeng.guns.modular.agent.model.result.AgentPowerResult;
import cn.stylefeng.guns.modular.agent.service.AgentAppService;
import cn.stylefeng.guns.modular.agent.service.AgentPowerService;
import cn.stylefeng.guns.modular.app.model.params.AppInfoParam;
import cn.stylefeng.guns.modular.app.service.AppInfoService;
import cn.stylefeng.guns.sys.modular.system.service.UserService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 开发者端代理软件表控制器
 *
 * @author shenyang.ou
 * @Date 2020-05-20 11:20:02
 */
@Controller
@RequestMapping("/agentApp")
public class AgentAppController extends BaseController {

    private String PREFIX = "/modular/agent";

    private final AgentAppService agentAppService;

    private final AppInfoService appInfoService;

    private final UserService userService;

    private final AgentPowerService agentPowerService;

    public AgentAppController(AppInfoService appInfoService, AgentAppService agentAppService, UserService userService, AgentPowerService agentPowerService) {
        this.appInfoService = appInfoService;
        this.agentAppService = agentAppService;
        this.userService = userService;
        this.agentPowerService = agentPowerService;
    }

    /**
     * 跳转到主页面
     *
     * @author shenyang.ou
     * @Date 2020-05-20
     */
    @RequestMapping("")
    public String index(Model model,Integer type) {
        //获取当前用户应用列表
        List<AppInfoParam> appInfoParams = appInfoService.getAppInfoList(LoginContextHolder.getContext().getUserId());
        model.addAttribute("appInfoParams", appInfoParams);
        model.addAttribute("type", type);
        return PREFIX + "/agentApp.html";
    }

    /**
     * 新增页面
     *
     * @author shenyang.ou
     * @Date 2020-05-20
     */
    @RequestMapping("/add")
    public String add(Model model,Integer type) {
        List<AppInfoParam> appInfoParams = new ArrayList<>();
        //新增下级代理
        if (type == 2){
            //查找当前代理用户所代理的软件
            appInfoParams = appInfoService.getAgentAppInfoList(LoginContextHolder.getContext().getUserId());
        }else {
            //获取当前用户应用列表
            appInfoParams = appInfoService.getAppInfoList(LoginContextHolder.getContext().getUserId());
        }
        model.addAttribute("appInfoParams", appInfoParams);
        return PREFIX + "/agentApp_add.html";
    }

    /**
     * 编辑页面
     *
     * @author shenyang.ou
     * @Date 2020-05-20
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/agentApp_edit.html";
    }

    /**
     * 充值页面
     *
     * @author shenyang.ou
     * @Date 2020-05-20
     */
    @RequestMapping("/recharge")
    public String recharge() {
        return PREFIX + "/agentApp_recharge.html";
    }

    /**
     * 权限设置页面
     *
     * @author shenyang.ou
     * @Date 2020-05-20
     */
    @RequestMapping("/power")
    public String power(Model model,Long agentAppId,Integer type) {
        //代理页面
        if (type == 2){
            AgentPowerResult agentPowerResult= agentPowerService.getSubordinateAgentPowerShow(agentAppId);
            model.addAttribute("agentPower",agentPowerResult);
        }
        model.addAttribute("type",type);
        return PREFIX + "/agentApp_power.html";
    }

    /**
     * 卡密设置页面
     *
     * @author shenyang.ou
     * @Date 2020-05-20
     */
    @RequestMapping("/card")
    public String card() {
        return PREFIX + "/agentApp_card.html";
    }

    /**
     * 新增接口
     *
     * @author shenyang.ou
     * @Date 2020-05-20
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(AgentAppParam agentAppParam) {
        this.agentAppService.add(agentAppParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     *
     * @author shenyang.ou
     * @Date 2020-05-20
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(AgentAppParam agentAppParam) {
        this.agentAppService.update(agentAppParam);
        return ResponseData.success();
    }

    /**
     * 充值接口
     *
     * @author shenyang.ou
     * @Date 2020-05-20
     */
    @RequestMapping("/rechargeItem")
    @ResponseBody
    public ResponseData rechargeItem(AgentAppRechargeParam agentAppRechargeParam) {
        this.agentAppService.recharge(agentAppRechargeParam);
        return ResponseData.success();
    }

    /**
     * 删除代理接口
     *
     * @author shenyang.ou
     * @Date 2020-05-20
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(AgentAppParam agentAppParam) {
        this.agentAppService.delete(agentAppParam);
        return ResponseData.success();
    }

    /**
     * 设置总代
     *
     * @author shenyang.ou
     * @Date 2020-05-20
     */
    @RequestMapping("/setRose")
    @ResponseBody
    public ResponseData setRose(AgentAppParam agentAppParam) {
        agentAppParam.setRose(true);
        this.agentAppService.update(agentAppParam);
        return ResponseData.success();
    }

    /**
     * 取消总代
     *
     * @author shenyang.ou
     * @Date 2020-05-20
     */
    @RequestMapping("/cancelRose")
    @ResponseBody
    public ResponseData cancelRose(AgentAppParam agentAppParam) {
        agentAppParam.setRose(false);
        this.agentAppService.update(agentAppParam);
        return ResponseData.success();
    }

    /**
     * 冻结代理
     *
     * @author shenyang.ou
     * @Date 2020-05-20
     */
    @RequestMapping("/disable")
    @ResponseBody
    public ResponseData disable(AgentAppParam agentAppParam) {
        agentAppParam.setStatus(1);
        this.agentAppService.update(agentAppParam);
        return ResponseData.success();
    }

    /**
     * 解冻代理
     *
     * @author shenyang.ou
     * @Date 2020-05-20
     */
    @RequestMapping("/cancelDisable")
    @ResponseBody
    public ResponseData cancelDisable(AgentAppParam agentAppParam) {
        agentAppParam.setStatus(0);
        this.agentAppService.update(agentAppParam);
        return ResponseData.success();
    }

    /**
     * 批量删除接口
     *
     * @author shenyang.ou
     * @Date 2020-05-20
     */
    @RequestMapping("/batchRemove")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public ResponseData batchRemove(String ids) {
        this.agentAppService.batchRemove(ids);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author shenyang.ou
     * @Date 2020-05-20
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(AgentAppParam agentAppParam) {
        AgentApp detail = this.agentAppService.getById(agentAppParam.getAgentAppId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author shenyang.ou
     * @Date 2020-05-20
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(AgentAppParam agentAppParam) {
//        return this.agentAppService.findPageBySpec(agentAppParam);
        //获取分页参数
        Page page = LayuiPageFactory.defaultPage();
        agentAppParam.setPid(LoginContextHolder.getContext().getUserId());
        //根据条件查询操作日志
        List<Map<String, Object>> result = agentAppService.findListBySpec(page, agentAppParam);
        page.setRecords(result);
        return LayuiPageFactory.createPageInfo(page);
    }

}


