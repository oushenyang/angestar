package cn.stylefeng.guns.core.constant.state;

import lombok.Getter;

/**
 * redis类型
 *
 * @author fengshuonan
 * @Date 2017年1月22日 下午12:14:59
 */
@Getter
public enum RedisType {

    //卡类时间类型  0-分；1-时；2-天；3-周；4-月；5-年
    CALL_CODE("call_code", "call_code"),
    APP_ID("APP_ID", "APP_ID"),
    DAY("", "天"),
    WEEK("", "周"),
    MONTH("", "月"),
    YEAR("", "年");

    String code;
    String message;

    RedisType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getRedisTypeName(String status) {
        for (RedisType s : RedisType.values()) {
            if (s.getCode().equals(status)) {
                return s.getMessage();
            }
        }
        return "";
    }
}
