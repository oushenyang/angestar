package cn.stylefeng.guns.modular.card.controller;

import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.app.model.params.AppInfoParam;
import cn.stylefeng.guns.modular.app.service.AppInfoService;
import cn.stylefeng.guns.modular.card.entity.CardInfo;
import cn.stylefeng.guns.modular.card.entity.CodeCardType;
import cn.stylefeng.guns.modular.card.model.params.CardInfoParam;
import cn.stylefeng.guns.modular.card.service.CardInfoService;
import cn.stylefeng.guns.modular.card.service.CodeCardTypeService;
import cn.stylefeng.guns.sys.core.util.ExportTextUtil;
import cn.stylefeng.guns.sys.modular.system.entity.Sql;
import cn.stylefeng.guns.sys.modular.system.service.SqlService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.util.HttpContext;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
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

    @Autowired
    private CardInfoService cardInfoService;

    @Autowired
    private AppInfoService appInfoService;

    @Autowired
    private CodeCardTypeService codeCardTypeService;

    @Autowired
    private SqlService SqlService;

    /**
     * 跳转到主页面
     *
     * @author shenyang.ou
     * @Date 2020-04-20
     */
    @RequestMapping("")
    public String index(Model model) {
        //获取当前用户应用列表
        List<AppInfoParam> appInfoParams = appInfoService.getAppInfoList(LoginContextHolder.getContext().getUserId());
        model.addAttribute("appInfoParams", appInfoParams);
        model.addAttribute("appId", 0);
        List<CodeCardType> codeCardTypes = codeCardTypeService.getCardTypeByAppId(-1L,LoginContextHolder.getContext().getUserId());
        codeCardTypes.forEach(codeCardType->{
            if (codeCardType.getAppId() == 0){
                codeCardType.setCardTypeName(codeCardType.getCardTypeName()+" ---通用卡类");
            }else {
                codeCardType.setCardTypeName(codeCardType.getCardTypeName()+" ---"+codeCardType.getAppName());
            }
        });
        model.addAttribute("codeCardTypes", codeCardTypes);
        return PREFIX + "/cardInfo.html";
    }

    /**
     * 新增页面
     *
     * @author shenyang.ou
     * @Date 2020-04-20
     */
    @RequestMapping("/add")
    public String add(Model model) {
        //获取当前用户应用列表
        List<AppInfoParam> appInfoParams = appInfoService.getAppInfoList(LoginContextHolder.getContext().getUserId());
        model.addAttribute("appInfoParams", appInfoParams);
        model.addAttribute("appId", 0);
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
     * 编辑页面
     *
     * @author shenyang.ou
     * @Date 2020-04-20
     */
    @RequestMapping("/edit")
    public String edit(Model model) {
        //获取当前用户应用列表
        List<AppInfoParam> appInfoParams = appInfoService.getAppInfoList(LoginContextHolder.getContext().getUserId());
        model.addAttribute("appInfoParams", appInfoParams);
        model.addAttribute("appId", 0);
        return PREFIX + "/cardInfo_edit.html";
    }

    /**
     * 新增接口
     *
     * @author shenyang.ou
     * @Date 2020-04-20
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(CardInfoParam cardInfoParam) {
        List<String> cardInfos = this.cardInfoService.add(cardInfoParam);
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
    public ResponseData editItem(CardInfoParam cardInfoParam) {
        this.cardInfoService.update(cardInfoParam);
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
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(CardInfoParam cardInfoParam) {
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
        List<CodeCardType> codeCardTypes = codeCardTypeService.getCardTypeByAppId(appId,LoginContextHolder.getContext().getUserId());
        return ResponseData.success(codeCardTypes);
    }

    /**
     * 根据应用id创建卡类信息
     *
     * @author shenyang.ou
     * @Date 2020-04-20
     */
    @RequestMapping("/addCardTypeByAppId")
    @ResponseBody
    public ResponseData addCardTypeByAppId(Long appId) {
        List<Sql> sqls = SqlService.listSqlsByCode("CARD_TYPE");
        List<CodeCardType> codeCardTypes = codeCardTypeService.addCardTypeBySql(sqls,appId);
        return ResponseData.success(codeCardTypes);
    }

}


