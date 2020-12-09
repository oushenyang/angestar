package cn.stylefeng.guns.modular.agent.controller;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.agent.entity.AgentExamine;
import cn.stylefeng.guns.modular.agent.model.params.AgentExamineParam;
import cn.stylefeng.guns.modular.agent.service.AgentExamineService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 代理审核表控制器
 *
 * @author shenyang.ou
 * @Date 2020-12-09 16:46:23
 */
@Controller
@RequestMapping("/actExamine")
public class AgentExamineController extends BaseController {

    private String PREFIX = "/modular/actExamine";

    @Autowired
    private AgentExamineService agentExamineService;

    /**
     * 跳转到主页面
     *
     * @author shenyang.ou
     * @Date 2020-12-09
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/actExamine.html";
    }

    /**
     * 新增接口
     *
     * @author shenyang.ou
     * @Date 2020-12-09
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(AgentExamineParam agentExamineParam) {
        this.agentExamineService.add(agentExamineParam);
        return ResponseData.success();
    }

    /**
     * 查询列表
     *
     * @author shenyang.ou
     * @Date 2020-12-09
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(AgentExamineParam agentExamineParam) {
        return this.agentExamineService.findPageBySpec(agentExamineParam);
    }

}


