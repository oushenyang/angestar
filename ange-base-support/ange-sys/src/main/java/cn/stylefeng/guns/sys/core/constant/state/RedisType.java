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

    //api接口,hash表,一周
    API_MANAGE("API_MANAGE:CALL_CODE:",604800L),
    //应用,一周
    APP_INFO("APP_INFO:CALL_CODE:",604800L),
    //应用版本,一周
    EDITION("EDITION:APP_ID:",604800L),
    //卡密信息,三天
    CARD_INFO("CARD_INFO:CARD_CODE:",259200L),
    //设备信息,三天
    DEVICE("DEVICE",259200L),
    //token,三天
    TOKEN("TOKEN",259200L),
    //返回结果,一周
    API_RESULT("API_RESULT:APP_ID_AND_RESULT_CODE:",604800L),
    //在线人数，五分钟
    ONLINE_NUM("ONLINE_NUM:APP_ID_AND_LIMIT:",300L),
    //远程数据,三天
    REMOTE_DATA("REMOTE_DATA:APP_ID:",259200L),
    //字典，三天
    DICT("DICT:DICTTYPECODE:",259200L),
    //盗版信息,一周
    APP_POWER("ANGE:APP_POWER:APP_TYPE_CODE:",604800L),
    //幻影,一周
    HUANYIN("HUANYIN:utDid:sign:appCode:",604800L),
    APP_ID("ANGE:APP_ID:",604800L);

    String code;
    Long time;
    RedisType(String code, Long time) {
        this.code = code;
        this.time = time;
    }
}
