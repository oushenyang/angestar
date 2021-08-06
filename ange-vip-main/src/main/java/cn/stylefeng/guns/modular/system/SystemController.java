/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.stylefeng.guns.modular.system;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.model.LoginUser;
import cn.stylefeng.guns.base.oshi.SystemHardwareInfo;
import cn.stylefeng.guns.modular.account.mapper.AccountInfoMapper;
import cn.stylefeng.guns.modular.account.model.result.AccountMonth;
import cn.stylefeng.guns.modular.account.service.AccountInfoService;
import cn.stylefeng.guns.modular.agent.service.AgentAppService;
import cn.stylefeng.guns.modular.app.service.AppInfoService;
import cn.stylefeng.guns.modular.card.model.result.CardMonth;
import cn.stylefeng.guns.modular.card.model.result.IncomeStatistics;
import cn.stylefeng.guns.modular.card.service.CardInfoService;
import cn.stylefeng.guns.modular.device.service.TokenService;
import cn.stylefeng.guns.sys.core.constant.factory.ConstantFactory;
import cn.stylefeng.guns.sys.core.log.LogObjectHolder;
import cn.stylefeng.guns.sys.core.util.DefaultImages;
import cn.stylefeng.guns.sys.core.util.RelativeDateHandler;
import cn.stylefeng.guns.sys.core.util.SpringUtil;
import cn.stylefeng.guns.sys.modular.system.entity.Notice;
import cn.stylefeng.guns.sys.modular.system.entity.User;
import cn.stylefeng.guns.sys.modular.system.entity.UserOperationLog;
import cn.stylefeng.guns.sys.modular.system.model.UploadResult;
import cn.stylefeng.guns.sys.modular.system.model.result.UserOperationLogResult;
import cn.stylefeng.guns.sys.modular.system.service.FileInfoService;
import cn.stylefeng.guns.sys.modular.system.service.NoticeService;
import cn.stylefeng.guns.sys.modular.system.service.UserOperationLogService;
import cn.stylefeng.guns.sys.modular.system.service.UserService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.RequestEmptyException;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import cn.stylefeng.roses.kernel.model.exception.enums.CoreExceptionEnum;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import cn.stylefeng.roses.kernel.model.response.SuccessResponseData;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.*;

/**
 * 通用控制器
 *
 * @author fengshuonan
 * @Date 2017年2月17日20:27:22
 */
@Controller
@RequestMapping("/system")
@Slf4j
public class SystemController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileInfoService fileInfoService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private AppInfoService appInfoService;

    @Autowired
    private CardInfoService cardInfoService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AccountInfoService accountInfoService;

    @Autowired
    private AgentAppService agentAppService;

    @Autowired
    private UserOperationLogService userOperationLogService;

    /**
     * 控制台页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/console")
    public String console(Model model) {
        Long userId = LoginContextHolder.getContext().getUserId();
        Integer appNum = appInfoService.appNum(userId);
        Integer allCardNum = cardInfoService.allCardNum(userId);
        Integer onlineNum = tokenService.onlineNum(userId);
        Integer expireCardNum = cardInfoService.expireCardNum(userId);
        Integer accountNum = accountInfoService.accountNum(userId);
        Integer agentNum = agentAppService.agentNum(userId);
        List<UserOperationLogResult> logList = userOperationLogService.getLogListByUserId(userId);
        logList.forEach(userOperationLogResult -> {
            userOperationLogResult.setCreateTimeName(RelativeDateHandler.format(userOperationLogResult.getCreateTime()));
        });
        User user = this.userService.getById(userId);
        model.addAttribute("appNum", appNum);
        model.addAttribute("allCardNum", allCardNum);
        model.addAttribute("onlineNum", onlineNum);
        model.addAttribute("expireCardNum", expireCardNum);
        model.addAttribute("accountNum", accountNum);
        model.addAttribute("agentNum", agentNum);
        model.addAllAttributes(BeanUtil.beanToMap(user));
        model.addAttribute("roleName", ConstantFactory.me().getRoleName(user.getRoleId()));
        model.addAttribute("deptName", ConstantFactory.me().getDeptName(user.getDeptId()));
        model.addAttribute("avatar", DefaultImages.defaultAvatarUrl());
        model.addAttribute("user", user);
        model.addAttribute("logList", logList);
        return "/modular/frame/console3.html";
    }

    /**
     * 获取卡密月统计
     *
     * @author fengshuonan
     * @Date 2018/11/9 12:45 PM
     */
    @RequestMapping("/cardMonth")
    @ResponseBody
    public Object cardMonth() {
        String[] countArr = new String[29];
        List<CardMonth> cardMonths = cardInfoService.getCardMonth(LoginContextHolder.getContext().getUserId(),DateUtil.format(DateUtil.offsetDay(new Date(), -29),"YYYY-MM-dd"),countArr);
//        Collections.reverse(cardMonths);
        List<String> months = Lists.newArrayList();
        List<Integer> expireNums = Lists.newArrayList();
        List<Integer> activeNums = Lists.newArrayList();
        for (CardMonth cardMonth : cardMonths){
            months.add(DateUtil.format(cardMonth.getActiveTime(),"MM-dd"));
            expireNums.add(cardMonth.getExpireNum());
            activeNums.add(cardMonth.getActiveNum());
        }
        Map<String,Object> maps = Maps.newHashMap();
        maps.put("months",months);
        maps.put("expireNums",expireNums);
        maps.put("activeNums",activeNums);
        return JSON.toJSON(maps);
    }

    /**
     * 收入统计
     *
     * @author fengshuonan
     * @Date 2018/11/9 12:45 PM
     */
    @RequestMapping("/incomeStatistics")
    @ResponseBody
    public Object incomeStatistics() {
        String[] countArr = new String[10];
        List<IncomeStatistics> incomeStatisticsList = cardInfoService.getIncomeStatistics(LoginContextHolder.getContext().getUserId(),DateUtil.format(DateUtil.offsetDay(new Date(), -10),"YYYY-MM-dd"),countArr);
//        Collections.reverse(cardMonths);
        List<String> months = Lists.newArrayList();
        List<BigDecimal> price = Lists.newArrayList();
        for (IncomeStatistics incomeStatistics : incomeStatisticsList){
            months.add(DateUtil.format(incomeStatistics.getActiveTime(),"MM-dd"));
            price.add(incomeStatistics.getPrice());
        }
        Map<String,Object> maps = Maps.newHashMap();
        maps.put("months",months);
        maps.put("price",price);
        return JSON.toJSON(maps);
    }

    /**
     * 获取用户月统计
     *
     * @author fengshuonan
     * @Date 2018/11/9 12:45 PM
     */
    @RequestMapping("/accountMonth")
    @ResponseBody
    public Object accountMonth() {
        String[] countArr = new String[29];
        List<AccountMonth> cardMonths = accountInfoService.getAccountMonth(LoginContextHolder.getContext().getUserId(),DateUtil.format(DateUtil.offsetDay(new Date(), -29),"YYYY-MM-dd"),countArr);
//        Collections.reverse(cardMonths);
        List<String> months = Lists.newArrayList();
        List<Integer> expireNums = Lists.newArrayList();
        List<Integer> activeNums = Lists.newArrayList();
        for (AccountMonth accountMonth : cardMonths){
            months.add(DateUtil.format(accountMonth.getRegistrationTime(),"MM-dd"));
            expireNums.add(accountMonth.getExpireNum());
            activeNums.add(accountMonth.getActiveNum());
        }
        Map<String,Object> maps = Maps.newHashMap();
        maps.put("months",months);
        maps.put("expireNums",expireNums);
        maps.put("activeNums",activeNums);
        return JSON.toJSON(maps);
    }

    /**
     * 分析页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/console2")
    public String console2() {
        return "/modular/frame/console2.html";
    }

    /**
     * 系统硬件信息页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/systemInfo")
    public String systemInfo(Model model) {

        SystemHardwareInfo systemHardwareInfo = new SystemHardwareInfo();
        systemHardwareInfo.copyTo();

        model.addAttribute("server", systemHardwareInfo);

        return "/modular/frame/systemInfo.html";
    }

    /**
     * 跳转到首页通知
     *
     * @author fengshuonan
     * @Date 2018/12/23 6:06 PM
     */
    @RequestMapping("/notice")
    public String hello() {
        List<Notice> notices = noticeService.list();
        super.setAttr("noticeList", notices);
        return "/modular/frame/notice.html";
    }

    /**
     * 主页面
     *
     * @author fengshuonan
     * @Date 2019/1/24 3:38 PM
     */
    @RequestMapping("/welcome")
    public String welcome() {
        return "/modular/frame/welcome.html";
    }

    /**
     * 主题页面
     *
     * @author fengshuonan
     * @Date 2019/1/24 3:38 PM
     */
    @RequestMapping("/theme")
    public String theme() {
        return "/modular/frame/theme.html";
    }

    /**
     * 锁屏界面
     *
     * @author fengshuonan
     * @Date 2020/3/8 17:19
     */
    @RequestMapping("/lock")
    public String lock() {
        return "/modular/frame/lock-screen.html";
    }

    /**
     * 跳转到修改密码界面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/user_chpwd")
    public String chPwd() {
        return "/modular/frame/password.html";
    }

    /**
     * 个人消息列表
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/message")
    public String message() {
        return "/modular/frame/message.html";
    }

    /**
     * 跳转到查看用户详情页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/user_info")
    public String userInfo(Model model) {
        Long userId = LoginContextHolder.getContext().getUserId();
        User user = this.userService.getById(userId);

        model.addAllAttributes(BeanUtil.beanToMap(user));
        model.addAttribute("roleName", ConstantFactory.me().getRoleName(user.getRoleId()));
        model.addAttribute("deptName", ConstantFactory.me().getDeptName(user.getDeptId()));
        model.addAttribute("avatar", DefaultImages.defaultAvatarUrl());
        LogObjectHolder.me().set(user);

        return "/modular/frame/user_info.html";
    }

    /**
     * 通用的树列表选择器
     *
     * @author fengshuonan
     * @Date 2018/12/23 6:59 PM
     */
    @RequestMapping("/commonTree")
    public String deptTreeList(@RequestParam("formName") String formName,
                               @RequestParam("formId") String formId,
                               @RequestParam("treeUrl") String treeUrl, Model model) {

        if (ToolUtil.isOneEmpty(formName, formId, treeUrl)) {
            throw new RequestEmptyException("请求数据不完整！");
        }

        try {
            model.addAttribute("formName", URLDecoder.decode(formName, "UTF-8"));
            model.addAttribute("formId", URLDecoder.decode(formId, "UTF-8"));
            model.addAttribute("treeUrl", URLDecoder.decode(treeUrl, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RequestEmptyException("请求数据不完整！");
        }

        return "/common/tree_dlg.html";
    }

    /**
     * 更新头像
     *
     * @author fengshuonan
     * @Date 2018/11/9 12:45 PM
     */
    @RequestMapping("/updateAvatar")
    @ResponseBody
    public Object uploadAvatar(@RequestParam("fileId") String fileId) {

        if (ToolUtil.isEmpty(fileId)) {
            throw new RequestEmptyException("请求头像为空");
        }

        fileInfoService.updateAvatar(fileId);

        return SUCCESS_TIP;
    }

    /**
     * 预览头像
     *
     * @author fengshuonan
     * @Date 2018/11/9 12:45 PM
     */
    @RequestMapping("/previewAvatar")
    @ResponseBody
    public Object previewAvatar(HttpServletResponse response) {

        //输出图片的文件流
        try {
            response.setContentType("image/jpeg");
            byte[] decode = this.fileInfoService.previewAvatar();
            response.getOutputStream().write(decode);
        } catch (IOException e) {
            throw new ServiceException(CoreExceptionEnum.SERVICE_ERROR);
        }

        return null;
    }

    /**
     * 获取当前用户详情
     *
     * @author fengshuonan
     * @Date 2018/12/23 6:59 PM
     */
    @RequestMapping("/currentUserInfo")
    @ResponseBody
    public ResponseData getUserInfo() {

        LoginUser currentUser = LoginContextHolder.getContext().getUser();
        if (currentUser == null) {
            throw new ServiceException(CoreExceptionEnum.NO_CURRENT_USER);
        }

        return new SuccessResponseData(userService.getUserInfo(currentUser.getId()));
    }

    /**
     * layui上传组件 通用文件上传接口
     *
     * @author fengshuonan
     * @Date 2019-2-23 10:48:29
     */
    @RequestMapping(method = RequestMethod.POST, path = "/upload")
    @ResponseBody
    public ResponseData layuiUpload(@RequestPart("file") MultipartFile file) {

        UploadResult uploadResult = this.fileInfoService.uploadFile(file);
        String fileId = uploadResult.getFileId();

        HashMap<String, Object> map = new HashMap<>();
        map.put("fileId", fileId);

        return ResponseData.success(0, "上传成功", map);
    }


}
