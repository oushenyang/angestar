package cn.stylefeng.guns.webApi.quick;

import cn.stylefeng.guns.modular.app.entity.AppInfo;
import cn.stylefeng.guns.modular.app.service.AppInfoService;
import cn.stylefeng.guns.modular.card.entity.CodeCardType;
import cn.stylefeng.guns.modular.card.model.params.CardInfoParam;
import cn.stylefeng.guns.modular.card.service.CardInfoService;
import cn.stylefeng.guns.modular.card.service.CodeCardTypeService;
import cn.stylefeng.guns.sys.core.util.SnowflakeUtil;
import cn.stylefeng.guns.sys.modular.system.entity.User;
import cn.stylefeng.guns.sys.modular.system.service.UserService;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/quick")
public class QuickController {

    private String PREFIX = "/modular/quick";

    private final AppInfoService appInfoService;

    private final CodeCardTypeService codeCardTypeService;

    private final CardInfoService cardInfoService;
    private final UserService userService;

    public QuickController(AppInfoService appInfoService, CodeCardTypeService codeCardTypeService, CardInfoService cardInfoService, UserService userService) {
        this.appInfoService = appInfoService;
        this.codeCardTypeService = codeCardTypeService;
        this.cardInfoService = cardInfoService;
        this.userService = userService;
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

}
