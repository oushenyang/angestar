package cn.stylefeng.guns.sys.core.constant.state;

import lombok.Getter;

/**
 * redis类型
 *
 * @author fengshuonan
 * @Date 2017年1月22日 下午12:14:59
 */
@Getter
public enum RedisType {

    //api接口,hash表
    API_MANAGE("ANGE:API_MANAGE:CALL_CODE:","ANGE:API_MANAGE:CALL_CODE:"),
    APP_INFO("ANGE:APP_INFO:APP_ID:","ANGE:APP_INFO:APP_ID:"),
    CARD_INFO("ANGE:CARD_INFO:APP_ID_AND_SINGLE_CODE:","ANGE:CARD_INFO:APP_ID_AND_SINGLE_CODE:"),
    DEVICE("ANGE:DEVICE:CARD_ID:","ANGE:DEVICE:CARD_ID:"),
    TOKEN("ANGE:TOKEN:CARD_ID:","ANGE:TOKEN:CARD_ID:"),
    API_RESULT("ANGE:API_RESULT:APP_ID_AND_RESULT_CODE:","ANGE:API_RESULT:APP_ID_AND_RESULT_CODE:"),

    APP_POWER("APP_POWER","APP_POWER"),
    HUANYIN("HUANYIN","HUANYIN"),
    APP_ID("ANGE:APP_ID:","ANGE:APP_ID:");
    String code;
    String message;
    RedisType(String code, String message) {
        this.code = code;
        this.message = message;
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
