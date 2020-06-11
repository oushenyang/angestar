package cn.stylefeng.guns.core.constant.state;

import lombok.Getter;

/**
 * 卡类时间类型
 *
 * @author fengshuonan
 * @Date 2017年1月22日 下午12:14:59
 */
@Getter
public enum CardTimeType {

    //卡类时间类型  0-分；1-时；2-天；3-周；4-月；5-年
    MINUTE(0, "分"),
    HOUR(1, "时"),
    DAY(2, "天"),
    WEEK(3, "周"),
    MONTH(4, "月"),
    YEAR(5, "年");

    int code;
    String message;

    CardTimeType(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getCardStatusName(int status) {
        for (CardTimeType s : CardTimeType.values()) {
            if (s.getCode()==status) {
                return s.getMessage();
            }
        }
        return "";
    }
}
