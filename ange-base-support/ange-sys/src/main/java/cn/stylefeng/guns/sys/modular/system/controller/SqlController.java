package cn.stylefeng.guns.sys.modular.system.controller;

import cn.stylefeng.guns.base.pojo.node.ZTreeNode;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.sys.modular.system.entity.Sql;
import cn.stylefeng.guns.sys.modular.system.entity.SqlType;
import cn.stylefeng.guns.sys.modular.system.model.params.SqlParam;
import cn.stylefeng.guns.sys.modular.system.model.result.SqlResult;
import cn.stylefeng.guns.sys.modular.system.service.SqlService;
import cn.stylefeng.guns.sys.modular.system.service.SqlTypeService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.exception.RequestEmptyException;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import cn.stylefeng.roses.kernel.model.response.SuccessResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * sql管理控制器
 *
 * @author stylefeng
 * @Date 2019-03-13 13:53:53
 */
@Controller
@RequestMapping("/sql")
public class SqlController extends BaseController {

    private String PREFIX = "/modular/system/sql";

    @Autowired
    private SqlService sqlService;

    @Autowired
    private SqlTypeService sqlTypeService;

    /**
     * 跳转到主页面
     *
     * @author stylefeng
     * @Date 2019-03-13
     */
    @RequestMapping("")
    public String index(@RequestParam("sqlTypeId") Long sqlTypeId, Model model) {
        model.addAttribute("sqlTypeId", sqlTypeId);

        //获取type的名称
        SqlType sqlType = sqlTypeService.getById(sqlTypeId);
        if (sqlType == null) {
            throw new RequestEmptyException();
        }
        model.addAttribute("sqlTypeName", sqlType.getName());

        return PREFIX + "/sql.html";
    }

    /**
     * 新增页面
     *
     * @author stylefeng
     * @Date 2019-03-13
     */
    @RequestMapping("/add")
    public String add(@RequestParam("sqlTypeId") Long sqlTypeId, Model model) {
        model.addAttribute("sqlTypeId", sqlTypeId);

        //获取type的名称
        SqlType sqlType = sqlTypeService.getById(sqlTypeId);
        if (sqlType == null) {
            throw new RequestEmptyException();
        }

        model.addAttribute("sqlTypeName", sqlType.getName());
        return PREFIX + "/sql_add.html";
    }

    /**
     * 编辑页面
     *
     * @author stylefeng
     * @Date 2019-03-13
     */
    @RequestMapping("/edit")
    public String edit(@RequestParam("sqlId") Long sqlId, Model model) {

        //获取type的id
        Sql sql = sqlService.getById(sqlId);
        if (sql == null) {
            throw new RequestEmptyException();
        }

        //获取type的名称
        SqlType sqlType = sqlTypeService.getById(sql.getSqlTypeId());
        if (sqlType == null) {
            throw new RequestEmptyException();
        }

        model.addAttribute("sqlTypeId", sql.getSqlTypeId());
        model.addAttribute("sqlTypeName", sqlType.getName());

        return PREFIX + "/sql_edit.html";
    }

    /**
     * 新增接口
     *
     * @author stylefeng
     * @Date 2019-03-13
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(SqlParam sqlParam) {
        this.sqlService.add(sqlParam);
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
    public ResponseData editItem(SqlParam sqlParam) {
        this.sqlService.update(sqlParam);
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
    public ResponseData delete(SqlParam sqlParam) {
        this.sqlService.delete(sqlParam);
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
    public ResponseData detail(SqlParam sqlParam) {
        SqlResult sqlResult = this.sqlService.sqlDetail(sqlParam.getSqlId());
        return ResponseData.success(sqlResult);
    }

    /**
     * 查询列表
     *
     * @author stylefeng
     * @Date 2019-03-13
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(SqlParam sqlParam) {
        return this.sqlService.findPageBySpec(sqlParam);
    }

    /**
     * 获取某个字典类型下的所有字典
     *
     * @author stylefeng
     * @Date 2019-03-13
     */
    @ResponseBody
    @RequestMapping("/listSqls")
    public ResponseData listSqls(@RequestParam("sqlTypeId") Long sqlTypeId) {
        List<Sql> sqls = this.sqlService.listSqls(sqlTypeId);
        return new SuccessResponseData(sqls);
    }

    /**
     * 获取某个字典类型下的所有字典
     *
     * @author stylefeng
     * @Date 2019-03-13
     */
    @ResponseBody
    @RequestMapping("/listSqlsByCode")
    public ResponseData listSqlsByCode(@RequestParam("sqlTypeCode") String sqlTypeCode) {
        List<Sql> sqls = this.sqlService.listSqlsByCode(sqlTypeCode);
        return new SuccessResponseData(sqls);
    }

    /**
     * 获取某个类型下字典树的列表，ztree格式
     *
     * @author fengshuonan
     * @Date 2018/12/23 4:56 PM
     */
    @RequestMapping(value = "/ztree")
    @ResponseBody
    public List<ZTreeNode> ztree(@RequestParam("sqlTypeId") Long sqlTypeId, @RequestParam(value = "sqlId", required = false) Long sqlId) {
        return this.sqlService.sqlTreeList(sqlTypeId, sqlId);
    }

}


