package cn.stylefeng.guns.modular.device.controller;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.device.entity.Token;
import cn.stylefeng.guns.modular.device.model.params.TokenParam;
import cn.stylefeng.guns.modular.device.service.TokenService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 登录信息控制器
 *
 * @author shenyang.ou
 * @Date 2020-08-02 16:20:32
 */
@Controller
@RequestMapping("/token")
public class TokenController extends BaseController {

    private String PREFIX = "/modular/token";

    @Autowired
    private TokenService tokenService;

    /**
     * 跳转到主页面
     *
     * @author shenyang.ou
     * @Date 2020-08-02
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/token.html";
    }

    /**
     * 新增页面
     *
     * @author shenyang.ou
     * @Date 2020-08-02
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/token_add.html";
    }

    /**
     * 编辑页面
     *
     * @author shenyang.ou
     * @Date 2020-08-02
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/token_edit.html";
    }

    /**
     * 新增接口
     *
     * @author shenyang.ou
     * @Date 2020-08-02
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(TokenParam tokenParam) {
        this.tokenService.add(tokenParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     *
     * @author shenyang.ou
     * @Date 2020-08-02
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(TokenParam tokenParam) {
        this.tokenService.update(tokenParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author shenyang.ou
     * @Date 2020-08-02
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(TokenParam tokenParam) {
        this.tokenService.delete(tokenParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author shenyang.ou
     * @Date 2020-08-02
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(TokenParam tokenParam) {
        Token detail = this.tokenService.getById(tokenParam.getTokenId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author shenyang.ou
     * @Date 2020-08-02
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(TokenParam tokenParam) {
        return this.tokenService.findPageBySpec(tokenParam);
    }

}


