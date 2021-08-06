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
package cn.stylefeng.guns.sys.core.constant.state;

/**
 * 用户日志消息类型
 *
 * @author fengshuonan
 * @Date 2017年1月22日 下午12:14:59
 */
public enum UserLogMsg {
    APP_ADD("应用新增","新增了应用{}"),
    APP_DEL("应用删除","删除了应用{}"),
    APP_UP("应用更新","更新了应用{}"),
    EDITION_ADD("版本新增","新增了{}版本"),
    EDITION_DEl("版本删除","删除了{}版本"),
    EDITION_UP("版本更新","更新了{}版本"),
    CARD_ADD("卡密新增","新增了{}张{}"),
    CARD_ONE_DEL("卡密删除","删除了卡密{}"),
    CARD_MORE_DEL("卡密删除","删除了{}张卡密"),
    CARD_ONE_PROHIBITION("卡密禁用","禁用了卡密{}"),
    CARD_MORE_PROHIBITION("卡密禁用","禁用了{}张卡密"),
    CARD_ONE_UNSEALING("卡密解封","解封了卡密{}"),
    CARD_MORE_UNSEALING("卡密解封","解封了{}张卡密"),

    CARD_ONE_OVERTIME("卡密加时","加时了卡密{}"),
    CARD_MORE_OVERTIME("卡密加时","加时了{}张卡密"),

    CARD_ONE_UNTYING("卡密解绑","解绑了卡密{}"),
    CARD_MORE_UNTYING("卡密解绑","解绑了{}张卡密"),

    CARD_ONE_REMARK("卡密备注","备注了卡密{}"),
    CARD_MORE_REMARK("卡密备注","备注了{}张卡密"),
    CARD_ONE_DATA("卡密数据","为卡密{}新增了数据"),
    CARD_MORE_DATA("卡密数据","为{}张卡密新增了数据"),
    CARD_EXPORT("卡密导出","导出了{}张卡密"),
    CARD_IMPORT("卡密导入","导如了{}张卡密");

    String logName;
    String message;

    UserLogMsg(String logName,String message) {
        this.logName = logName;
        this.message = message;
    }

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
