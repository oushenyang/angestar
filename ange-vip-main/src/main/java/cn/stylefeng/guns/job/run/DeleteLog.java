package cn.stylefeng.guns.job.run;

import cn.stylefeng.guns.core.schedue.ScheduleUtils;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DeleteLog implements org.quartz.Job  {

    private final Logger logger = LoggerFactory.getLogger(DeleteLog.class);

    @Override
    public void execute(JobExecutionContext context) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        logger.info("----------删除七天前日志开始----------"+sdf.format(new Date()));
        try {
            ScheduleUtils.delLog();
            ScheduleUtils.witerlog(context,0,"日志删除成功");
            logger.info("日志删除成功"+sdf.format(new Date()));
        } catch (Exception e) {
            ScheduleUtils.witerlog(context,1,"发生错误 " + e.getMessage());
            e.printStackTrace();
        }
        logger.info("----------删除七天前日志结束----------"+sdf.format(new Date()));

    }
}
