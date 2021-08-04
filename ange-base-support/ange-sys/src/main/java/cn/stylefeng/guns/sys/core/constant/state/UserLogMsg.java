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
    APP_ADD("新增应用","新增了应用{}"),
    APP_DEL("删除应用","删除了应用{}"),
    APP_UP("更新应用","更新了应用{}"),
    EDITION_ADD("新增版本","为应用{}新增了{}版本"),
    EDITION_DEl("删除版本","为应用{}删除了{}版本"),
    EDITION_UP("更新版本","为应用{}更新了{}版本"),
    CARD_ADD("新增卡密","为应用{}新增了{}张{}"),
    CARD_ONE_DEL("删除卡密","为应用{}删除了卡密{}"),
    API("","接口相关");

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
