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

    //api接口
    API_MANAGE("ANGE:API_MANAGE:"),
    APP_INFO("ANGE:APP_INFO:"),
    CARD_INFO("ANGE:CARD_INFO:"),
    DEVICE("ANGE:DEVICE:"),
    TOKEN("ANGE:TOKEN:"),
    HUANYIN("huanyin"),
    APP_ID("APP_ID:");
    String code;
    RedisType(String code) {
        this.code = code;
    }

    public static String getRedisTypeName(String status) {
        for (RedisType s : RedisType.values()) {
            if (s.getCode().equals(status)) {
                return s.getCode();
            }
        }
        return "";
    }
}
