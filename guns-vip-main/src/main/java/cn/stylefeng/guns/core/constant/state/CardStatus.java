package cn.stylefeng.guns.core.constant.state;

import lombok.Getter;

/**
 * 卡密的状态
 *
 * @author fengshuonan
 * @Date 2017年1月22日 下午12:14:59
 */
@Getter
public enum CardStatus {

    //0-未激活；1-已激活；2-已过期；3-已禁用；4-已删除
    NOT_ACTIVE(0, "未激活"),
    ACTIVATED(1, "已激活"),
    EXPIRED(2, "已过期"),
    DISABLED(3, "已禁用"),
    DELETED(4, "已删除");

    int code;
    String message;

    CardStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getCardStatusName(int status) {
        for (CardStatus s : CardStatus.values()) {
            if (s.getCode()==status) {
                return s.getMessage();
            }
        }
        return "";
    }
}
