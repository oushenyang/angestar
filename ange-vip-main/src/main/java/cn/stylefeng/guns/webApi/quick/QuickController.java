package cn.stylefeng.guns.webApi.quick;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.exception.OperationException;
import cn.stylefeng.guns.core.constant.type.CardType;
import cn.stylefeng.guns.modular.agent.entity.AgentApp;
import cn.stylefeng.guns.modular.agent.model.params.AgentAppParam;
import cn.stylefeng.guns.modular.agent.model.result.AgentAppResult;
import cn.stylefeng.guns.modular.agent.model.result.AgentCardResult;
import cn.stylefeng.guns.modular.agent.service.AgentAppService;
import cn.stylefeng.guns.modular.agent.service.AgentCardService;
import cn.stylefeng.guns.modular.app.entity.AppInfo;
import cn.stylefeng.guns.modular.app.service.AppInfoService;
import cn.stylefeng.guns.modular.card.entity.CardInfo;
import cn.stylefeng.guns.modular.card.entity.CodeCardType;
import cn.stylefeng.guns.modular.card.model.params.CardInfoParam;
import cn.stylefeng.guns.modular.card.service.CardInfoService;
import cn.stylefeng.guns.modular.card.service.CodeCardTypeService;
import cn.stylefeng.guns.modular.device.entity.Device;
import cn.stylefeng.guns.modular.device.service.DeviceService;
import cn.stylefeng.guns.sys.core.auth.util.RedisUtil;
import cn.stylefeng.guns.sys.core.constant.state.RedisType;
import cn.stylefeng.guns.sys.core.util.ExportTextUtil;
import cn.stylefeng.guns.sys.core.util.SnowflakeUtil;
import cn.stylefeng.guns.sys.modular.system.entity.User;
import cn.stylefeng.guns.sys.modular.system.service.UserService;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

import static cn.stylefeng.guns.sys.core.exception.enums.BizExceptionEnum.CARD_BINDED;
import static cn.stylefeng.guns.sys.core.exception.enums.BizExceptionEnum.UN_FIND_CARD;

@Controller
@RequestMapping("/quick")
public class QuickController {

    private String PREFIX = "/modular/quick";

    private final AppInfoService appInfoService;

    private final CodeCardTypeService codeCardTypeService;
    private final AgentCardService agentCardService;

    private final AgentAppService agentAppService;

    private final CardInfoService cardInfoService;
    private final UserService userService;
    private final DeviceService deviceService;
    private final RedisUtil redisUtil;

    public QuickController(AppInfoService appInfoService, CodeCardTypeService codeCardTypeService, AgentCardService agentCardService, AgentAppService agentAppService, CardInfoService cardInfoService, UserService userService, DeviceService deviceService, RedisUtil redisUtil) {
        this.appInfoService = appInfoService;
        this.codeCardTypeService = codeCardTypeService;
        this.agentCardService = agentCardService;
        this.agentAppService = agentAppService;
        this.cardInfoService = cardInfoService;
        this.userService = userService;
        this.deviceService = deviceService;
        this.redisUtil = redisUtil;
    }

    /**
     * 卡密生成页面
     *
     * @author shenyang.ou
     * @Date 2020-04-01
     */
    @RequestMapping("/cardAdd/{appQuick}")
    public String cardAdd(@PathVariable String appQuick, Model model) {
        //获取当前用户应用
        AppInfo appInfoResult = appInfoService.getOne(new QueryWrapper<AppInfo>().eq("app_quick",appQuick));
        model.addAttribute("appInfoResult", appInfoResult);
        List<CodeCardType> codeCardTypes = codeCardTypeService.getCardTypeByUserId(appInfoResult.getCreateUser());
        model.addAttribute("codeCardTypes", codeCardTypes);
        return PREFIX + "/cardInfo_add.html";
    }

    /**
     * 卡密解绑页面
     *
     * @author shenyang.ou
     * @Date 2020-04-01
     */
    @RequestMapping("/cardUnBind/{appQuick}")
    public String cardUnBind(@PathVariable String appQuick, Model model) {
        //获取当前用户应用
        AppInfo appInfoResult = appInfoService.getOne(new QueryWrapper<AppInfo>().eq("app_quick",appQuick));
        model.addAttribute("appInfoResult", appInfoResult);
        return PREFIX + "/cardInfo_unBindNew.html";
    }

    /**
     * 卡密解绑接口
     *
     * @author shenyang.ou
     * @Date 2020-04-20
     */
    @RequestMapping("/cardUnBind/unBindItem")
    @ResponseBody
    public ResponseData unBindItem(String card,Long appId) {
        CardInfo cardInfo = cardInfoService.getOne(new QueryWrapper<CardInfo>().eq("app_id",appId).eq("card_code",card.trim()));
        if (ObjectUtil.isNull(cardInfo)){
            throw new OperationException(UN_FIND_CARD);
        }
        List<Device> devices = deviceService.list(new QueryWrapper<Device>().eq("card_or_user_id", cardInfo.getCardId()));
        if (CollectionUtil.isEmpty(devices)){
            throw new OperationException(CARD_BINDED);
        }
        //删除卡密缓存
        redisUtil.del(RedisType.CARD_INFO.getCode() + card.trim());
        deviceService.remove(new QueryWrapper<Device>().eq("card_or_user_id", cardInfo.getCardId()));
        return ResponseData.success();
    }

    /**
     * 开发者卡密新增接口
     *
     * @author shenyang.ou
     * @Date 2020-04-20
     */
    @RequestMapping("/cardAdd/addItem")
    @ResponseBody
    public ResponseData addItem(CardInfoParam cardInfoParam) {
        cardInfoParam.setUserId(cardInfoParam.getUserId());
        User user = userService.getById(cardInfoParam.getUserId());
        cardInfoParam.setCreateUser(cardInfoParam.getUserId());
        cardInfoParam.setUserName(user.getName());
        //生成批次号
        cardInfoParam.setBatchNo(SnowflakeUtil.getInstance().nextIdStr());
        List<String> cardInfos = this.cardInfoService.add(cardInfoParam);
        return ResponseData.success(cardInfos);
    }

    /**
     * 代理卡密新增接口
     *
     * @author shenyang.ou
     * @Date 2020-04-20
     */
    @RequestMapping("/agentAddItem")
    @ResponseBody
    public ResponseData agentAddItem(CardInfoParam cardInfoParam) {
        User user = userService.getById(cardInfoParam.getUserId());
        cardInfoParam.setUserName(user.getName());
        List<String> cardInfos = this.cardInfoService.actAddItem(cardInfoParam);
        return ResponseData.success(cardInfos);
    }

    /**
     * 新增结果导出txt
     *
     * @author shenyang.ou
     * @Date 2020-04-20
     */
    @RequestMapping("/cardAdd/addExport")
    public void addExport(HttpServletResponse response, String cards) {
        List<String> cardList = Arrays.asList(cards.split(","));
        ExportTextUtil.writeToTxt(response,cardList, String.valueOf(DateUtil.date()));
    }

    /**
     * 新增结果导出页面
     *
     * @author shenyang.ou
     * @Date 2020-04-20
     */
    @RequestMapping("/cardAdd/addResult")
    public String addResult(Model model,String cards) {
        List<String> cardList = Arrays.asList(cards.split(","));
        model.addAttribute("cards", cardList);
        return PREFIX + "/cardInfo_add_result.html";
    }

    /**
     * 代理卡密生成页面
     *
     * @author shenyang.ou
     * @Date 2020-04-01
     */
    @RequestMapping("/agentCardAdd/{agentAppQuick}")
    public String agentCardAdd(@PathVariable String agentAppQuick, Model model) {
        //获取当前用户应用
        AgentApp agentApp = agentAppService.getOne(new QueryWrapper<AgentApp>().eq("agent_app_quick",agentAppQuick));
        if (ObjectUtil.isNull(agentApp)){
            return "/index.html";
        }
        AppInfo appInfo = appInfoService.getById(agentApp.getAppId());
        model.addAttribute("agentApp", agentApp);
        model.addAttribute("appInfo", appInfo);
        List<AgentCardResult> agentCardResults =  agentCardService.findCardTypeByAppIdAndAgentAppId(agentApp.getAppId(),agentApp.getAgentAppId(), CardType.SINGLE_CARD.getCode());
        model.addAttribute("agentCardResults", agentCardResults);
        return PREFIX + "/actCard_add.html";
    }

}
