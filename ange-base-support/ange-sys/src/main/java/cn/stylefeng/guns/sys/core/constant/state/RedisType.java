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
    API_MANAGE("API_MANAGE:CALL_CODE:","API_MANAGE:CALL_CODE:"),
    APP_INFO("APP_INFO:CALL_CODE:","APP_INFO:CALL_CODE:"),
    CARD_INFO("CARD_INFO:CARD_CODE:","CARD_INFO:CARD_CODE:"),
    DEVICE("DEVICE","DEVICE"),
    TOKEN("TOKEN","TOKEN"),
    API_RESULT("API_RESULT:APP_ID_AND_RESULT_CODE:","API_RESULT:APP_ID_AND_RESULT_CODE:"),
    REMOTE_DATA("REMOTE_DATA:APP_ID:","REMOTE_DATA:APP_ID"),

    APP_POWER("ANGE:APP_POWER:APP_TYPE_CODE:","APP_POWER"),
    HUANYIN("HUANYIN:utDid:sign:appCode:","HUANYIN:utDid:sign:appCode:"),
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
