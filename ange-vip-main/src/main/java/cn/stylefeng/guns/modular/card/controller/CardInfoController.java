package cn.stylefeng.guns.modular.card.controller;

import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.app.entity.AppInfo;
import cn.stylefeng.guns.modular.app.model.params.AppInfoParam;
import cn.stylefeng.guns.modular.app.service.AppInfoService;
import cn.stylefeng.guns.modular.card.entity.CardInfo;
import cn.stylefeng.guns.modular.card.entity.CodeCardType;
import cn.stylefeng.guns.modular.card.model.params.BatchCardInfoParam;
import cn.stylefeng.guns.modular.card.model.params.CardInfoParam;
import cn.stylefeng.guns.modular.card.service.CardInfoService;
import cn.stylefeng.guns.modular.card.service.CodeCardTypeService;
import cn.stylefeng.guns.modular.device.entity.Device;
import cn.stylefeng.guns.modular.device.model.params.DeviceParam;
import cn.stylefeng.guns.modular.device.model.result.TokenResult;
import cn.stylefeng.guns.modular.device.service.DeviceService;
import cn.stylefeng.guns.modular.device.service.TokenService;
import cn.stylefeng.guns.sys.core.util.ExportTextUtil;
import cn.stylefeng.guns.sys.core.util.SnowflakeUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 卡密表控制器
 *
 * @author shenyang.ou
 * @Date 2020-04-20 11:52:48
 */
@Controller
@RequestMapping("/cardInfo")
public class CardInfoController extends BaseController {

    private String PREFIX = "/modular/card";

    private final CardInfoService cardInfoService;

    private final TokenService tokenService;

    private final AppInfoService appInfoService;

    private final CodeCardTypeService codeCardTypeService;

    private final DeviceService deviceService;

    public CardInfoController(CardInfoService cardInfoService, TokenService tokenService, AppInfoService appInfoService, CodeCardTypeService codeCardTypeService, DeviceService deviceService) {
        this.cardInfoService = cardInfoService;
        this.tokenService = tokenService;
        this.appInfoService = appInfoService;
        this.codeCardTypeService = codeCardTypeService;
        this.deviceService = deviceService;
    }

    /**
     * 跳转到主页面
     *
     * @author shenyang.ou
     * @Date 2020-04-20
     */
    @RequestMapping("")
    public String index(Model model,Integer type) {
        //获取当前用户应用列表
        List<AppInfoParam> appInfoParams = appInfoService.getAppInfoList(LoginContextHolder.getContext().getUserId());
        model.addAttribute("appInfoParams", appInfoParams);
        model.addAttribute("appId", 0);
        List<CodeCardType> codeCardTypes = codeCardTypeService.getCardTypeByUserId(LoginContextHolder.getContext().getUserId());
        model.addAttribute("type", type);
        model.addAttribute("codeCardTypes", codeCardTypes);
        model.addAttribute("codeCardTypeList", codeCardTypes);
        return PREFIX + "/cardInfo.html";
    }

    /**
     * 新增页面
     *
     * @author shenyang.ou
     * @Date 2020-04-20
     */
    @RequestMapping("/add")
    public String add(Model model,Integer type) {
        //获取当前用户应用列表
        List<AppInfoParam> appInfoParams = appInfoService.getAppInfoList(LoginContextHolder.getContext().getUserId());
        model.addAttribute("appInfoParams", appInfoParams);
        model.addAttribute("appId", 0);
        model.addAttribute("type", type);
        List<CodeCardType> codeCardTypes = codeCardTypeService.getCardTypeByUserId(LoginContextHolder.getContext().getUserId());
        model.addAttribute("codeCardTypes", codeCardTypes);
        return PREFIX + "/cardInfo_add.html";
    }

    /**
     * 新增结果导出页面
     *
     * @author shenyang.ou
     * @Date 2020-04-20
     */
    @RequestMapping("/addResult")
    public String addResult(Model model,String cards) {
        List<String> cardList = Arrays.asList(cards.split(","));
        model.addAttribute("cards", cardList);
        return PREFIX + "/cardInfo_add_result.html";
    }

    /**
     * 详情页面
     *
     * @author shenyang.ou
     * @Date 2020-04-20
     */
    @RequestMapping("/detail")
    public String detail(Model model,Long cardId) {
        CardInfo cardInfo = cardInfoService.getById(cardId);
        TokenResult token = tokenService.getLastTokenByCardOrUserId(cardId);
        DeviceParam deviceParam = new DeviceParam();
        deviceParam.setCardType(1);
        deviceParam.setCardOrUserId(cardId);
        List<Device> deviceList = deviceService.findListBySpec(deviceParam);
        model.addAttribute("cardInfo", cardInfo);
        model.addAttribute("token", token);
        model.addAttribute("deviceList", deviceList);
        return PREFIX + "/cardInfo_detail.html";
    }

    /**
     * 卡密配置页面
     *
     * @author shenyang.ou
     * @Date 2020-04-20
     */
    @RequestMapping("/configEdit")
    public String configEdit() {
        return PREFIX + "/cardInfo_config_edit.html";
    }

    /**
     * 自定义导入页面
     *
     * @author shenyang.ou
     * @Date 2020-04-20
     */
    @RequestMapping("/customImport")
    public String customImport(Model model,Integer type) {
        //获取当前用户应用列表
        List<AppInfoParam> appInfoParams = appInfoService.getAppInfoList(LoginContextHolder.getContext().getUserId());
        model.addAttribute("appInfoParams", appInfoParams);
        model.addAttribute("appId", 0);
        model.addAttribute("type", type);
        List<CodeCardType> codeCardTypes = codeCardTypeService.getCardTypeByUserId(LoginContextHolder.getContext().getUserId());
        model.addAttribute("codeCardTypes", codeCardTypes);
        return PREFIX + "/cardInfo_import.html";
    }

    /**
     * 易游导入页面
     *
     * @author shenyang.ou
     * @Date 2020-04-20
     */
    @RequestMapping("/yyImport")
    public String yyImport(Model model,Integer type) {
        //获取当前用户应用列表
        List<AppInfoParam> appInfoParams = appInfoService.getAppInfoList(LoginContextHolder.getContext().getUserId());
        model.addAttribute("appInfoParams", appInfoParams);
        model.addAttribute("appId", 0);
        model.addAttribute("type", type);
        List<CodeCardType> codeCardTypes = codeCardTypeService.getCardTypeByUserId(LoginContextHolder.getContext().getUserId());
        model.addAttribute("codeCardTypes", codeCardTypes);
        return PREFIX + "/cardInfo_yyImport.html";
    }

    /**
     * 易游导入
     *
     * @author shenyang.ou
     * @Date 2020-04-20
     */
    @RequestMapping("/yyImportItem")
    @ResponseBody
    public ResponseData yyImportItem(Long appId,String yyCardAddress,String txtFileName) {
        cardInfoService.yyImportItem(appId,yyCardAddress,txtFileName);
        return ResponseData.success();
    }

    /**
     * 新增结果价格维护页面
     *
     * @author shenyang.ou
     * @Date 2020-04-20
     */
    @RequestMapping("/addPriceEdit")
    public String addPriceEdit(Model model,String cardTypeId) {
        model.addAttribute("cardTypeId", cardTypeId);
        return PREFIX + "/cardInfo_add_priceEdit.html";
    }

    /**
     * 新增结果导出txt
     *
     * @author shenyang.ou
     * @Date 2020-04-20
     */
    @RequestMapping("/addExport")
    public void addExport(HttpServletResponse response,String cards) {
        List<String> cardList = Arrays.asList(cards.split(","));
        ExportTextUtil.writeToTxt(response,cardList, String.valueOf(DateUtil.date()));
    }

    /**
     * 卡密导出txt
     *
     * @author shenyang.ou
     * @Date 2020-04-20
     */
    @RequestMapping("/exportCard")
    public void exportCard(HttpServletRequest request, HttpServletResponse response, String data) {
        BatchCardInfoParam param = JSONObject.parseObject(data,BatchCardInfoParam.class);
        this.cardInfoService.exportCard(request,response,param);
    }

    /**
     * 编辑页面
     *
     * @author shenyang.ou
     * @Date 2020-04-20
     */
    @RequestMapping("/edit")
    public String edit(Model model,Integer type) {
        //获取当前用户应用列表
        List<AppInfoParam> appInfoParams = appInfoService.getAppInfoList(LoginContextHolder.getContext().getUserId());
        model.addAttribute("appInfoParams", appInfoParams);
        model.addAttribute("appId", 0);
        List<CodeCardType> codeCardTypes = codeCardTypeService.getCardTypeByUserId(LoginContextHolder.getContext().getUserId());
        model.addAttribute("codeCardTypes", codeCardTypes);
        model.addAttribute("type", type);
        return PREFIX + "/cardInfo_edit.html";
    }

    /**
     * 编辑页面
     *
     * @author shenyang.ou
     * @Date 2020-04-20
     */
    @RequestMapping("/itemEdit")
    public String itemEdit(Model model,Integer type) {
        //获取当前用户应用列表
        List<AppInfoParam> appInfoParams = appInfoService.getAppInfoList(LoginContextHolder.getContext().getUserId());
        model.addAttribute("appInfoParams", appInfoParams);
        model.addAttribute("appId", 0);
        List<CodeCardType> codeCardTypes = codeCardTypeService.getCardTypeByUserId(LoginContextHolder.getContext().getUserId());
        model.addAttribute("codeCardTypes", codeCardTypes);
        model.addAttribute("type", type);
        return PREFIX + "/cardInfo_item_edit.html";
    }

    /**
     * 开发者新增接口
     *
     * @author shenyang.ou
     * @Date 2020-04-20
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(CardInfoParam cardInfoParam) {
        cardInfoParam.setUserId(LoginContextHolder.getContext().getUserId());
        cardInfoParam.setCreateUser(LoginContextHolder.getContext().getUserId());
        cardInfoParam.setUserName(LoginContextHolder.getContext().getUserName());
        //生成批次号
        cardInfoParam.setBatchNo(SnowflakeUtil.getInstance().nextIdStr());
        List<String> cardInfos = this.cardInfoService.add(cardInfoParam);
        return ResponseData.success(cardInfos);
    }

    /**
     * 代理新增卡密接口
     *
     * @author shenyang.ou
     * @Date 2020-04-20
     */
    @RequestMapping("/actAddItem")
    @ResponseBody
    public ResponseData actAddItem(CardInfoParam cardInfoParam) {
        List<String> cardInfos = this.cardInfoService.actAddItem(cardInfoParam);
        return ResponseData.success(cardInfos);
    }

    /**
     * 编辑接口
     *
     * @author shenyang.ou
     * @Date 2020-04-20
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(BatchCardInfoParam cardInfoParam) {
        this.cardInfoService.BatchEdit(cardInfoParam);
        return ResponseData.success();
    }

    /**
     * 卡密配置编辑接口
     *
     * @author shenyang.ou
     * @Date 2020-04-20
     */
    @RequestMapping("/editConfigItem")
    @ResponseBody
    public ResponseData editConfigItem(CardInfoParam cardInfoParam) {
        this.cardInfoService.editConfigItem(cardInfoParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author shenyang.ou
     * @Date 2020-04-20
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(CardInfoParam cardInfoParam) {
        //通用应用
        if (cardInfoParam.getAppId()!=0){
            //获取应用信息
            AppInfo appInfo = appInfoService.getById(cardInfoParam.getAppId());
            appInfo.setCardNum(appInfo.getCardNum()-1);
            appInfoService.updateById(appInfo);
        }
        this.cardInfoService.delete(cardInfoParam);
        return ResponseData.success();
    }

    /**
     * 批量删除接口
     *
     * @author shenyang.ou
     * @Date 2020-04-20
     */
    @RequestMapping("/batchRemove")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public ResponseData batchRemove(String ids) {
        this.cardInfoService.batchRemove(ids);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author shenyang.ou
     * @Date 2020-04-20
     */
    @RequestMapping("/detailItem")
    @ResponseBody
    public ResponseData detailItem(CardInfoParam cardInfoParam) {
        CardInfo detail = this.cardInfoService.getById(cardInfoParam.getCardId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author shenyang.ou
     * @Date 2020-04-20
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(CardInfoParam cardInfoParam) {
        //获取分页参数
        Page page = LayuiPageFactory.defaultPage();
        cardInfoParam.setCreateUser(LoginContextHolder.getContext().getUserId());
        //根据条件查询操作日志
        List<Map<String, Object>> result = cardInfoService.findListBySpec(page, cardInfoParam);
        page.setRecords(result);
        return LayuiPageFactory.createPageInfo(page);
    }

    /**
     * 根据应用id获取卡类信息
     *
     * @author shenyang.ou
     * @Date 2020-04-20
     */
    @RequestMapping("/getCardTypeByAppId")
    @ResponseBody
    public ResponseData getCardTypeByAppId(Long appId) {
        List<CodeCardType> codeCardTypes = codeCardTypeService.getCardTypeByUserId(LoginContextHolder.getContext().getUserId());
        return ResponseData.success(codeCardTypes);
    }

}


