package cn.stylefeng.guns.sys.modular.system.controller;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.sys.modular.system.entity.SqlType;
import cn.stylefeng.guns.sys.modular.system.model.params.SqlTypeParam;
import cn.stylefeng.guns.sys.modular.system.service.SqlTypeService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import cn.stylefeng.roses.kernel.model.response.SuccessResponseData;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * sql类型表控制器
 *
 * @author stylefeng
 * @Date 2019-03-13 13:53:54
 */
@Controller
@RequestMapping("/sqlType")
public class SqlTypeController extends BaseController {

    private String PREFIX = "/modular/system/sqlType";

    @Autowired
    private SqlTypeService sqlTypeService;

    /**
     * 跳转到主页面
     *
     * @author stylefeng
     * @Date 2019-03-13
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/sqlType.html";
    }

    /**
     * 新增页面
     *
     * @author stylefeng
     * @Date 2019-03-13
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/sqlType_add.html";
    }

    /**
     * 编辑页面
     *
     * @author stylefeng
     * @Date 2019-03-13
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/sqlType_edit.html";
    }

    /**
     * 新增接口
     *
     * @author stylefeng
     * @Date 2019-03-13
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(SqlTypeParam sqlTypeParam) {
        this.sqlTypeService.add(sqlTypeParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     *
     * @author stylefeng
     * @Date 2019-03-13
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(SqlTypeParam sqlTypeParam) {
        this.sqlTypeService.update(sqlTypeParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author stylefeng
     * @Date 2019-03-13
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(SqlTypeParam sqlTypeParam) {
        this.sqlTypeService.delete(sqlTypeParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author stylefeng
     * @Date 2019-03-13
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(SqlTypeParam sqlTypeParam) {
        SqlType detail = this.sqlTypeService.getById(sqlTypeParam.getSqlTypeId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author stylefeng
     * @Date 2019-03-13
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(SqlTypeParam sqlTypeParam) {
        return this.sqlTypeService.findPageBySpec(sqlTypeParam);
    }

    /**
     * 查询所有sql
     *
     * @author stylefeng
     * @Date 2019-03-13
     */
    @ResponseBody
    @RequestMapping("/listTypes")
    public ResponseData listTypes() {

        QueryWrapper<SqlType> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.select("sql_type_id", "code", "name");

        List<SqlType> list = this.sqlTypeService.list(objectQueryWrapper);
        return new SuccessResponseData(list);
    }

}


