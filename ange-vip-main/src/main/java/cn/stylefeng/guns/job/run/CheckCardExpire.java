package cn.stylefeng.guns.job.run;

import cn.stylefeng.guns.core.schedue.ScheduleUtils;
import cn.stylefeng.guns.modular.card.entity.CardInfo;
import cn.stylefeng.guns.modular.card.service.CardInfoService;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * <p>检查卡密是否到期</p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2021/2/1
 * @since JDK 1.8
 */
@Component
public class CheckCardExpire implements org.quartz.Job{
    private final Logger logger = LoggerFactory.getLogger(CheckCardExpire.class);

    @Autowired
    private CardInfoService cardInfoService;

    @Override
    public void execute(JobExecutionContext context) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        logger.info("----------检查卡密是否到期开始----------"+sdf.format(new Date()));
        try {
            //检查到期卡密并设置过期
            List<CardInfo> cardList = cardInfoService.checkCardExpireAndSet();
            Long id =ScheduleUtils.witerlog(context,0,"操作成功,共检测到"+cardList.size()+"条数据");
            ScheduleUtils.witerListLogToTxt(context, Collections.singletonList(cardList),id);
            logger.info("设置卡密到期成功"+sdf.format(new Date()));
        } catch (Exception e) {
            ScheduleUtils.witerlog(context,1,"发生错误 " + e.getMessage());
            e.printStackTrace();
        }
        logger.info("----------检查卡密是否到期结束----------"+sdf.format(new Date()));

    }
}
