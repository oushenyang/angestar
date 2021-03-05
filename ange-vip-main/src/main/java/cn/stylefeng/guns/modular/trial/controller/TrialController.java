package cn.stylefeng.guns.modular.trial.controller;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.trial.entity.Trial;
import cn.stylefeng.guns.modular.trial.model.params.TrialParam;
import cn.stylefeng.guns.modular.trial.model.result.TrialResult;
import cn.stylefeng.guns.modular.trial.service.TrialService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


/**
 * 试用信息控制器
 *
 * @author shenyang.ou
 * @Date 2021-03-04 12:54:56
 */
@Controller
@RequestMapping("/trial")
public class TrialController extends BaseController {

    private String PREFIX = "/modular/trial";

    @Autowired
    private TrialService trialService;

    /**
     * 跳转到主页面
     *
     * @author shenyang.ou
     * @Date 2021-03-04
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/trial.html";
    }

    /**
     * 新增页面
     *
     * @author shenyang.ou
     * @Date 2021-03-04
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/trial_add.html";
    }

    /**
     * 编辑页面
     *
     * @author shenyang.ou
     * @Date 2021-03-04
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/trial_edit.html";
    }

    /**
     * 新增接口
     *
     * @author shenyang.ou
     * @Date 2021-03-04
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(TrialParam trialParam) {
        this.trialService.add(trialParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     *
     * @author shenyang.ou
     * @Date 2021-03-04
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(TrialParam trialParam) {
        this.trialService.update(trialParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author shenyang.ou
     * @Date 2021-03-04
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(TrialParam trialParam) {
        this.trialService.delete(trialParam);
        return ResponseData.success();
    }

    /**
     * 批量删除接口
     *
     * @author shenyang.ou
     * @Date 2021-03-04
     */
    @RequestMapping("/batchRemove")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public ResponseData batchRemove(String ids) {
        this.trialService.batchRemove(ids);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author shenyang.ou
     * @Date 2021-03-04
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(TrialParam trialParam) {
        Trial detail = this.trialService.getById(trialParam.getTrialId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author shenyang.ou
     * @Date 2021-03-04
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(TrialParam trialParam) {
//        return this.trialService.findPageBySpec(trialParam);
        //获取分页参数
        Page page = LayuiPageFactory.defaultPage();
        trialParam.setCreateUser(LoginContextHolder.getContext().getUserId());
        //根据条件查询操作日志
        List<TrialResult> result = trialService.findListBySpec(page, trialParam);
        page.setRecords(result);
        return LayuiPageFactory.createPageInfo(page);
    }

}


