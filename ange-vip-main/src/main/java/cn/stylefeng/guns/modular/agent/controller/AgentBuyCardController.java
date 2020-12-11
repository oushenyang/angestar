package cn.stylefeng.guns.modular.agent.controller;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.agent.entity.AgentBuyCard;
import cn.stylefeng.guns.modular.agent.model.params.AgentBuyCardParam;
import cn.stylefeng.guns.modular.agent.service.AgentBuyCardService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 代理购卡记录控制器
 *
 * @author shenyang.ou
 * @Date 2020-12-11 12:34:59
 */
@Controller
@RequestMapping("/agentBuyCard")
public class AgentBuyCardController extends BaseController {

    private String PREFIX = "/modular/agentBuyCard";

    @Autowired
    private AgentBuyCardService agentBuyCardService;

    /**
     * 跳转到主页面
     *
     * @author shenyang.ou
     * @Date 2020-12-11
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/agentBuyCard.html";
    }

    /**
     * 新增页面
     *
     * @author shenyang.ou
     * @Date 2020-12-11
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/agentBuyCard_add.html";
    }

    /**
     * 编辑页面
     *
     * @author shenyang.ou
     * @Date 2020-12-11
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/agentBuyCard_edit.html";
    }

    /**
     * 新增接口
     *
     * @author shenyang.ou
     * @Date 2020-12-11
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(AgentBuyCardParam agentBuyCardParam) {
        this.agentBuyCardService.add(agentBuyCardParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     *
     * @author shenyang.ou
     * @Date 2020-12-11
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(AgentBuyCardParam agentBuyCardParam) {
        this.agentBuyCardService.update(agentBuyCardParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author shenyang.ou
     * @Date 2020-12-11
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(AgentBuyCardParam agentBuyCardParam) {
        this.agentBuyCardService.delete(agentBuyCardParam);
        return ResponseData.success();
    }


    /**
     * 查看详情接口
     *
     * @author shenyang.ou
     * @Date 2020-12-11
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(AgentBuyCardParam agentBuyCardParam) {
        AgentBuyCard detail = this.agentBuyCardService.getById(agentBuyCardParam.getAgentBuyCardId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author shenyang.ou
     * @Date 2020-12-11
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(AgentBuyCardParam agentBuyCardParam) {
        return this.agentBuyCardService.findPageBySpec(agentBuyCardParam);
    }

}


