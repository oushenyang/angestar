package cn.stylefeng.guns.modular.agent.controller;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.agent.entity.AgentApp;
import cn.stylefeng.guns.modular.agent.model.params.AgentAppParam;
import cn.stylefeng.guns.modular.agent.model.params.AgentAppRechargeParam;
import cn.stylefeng.guns.modular.agent.model.result.AgentAppResult;
import cn.stylefeng.guns.modular.agent.service.AgentAppService;
import cn.stylefeng.guns.modular.app.model.params.AppInfoParam;
import cn.stylefeng.guns.modular.app.service.AppInfoService;
import cn.stylefeng.guns.sys.modular.system.service.UserService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


/**
 * 代理端软件表控制器
 *
 * @author shenyang.ou
 * @Date 2020-05-20 11:20:02
 */
@Controller
@RequestMapping("/actApp")
public class ActAppController extends BaseController {

    private String PREFIX = "/modular/actApp";

    private final AgentAppService agentAppService;

    private final AppInfoService appInfoService;

    private final UserService userService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public ActAppController(AppInfoService appInfoService, AgentAppService agentAppService, UserService userService) {
        this.appInfoService = appInfoService;
        this.agentAppService = agentAppService;
        this.userService = userService;
    }

    /**
     * 跳转到主页面
     *
     * @author shenyang.ou
     * @Date 2020-05-20
     */
    @RequestMapping("")
    public String index(Model model) {
        //获取分页参数
        Page page = new Page(1, 100);
        AgentAppParam agentAppParam = new AgentAppParam();
        agentAppParam.setAgentUserId(LoginContextHolder.getContext().getUserId());
        //根据条件查询操作日志
        List<AgentAppResult> agentApps = agentAppService.findListBySpec(page, agentAppParam);
        model.addAttribute("agentApps", agentApps);
        return PREFIX + "/actApp.html";
    }

    /**
     * 新增页面
     *
     * @author shenyang.ou
     * @Date 2020-05-20
     */
    @RequestMapping("/add")
    public String add(Model model) {
        return PREFIX + "/actApp_add.html";
    }

    /**
     * 编辑页面
     *
     * @author shenyang.ou
     * @Date 2020-05-20
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/actApp_edit.html";
    }

    /**
     * 充值页面
     *
     * @author shenyang.ou
     * @Date 2020-05-20
     */
    @RequestMapping("/recharge")
    public String recharge() {
        return PREFIX + "/actApp_recharge.html";
    }

    /**
     * 权限设置页面
     *
     * @author shenyang.ou
     * @Date 2020-05-20
     */
    @RequestMapping("/power")
    public String power() {
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
     * 删除接口
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
        agentAppParam.setAgentUserId(LoginContextHolder.getContext().getUserId());
        //根据条件查询操作日志
        List<AgentAppResult> result = agentAppService.findListBySpec(page, agentAppParam);
        page.setRecords(result);
        return LayuiPageFactory.createPageInfo(page);
    }

}


