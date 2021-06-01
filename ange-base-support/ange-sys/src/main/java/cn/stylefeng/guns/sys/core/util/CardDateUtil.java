package cn.stylefeng.guns.sys.core.util;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
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

    /**
     * 根据卡类名称获取到期时间
     * @param cardTypeName 卡类名称
     * @param date 激活时间
     * @return 到期时间
     */
    public static DateTime getExpireTimeByCardTypeName(String cardTypeName,Date date){
        //到期时间
        DateTime expireTime = null;
        switch (cardTypeName){
            case "小时卡":
                expireTime = DateUtil.offsetHour(date, 1);
                break;
            case "六时卡":
                expireTime = DateUtil.offsetHour(date, 6);
                break;
            case "天卡":
                expireTime = DateUtil.offsetDay(date, 1);
                break;
            case "周卡":
                expireTime = DateUtil.offsetWeek(date, 1);
                break;
            case "半月卡":
                expireTime = DateUtil.offsetDay(date, 15);
                break;
            case "月卡":
                expireTime = DateUtil.offsetMonth(date, 1);
                break;
            case "季卡":
                expireTime = DateUtil.offsetMonth(date, 3);
                break;
            case "半年卡":
                expireTime = DateUtil.offsetMonth(date, 6);
                break;
            case "年卡":
                expireTime = DateUtil.offsetMonth(date, 99*12);
                break;

        }
        return expireTime;
    }

    /**
     * 加时时间处理
     * @param expireTime 到期时间
     * @param addDayNum 天数
     * @param addHourNum 小时
     * @param addMinuteNum 分钟
     * @return 处理的到期时间
     */
    public static DateTime getAddExpireTime(Date expireTime, Integer addDayNum, Integer addHourNum,Integer addMinuteNum){
        if (addDayNum!=null){
            expireTime = DateUtil.offsetDay(expireTime, addDayNum);
        }
        if (addHourNum!=null){
            expireTime = DateUtil.offsetHour(expireTime, addHourNum);
        }
        if (addMinuteNum!=null){
            expireTime = DateUtil.offsetMinute(expireTime, addMinuteNum);
        }
        return DateTime.of(expireTime);
    }

    /**
     * 获取token清理间隔
     * @param expireTime 到期时间
     * @param clearSpace 清理间隔
     * @return 返回秒
     */
    public static long getClearSpace(Date expireTime, Integer clearSpace){
        long time = 0;
        if (clearSpace==0){
            time = DateUtil.between(new Date(), expireTime, DateUnit.SECOND)+86400;
        }else {
            time = clearSpace*60*60;
        }
        return time;
    }

    /**
     * 获取token清理间隔
     * @param clearSpace 清理间隔
     * @return 返回分钟
     */
    public static int getClearSpace(Integer clearSpace){
        return clearSpace*60;
    }

    /**
     * 获取到期时间清理间隔
     * @param expireTime 到期时间
     * @return 返回秒
     */
    public static long getExpireTimeSpace(Date expireTime){
        return DateUtil.between(new Date(), expireTime, DateUnit.SECOND)+86400;
    }

}
