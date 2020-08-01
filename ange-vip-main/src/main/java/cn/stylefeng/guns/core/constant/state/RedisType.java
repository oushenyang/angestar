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
    API_MANAGE("apiManage", "apiManage"),
    APP_INFO("appInfo", "appInfo"),
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
