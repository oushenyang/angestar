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
 * @Description 所有api业务异常的枚举
 * @date 2016年11月12日 下午5:04:51
 */
public enum ApiExceptionEnum implements AbstractBaseExceptionEnum {

    /**
     * 系统相关
     */
    API_BAD(1, "接口不正确"),
    PARAMETER_HAS_NULL(2, "必传参数存在空值"),
    DATA_ERROR(3, "传入数据包错误"),
    SYSTEM_ERROR(4, "系统错误"),
    REQUESTS_TOO_FREQUENT(5, "请求过于频繁，请稍后再试"),

    /**
     * 公共相关
     */
    DATA_OVERTIME(10, "数据包超时"),
    API_CLOSE(11, "接口未开启"),
    TIMESTAMP_NULL(12, "已开启超时验证，时间戳必传"),
    DATA_TYPE_ERROR(13, "传入数据类型或格式错误"),
    SIGN_ERROR(14, "签名不正确"),

    /**
     * 应用相关
     */
    APPINFO_SUCCESS(1000, "获取应用信息成功"),
    ALREADY_LATEST_VERSION(1001, "已经是最新版本"),
    CHECK_UPDATE_SUCCESS(1002, "检查版本更新成功"),
    ONLINE_NUM_SUCCESS(1003, "取在线人数成功"),
    APP_CLOSED(1004, "应用已关闭"),

    /**
     * 单码相关
     */
    LOGIN_SUCCESS(2000, "登录成功"),
    CARD_NO(2001, "卡密不存在"),
    CARD_BIND_MAC_NO(2002, "卡密未在绑定的设备上登录"),
    CARD_BIND_IP_NO(2003, "卡密未在绑定的ip上登录"),
    CARD_BIND_IP_OR_MAC_NO(2004, "卡密未在绑定的设备或ip上登录"),
    CARD_DISABLED(2005, "卡密已被禁用"),
    CARD_EXPIRE(2006, "卡密已过期"),
    CARD_LOGIN_OTHER(2007, "卡密在别的设备上登录"),
    CARD_LOGIN_MAX(2008, "卡密超过最大登录数,如果确定已经下线,请等60分钟后重试"),
    OUTSIDE_TIMED_OUT(2009, "外部验证接口连接超时，请在应用设置中关闭外部应用对接"),
    TOKEN_EXPIRE(2010, "卡密登录过期，请重新登录"),
    CARD_STATE_NORMAL(2011, "卡密用户状态正常"),
    GET_CARD_SUCCESS(2012, "获取卡密信息成功"),

    /**
     * 试用相关
     */
    TRIAL_SUCCESS(3000, "试用成功"),
    TRIAL_CLOSE(3001, "试用功能已关闭"),
    TRIAL_EXPIRE(3002, "试用已过期");

    ApiExceptionEnum(int code, String message) {
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
