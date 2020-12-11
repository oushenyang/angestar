package cn.stylefeng.guns.core.constant.type;

import lombok.Getter;

/**
 * 卡类类型
 *
 * @author shenyang.ou
 * @Date 2017年1月22日 下午12:14:59
 */
@Getter
public enum CardType {
    SINGLE_CARD(0, "单码卡密"),
    CURRENCY_CARD(1, "通用卡密"),
    ACCOUNT_CARD(2, "注册卡密");

    int code;
    String message;

    CardType(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
