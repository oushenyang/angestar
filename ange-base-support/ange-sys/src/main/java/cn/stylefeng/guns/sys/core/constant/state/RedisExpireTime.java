package cn.stylefeng.guns.sys.core.constant.state;

import lombok.Getter;

/**
 * redis到期时间
 *
 * @author fengshuonan
 * @Date 2017年1月22日 下午12:14:59
 */
@Getter
public enum RedisExpireTime {

    //api接口,hash表
    DAY(259200L,"三天"),
    WEEK(604800L,"七天"),
    MONTH(2592000L,"一个月");
    long code;
    String message;
    RedisExpireTime(Long code, String message) {
        this.code = code;
        this.message = message;
    }
}
