package cn.stylefeng.guns.config.listener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 跟任务相关的事件包括：job开始要执行的提示; job执行完成的提示灯
 */
public class TimerJobListener implements org.quartz.JobListener {

    private static Logger logger = LoggerFactory.getLogger(TimerJobListener.class);

    @Override
    public String getName() {
        String name = getClass().getSimpleName();
        return name;
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        String jobName = context.getJobDetail().getKey().getName();
        logger.info("当前执行的jobName:"+jobName+"--;开始执行" );
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        String jobName = context.getJobDetail().getKey().getName();
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        String jobName = context.getJobDetail().getKey().getName();
        logger.info("当前执行的jobName:"+jobName+"--;结束执行"  );

    }
}
