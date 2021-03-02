package cn.stylefeng.guns.core.constant.state;

import lombok.Getter;

/**
 * 卡密规则
 *
 * @author fengshuonan
 * @Date 2017年1月22日 下午12:14:59
 */
@Getter
public enum CardTypeRule {

    //0-大写字母+数字；1-小写字母+数字；2-全大写字母；3-全小写字母；4-全数字；
    CAPITAL_AND_NUM(0, "大写字母+数字"),
    LOWER_CASE_AND_NUM(1, "小写字母+数字"),
    CAPITAL(2, "全大写字母"),
    LOWER_CASE(3, "全小写字母"),
    NUM(4, "全数字");

    int code;
    String message;

    CardTypeRule(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getCardStatusName(int status) {
        for (CardTypeRule s : CardTypeRule.values()) {
            if (s.getCode()==status) {
                return s.getMessage();
            }
        }
        return "";
    }
}
