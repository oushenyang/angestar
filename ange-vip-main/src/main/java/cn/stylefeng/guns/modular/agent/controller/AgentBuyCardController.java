package cn.stylefeng.guns.modular.agent.controller;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.agent.entity.AgentBuyCard;
import cn.stylefeng.guns.modular.agent.model.params.AgentAppParam;
import cn.stylefeng.guns.modular.agent.model.params.AgentBuyCardParam;
import cn.stylefeng.guns.modular.agent.model.result.AgentAppResult;
import cn.stylefeng.guns.modular.agent.service.AgentAppService;
import cn.stylefeng.guns.modular.agent.service.AgentBuyCardService;
import cn.stylefeng.guns.modular.card.entity.CardInfo;
import cn.stylefeng.guns.modular.card.service.CardInfoService;
import cn.stylefeng.guns.sys.modular.system.entity.User;
import cn.stylefeng.guns.sys.modular.system.service.UserService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


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

    @Autowired
    private CardInfoService cardInfoService;

    @Autowired
    private AgentAppService agentAppService;

    @Autowired
    private UserService userService;

    /**
     * 跳转到主页面
     *
     * @author shenyang.ou
     * @Date 2020-12-11
     */
    @RequestMapping("")
    public String index(Model model, Integer type) {
        model.addAttribute("type", type);
        //获取分页参数
        Page page = new Page(1, 100);
        AgentAppParam agentAppParam = new AgentAppParam();
        agentAppParam.setAgentUserId(LoginContextHolder.getContext().getUserId());
        //根据条件查询操作日志
        List<AgentAppResult> agentApps = agentAppService.findListBySpec(page, agentAppParam);
        model.addAttribute("agentApps", agentApps);
        List<User> userList = agentAppService.getAgentUserByUserId(LoginContextHolder.getContext().getUserId());
//        User user = userService.getById(LoginContextHolder.getContext().getUserId());
//        userList.add(user);
        model.addAttribute("userList", userList);
        return PREFIX + "/agentBuyCard.html";
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
     * 通过卡密批次号查询相关卡密
     *
     * @author shenyang.ou
     * @Date 2020-12-11
     */
    @RequestMapping("/getCardsByBatchNo")
    @ResponseBody
    public ResponseData getCardsByBatchNo(AgentBuyCardParam agentBuyCardParam) {
        List<CardInfo> cardInfos = this.cardInfoService.list(new QueryWrapper<CardInfo>().eq("batch_no",agentBuyCardParam.getBatchNo()));
        return ResponseData.success(cardInfos);
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
//        return this.agentBuyCardService.findPageBySpec(agentBuyCardParam);
        //获取分页参数
        Page page = LayuiPageFactory.defaultPage();
        agentBuyCardParam.setCreateUser(LoginContextHolder.getContext().getUserId());
        //根据条件查询操作日志
        List<Map<String, Object>> result = agentBuyCardService.findListBySpec(page, agentBuyCardParam);
        page.setRecords(result);
        return LayuiPageFactory.createPageInfo(page);
    }

}


