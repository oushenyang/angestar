package cn.stylefeng.guns.sys.core.exception.inter;

import java.lang.annotation.*;

/**
 * <p></p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2021/3/2
 * @since JDK 1.8
 */
@Inherited
@Documented
@Target({ElementType.FIELD,ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLimit {
    //指定second 时间内 API请求次数
    int times() default 4;

    // 请求次数的指定时间范围  秒数(redis数据过期时间)
    int second() default 10;
}
