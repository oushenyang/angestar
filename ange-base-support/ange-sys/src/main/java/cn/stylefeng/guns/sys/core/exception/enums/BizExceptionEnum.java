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
package cn.stylefeng.guns.sys.core.exception.enums;

import cn.stylefeng.roses.kernel.model.exception.AbstractBaseExceptionEnum;

/**
 * @author fengshuonan
 * @Description 所有业务异常的枚举
 * @date 2016年11月12日 下午5:04:51
 */
public enum BizExceptionEnum implements AbstractBaseExceptionEnum {

    /**
     * 字典
     */
    DICT_EXISTED(400, "字典已经存在"),
    ERROR_CREATE_DICT(500, "创建字典失败"),
    ERROR_WRAPPER_FIELD(500, "包装字典属性失败"),
    ERROR_CODE_EMPTY(500, "字典类型不能为空"),

    /**
     * 文件上传
     */
    FILE_READING_ERROR(400, "FILE_READING_ERROR!"),
    FILE_NOT_FOUND(400, "FILE_NOT_FOUND!"),
    UPLOAD_ERROR(500, "上传图片出错"),

    /**
     * 权限和数据问题
     */
    DB_RESOURCE_NULL(400, "数据库中没有该资源"),
    NO_PERMITION(405, "权限异常"),
    REQUEST_INVALIDATE(400, "请求数据格式不正确"),
    INVALID_KAPTCHA(400, "验证码不正确"),
    CANT_DELETE_ADMIN(600, "不能删除超级管理员"),
    CANT_FREEZE_ADMIN(600, "不能冻结超级管理员"),
    CANT_CHANGE_ADMIN(600, "不能修改超级管理员角色"),

    /**
     * 账户问题
     */
    NOT_LOGIN(401, "当前用户未登录"),
    USER_ALREADY_REG(401, "该用户已经注册"),
    NO_THIS_USER(400, "没有此用户"),
    USER_NOT_EXISTED(400, "没有此用户"),
    ACCOUNT_FREEZED(401, "账号被冻结"),
    OLD_PWD_NOT_RIGHT(402, "原密码不正确"),
    TWO_PWD_NOT_MATCH(405, "两次输入密码不一致"),

    /**
     * 错误的请求
     */
    MENU_PCODE_COINCIDENCE(400, "菜单编号和副编号不能一致"),
    EXISTED_THE_MENU(400, "菜单编号重复，不能添加"),
    DICT_MUST_BE_NUMBER(400, "字典的值必须为数字"),
    REQUEST_NULL(400, "请求有错误"),
    SESSION_TIMEOUT(400, "会话超时"),
    SERVER_ERROR(500, "服务器异常"),

    /**
     * token异常
     */
    TOKEN_EXPIRED(700, "token过期"),
    TOKEN_ERROR(700, "token验证失败"),

    /**
     * 签名异常
     */
    SIGN_ERROR(700, "签名验证失败"),

    /**
     * 系统常量
     */
    ALREADY_CONSTANTS_ERROR(400, "已经存在该编码的系统参数"),
    SYSTEM_CONSTANT_ERROR(400, "不能删除系统常量"),

    /**
     * 其他
     */
    AUTH_REQUEST_ERROR(400, "账号密码错误"),

    /**
     * ueditor相关异常
     */
    UE_CONFIG_ERROR(800, "读取ueditor配置失败"),
    UE_FILE_NULL_ERROR(801, "上传文件为空"),
    UE_FILE_READ_ERROR(803, "读取文件错误"),
    UE_FILE_SAVE_ERROR(802, "保存ue的上传文件出错"),

    /**
     * 工作流相关
     */
    ACT_NO_FLOW(900, "无可用流程，请先导入或新建流程"),
    ACT_ADD_ERROR(901, "新建流程错误"),

    /**
     * 租户相关的异常
     */
    NO_TENANT_ERROR(1901, "没有相关租户"),

    /**
     * 应用相关的异常
     */
    ADD_HEAD_ERROR(400, "生成应用头像失败"),

    /**
     * 应用版本相关的异常
     */
    ALREADY_APPEDITION_ERROR(400, "已经存在该版本号"),
    UPDATE_APPEDITION_ERROR(400, "应用更新版本号失败"),

    /**
     * 动态执行sql的异常
     */
    SQL_ERROR(400, "sql执行失败"),

    /**
     * 卡密相关的异常
     */
    UN_SELECT_CARD(400, "未选择卡密"),
    UN_FIND_CARD(400, "未找到相关卡密"),

    /**
     * 卡类相关的异常
     */
    CARD_TYPE_NAME_EXISTED(400, "卡类名称已经存在"),

    /**
     * 代理审核相关异常
     */
    INVITED_AGENT(400, "您已邀请过该代理，请等待代理审批"),
    INVITED_OTHER_AGENT(400, "该应用已有其他用户邀请过该代理，您不能重复邀请"),
    ALREADY_AGENT(400, "该用户已经是您的代理，请勿重复操作"),
    ALREADY_AGENT_APP(400, "该用户已经代理过该软件，请勿重复操作"),
    AGREED_AGENT(400, "已同意代理，请勿重复操作"),

    /**
     * 代理操作相关异常
     */
    NOT_AGENT(400, "您还不是代理，不能生成卡密"),
    DISABLE_AGENT(400, "您的代理已被冻结，无法操作，请联系开发者处理"),
    INSUFFICIENT_BALANCE_AGENT(400, "代理余额不足，请联系上级代理充值"),
    SUBORDINATE_INSUFFICIENT_BALANCE_AGENT(400, "您的代理余额不足，无法为下级代理充值，请联系上级代理充值"),
    GREATER_THAN_SUBORDINATE_INSUFFICIENT_BALANCE_AGENT(400, "扣款金额大于下级代理余额，无法操作"),
    NO_SELF_AGENT(400, "不能添加自己为代理"),
    NO_ROSE_AGENT(400, "您不是总代，不能添加下级代理，请联系上级代理处理"),

    /**
     * 代理权限相关异常
     */
    NOT_CARD_CREATE(400, "您没有生成卡密权限，无法操作，请联系上级代理开通");


    BizExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;

    private String message;

    @Override
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
