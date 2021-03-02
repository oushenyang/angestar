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
 * <p>删除redis卡密缓存</p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2021/2/1
 * @since JDK 1.8
 */
@Component
public class DeleteRedisCard implements org.quartz.Job{
    private final Logger logger = LoggerFactory.getLogger(DeleteRedisCard.class);

    @Autowired
    private CardInfoService cardInfoService;

    @Override
    public void execute(JobExecutionContext context) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        logger.info("----------删除redis卡密缓存开始----------"+sdf.format(new Date()));
        try {
            List<CardInfo> cardList = cardInfoService.list();
            Long id =ScheduleUtils.witerlog(context,0,"操作成功,共删除"+cardList.size()+"条数据");
            ScheduleUtils.witerListLogToTxt(context, Collections.singletonList(cardList),id);
            logger.info("删除redis卡密缓存成功"+sdf.format(new Date()));
        } catch (Exception e) {
            ScheduleUtils.witerlog(context,1,"发生错误 " + e.getMessage());
            e.printStackTrace();
        }
        logger.info("----------删除redis卡密缓存结束----------"+sdf.format(new Date()));

    }
}
