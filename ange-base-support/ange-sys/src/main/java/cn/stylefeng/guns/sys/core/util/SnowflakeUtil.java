package cn.stylefeng.guns.sys.core.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * <p>分布式系统生成随机唯一自增数（单例）</p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2020/12/10
 * @since JDK 1.8
 */
public class SnowflakeUtil {
    private static Snowflake snowflake = null;

    public static Snowflake getInstance() {
        if (snowflake == null) {
            snowflake = IdUtil.getSnowflake(1, 1);
        }
        return snowflake;
    }
}
