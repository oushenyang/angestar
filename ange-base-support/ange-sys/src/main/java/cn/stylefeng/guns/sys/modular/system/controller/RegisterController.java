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
package cn.stylefeng.guns.sys.modular.system.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.base.auth.service.AuthService;
import cn.stylefeng.guns.sys.core.auth.cache.SessionManager;
import cn.stylefeng.guns.sys.core.constant.state.ManagerStatus;
import cn.stylefeng.guns.sys.core.exception.InvalidKaptchaException;
import cn.stylefeng.guns.sys.core.exception.enums.BizExceptionEnum;
import cn.stylefeng.guns.sys.core.util.SaltUtil;
import cn.stylefeng.guns.sys.modular.system.entity.User;
import cn.stylefeng.guns.sys.modular.system.service.UserService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.RequestEmptyException;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import cn.stylefeng.roses.kernel.model.response.SuccessResponseData;
import com.google.code.kaptcha.Constants;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 注册控制器
 *
 * @author fengshuonan
 * @Date 2017年1月10日 下午8:25:24
 */
@Controller
public class RegisterController extends BaseController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private SessionManager sessionManager;

    /**
     * 前台跳转到代理注册页面
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:41 PM
     */
    @RequestMapping(value = "/agentRegister", method = RequestMethod.GET)
    public String agentRegister() {
        return "/agentRegister.html";
    }


    /**
     * 保存
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:42 PM
     */
    @RequestMapping(value = "/agentRegister/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData save() {
        String name = super.getPara("name");
        String username = super.getPara("username");
        String password = super.getPara("password");
        if (ToolUtil.isOneEmpty(name)) {
            throw new RequestEmptyException("昵称不能为空");
        }
        if (ToolUtil.isOneEmpty(username)) {
            throw new RequestEmptyException("手机号码不能为空");
        }
        if (ToolUtil.isOneEmpty(password)) {
            throw new RequestEmptyException("密码不能为空");
        }
        String kaptcha = super.getPara("kaptcha").trim();
        String code1 = (String) super.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if (ToolUtil.isEmpty(kaptcha) || !kaptcha.equalsIgnoreCase(code1)) {
            throw new InvalidKaptchaException();
        }
        User user1 = userService.getByAccount(username);
        if (ObjectUtil.isNotNull(user1)){
            throw new ServiceException(400,"账号已存在,不能重复注册");
        }
        // 完善账号信息
        String salt = SaltUtil.getRandomSalt();
        String password1 = SaltUtil.md5Encrypt(password, salt);
        User user = new User();
        user.setAccount(username);
        user.setPhone(username);
        user.setName(name);
        user.setRoleCode("agent");
//        user.setUserType(1);
        user.setCreateTime(new Date());
        user.setStatus(ManagerStatus.OK.getCode());
        user.setPassword(password1);
        user.setSalt(salt);
        userService.save(user);
        return new SuccessResponseData();
    }

}