package cn.stylefeng.guns.webApi.huanying.controller;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.sys.modular.system.entity.Dict;
import cn.stylefeng.guns.sys.modular.system.service.DictService;
import cn.stylefeng.guns.webApi.huanying.entity.HyApp;
import cn.stylefeng.guns.webApi.huanying.model.params.HyAppParam;
import cn.stylefeng.guns.webApi.huanying.service.HyAppService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 幻影破解控制器
 *
 * @author shenyang.ou
 * @Date 2020-10-28 12:58:35
 */
@Controller
@RequestMapping("/hyApp")
public class HyAppController extends BaseController {

    private String PREFIX = "/modular/hyApp";

    @Autowired
    private HyAppService hyAppService;

    @Autowired
    private DictService dictService;

    /**
     * 跳转到主页面
     *
     * @author shenyang.ou
     * @Date 2020-10-28
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/hyApp.html";
    }

    /**
     * 新增页面
     *
     * @author shenyang.ou
     * @Date 2020-10-28
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/hyApp_add.html";
    }

    /**
     * 编辑页面
     *
     * @author shenyang.ou
     * @Date 2020-10-28
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/hyApp_edit.html";
    }

    /**
     * 新增接口
     *
     * @author shenyang.ou
     * @Date 2020-10-28
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(HyAppParam hyAppParam) {
        this.hyAppService.add(hyAppParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     *
     * @author shenyang.ou
     * @Date 2020-10-28
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(HyAppParam hyAppParam) {
        this.hyAppService.update(hyAppParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author shenyang.ou
     * @Date 2020-10-28
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(HyAppParam hyAppParam) {
        this.hyAppService.delete(hyAppParam);
        return ResponseData.success();
    }

    /**
     * 批量删除接口
     *
     * @author shenyang.ou
     * @Date 2020-10-28
     */
    @RequestMapping("/batchRemove")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public ResponseData batchRemove(String ids) {
        this.hyAppService.batchRemove(ids);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author shenyang.ou
     * @Date 2020-10-28
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(HyAppParam hyAppParam) {
        HyApp detail = this.hyAppService.getById(hyAppParam.getAppinfoid());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author shenyang.ou
     * @Date 2020-10-28
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(HyAppParam hyAppParam) {
        //获取分页参数
        Page page = LayuiPageFactory.defaultPage();
        //根据条件查询操作日志
        List<Dict> dicts = dictService.listDictsByCode("HUANYINGAPP");
        List<String> signList = new ArrayList<>();
        for (Dict dict : dicts){
            signList.add(dict.getCode());
        }
        List<Map<String, Object>> result = hyAppService.findListByPage(page, hyAppParam,signList);
        page.setRecords(result);
        return LayuiPageFactory.createPageInfo(page);
    }

}


