package cn.stylefeng.guns.modular.agent.controller;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.agent.entity.AgentCard;
import cn.stylefeng.guns.modular.agent.model.params.AgentAppParam;
import cn.stylefeng.guns.modular.agent.model.params.AgentCardParam;
import cn.stylefeng.guns.modular.agent.service.AgentAppService;
import cn.stylefeng.guns.modular.agent.service.AgentCardService;
import cn.stylefeng.guns.modular.card.model.params.CardInfoParam;
import cn.stylefeng.guns.modular.card.service.CardInfoService;
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
 * 代理端卡密控制器
 *
 * @author shenyang.ou
 * @Date 2020-05-22 10:53:06
 */
@Controller
@RequestMapping("/actCard")
public class ActCardController extends BaseController {

    private String PREFIX = "/modular/actCard";

    private final AgentCardService agentCardService;
    private final AgentAppService agentAppService;
    private final CardInfoService cardInfoService;

    public ActCardController(AgentAppService agentAppService, AgentCardService agentCardService,CardInfoService cardInfoService) {
        this.agentAppService = agentAppService;
        this.agentCardService = agentCardService;
        this.cardInfoService = cardInfoService;
    }

    /**
     * 跳转到主页面
     *
     * @author shenyang.ou
     * @Date 2020-05-22
     */
    @RequestMapping("")
    public String index(Model model) {
        //获取分页参数
        Page page = new Page(1, 100);
        AgentAppParam agentAppParam = new AgentAppParam();
        agentAppParam.setAgentUserId(LoginContextHolder.getContext().getUserId());
        //根据条件查询操作日志
        List<Map<String, Object>> agentApps = agentAppService.findListBySpec(page, agentAppParam);
        model.addAttribute("agentApps", agentApps);
        model.addAttribute("agentAppsSize", agentApps.size());
        return PREFIX + "/actCard.html";
    }

    /**
     * 新增页面
     *
     * @author shenyang.ou
     * @Date 2020-05-22
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/actCard_add.html";
    }

    /**
     * 编辑页面
     *
     * @author shenyang.ou
     * @Date 2020-05-22
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/actCard_edit.html";
    }

    /**
     * 新增接口
     *
     * @author shenyang.ou
     * @Date 2020-05-22
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(AgentCardParam agentCardParam) {
        this.agentCardService.add(agentCardParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     *
     * @author shenyang.ou
     * @Date 2020-05-22
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(AgentCardParam agentCardParam) {
        this.agentCardService.update(agentCardParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author shenyang.ou
     * @Date 2020-05-22
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(AgentCardParam agentCardParam) {
        this.agentCardService.delete(agentCardParam);
        return ResponseData.success();
    }

    /**
     * 批量删除接口
     *
     * @author shenyang.ou
     * @Date 2020-05-22
     */
    @RequestMapping("/batchRemove")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public ResponseData batchRemove(String ids) {
        this.agentCardService.batchRemove(ids);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author shenyang.ou
     * @Date 2020-05-22
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(AgentCardParam agentCardParam) {
        AgentCard detail = this.agentCardService.getById(agentCardParam.getAgentCardId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author shenyang.ou
     * @Date 2020-05-22
     */
    @ResponseBody
    @RequestMapping("/appList")
    public List<Map<String, Object>> appList(AgentAppParam param) {
        //获取分页参数
        Page page = new Page(1, 100);
        param.setAgentUserId(LoginContextHolder.getContext().getUserId());
        //根据条件查询操作日志
        List<Map<String, Object>> result = agentAppService.findListBySpec(page, param);
        return result;
    }

    /**
     * 查询列表
     *
     * @author shenyang.ou
     * @Date 2020-05-22
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(CardInfoParam cardInfoParam) {
        //获取分页参数
        Page page = LayuiPageFactory.defaultPage();
        cardInfoParam.setUserId(LoginContextHolder.getContext().getUserId());
        //根据条件查询操作日志
        List<Map<String, Object>> result = cardInfoService.findListBySpec(page, cardInfoParam);
        page.setRecords(result);
        return LayuiPageFactory.createPageInfo(page);
    }

}


