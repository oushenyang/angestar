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
 * 用户日志类型
 *
 * @author fengshuonan
 * @Date 2017年1月22日 下午12:14:59
 */
public enum UserLogType {

    //日志类型：1-应用相关；2-卡密相关；3-账号相关；4-代理相关；5-试用相关；6-数据相关；7-接口相关
    APP(1,"应用相关"),
    CARD(2,"卡密相关"),
    ACCOUNT(3,"账号相关"),
    AGENT(4,"代理相关"),
    TRY(5,"试用相关"),
    DATA(6,"数据相关"),
    API(7,"接口相关");

    Integer type;
    String message;

    UserLogType(Integer type,String message) {
        this.type = type;
        this.message = message;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
