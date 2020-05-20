package cn.stylefeng.guns.modular.agent.controller;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.agent.entity.AgentPower;
import cn.stylefeng.guns.modular.agent.model.params.AgentPowerParam;
import cn.stylefeng.guns.modular.agent.service.AgentPowerService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 代理权限控制器
 *
 * @author shenyang.ou
 * @Date 2020-05-20 16:10:00
 */
@Controller
@RequestMapping("/agentPower")
public class AgentPowerController extends BaseController {

    private String PREFIX = "/modular/agentPower";

    @Autowired
    private AgentPowerService agentPowerService;

    /**
     * 跳转到主页面
     *
     * @author shenyang.ou
     * @Date 2020-05-20
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/agentPower.html";
    }

    /**
     * 新增页面
     *
     * @author shenyang.ou
     * @Date 2020-05-20
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/agentPower_add.html";
    }

    /**
     * 编辑页面
     *
     * @author shenyang.ou
     * @Date 2020-05-20
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/agentPower_edit.html";
    }

    /**
     * 新增接口
     *
     * @author shenyang.ou
     * @Date 2020-05-20
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(AgentPowerParam agentPowerParam) {
        this.agentPowerService.add(agentPowerParam);
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
    public ResponseData editItem(AgentPowerParam agentPowerParam) {
        this.agentPowerService.update(agentPowerParam);
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
    public ResponseData delete(AgentPowerParam agentPowerParam) {
        this.agentPowerService.delete(agentPowerParam);
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
        this.agentPowerService.batchRemove(ids);
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
    public ResponseData detail(AgentPowerParam agentPowerParam) {
        AgentPower detail = this.agentPowerService.getById(agentPowerParam.getAgentPowerId());
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
    public LayuiPageInfo list(AgentPowerParam agentPowerParam) {
        return this.agentPowerService.findPageBySpec(agentPowerParam);
    }

}


