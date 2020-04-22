package cn.stylefeng.guns.sys.core.util;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;

import javax.xml.crypto.Data;
import java.util.Date;

/**
 * <p></p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2020/4/22
 * @since JDK 1.8
 */
public class CardDateUtil {
    /**
     * 获取到期时间
     * @param activeTime 激活时间
     * @param cardTimeType 卡类时间类型  0-分；1-时；2-天；3-周；4-月；5-年
     * @param cardTypeData 卡值
     * @return 到期时间
     */
    public static DateTime getExpireTime(Date activeTime, Integer cardTimeType, Integer cardTypeData){
        DateTime expireTime = null;
        switch (cardTimeType){
            case 0:
                expireTime = DateUtil.offsetMinute(activeTime, cardTypeData);
                break;
            case 1:
                expireTime = DateUtil.offsetHour(activeTime, cardTypeData);
                break;
            case 2:
                expireTime = DateUtil.offsetDay(activeTime, cardTypeData);
                break;
            case 3:
                expireTime = DateUtil.offsetWeek(activeTime, cardTypeData);
                break;
            case 4:
                expireTime = DateUtil.offsetMonth(activeTime, cardTypeData);
                break;
            case 5:
                expireTime = DateUtil.offsetMonth(activeTime, cardTypeData*12);
                break;
        }
        return expireTime;
    }

}
