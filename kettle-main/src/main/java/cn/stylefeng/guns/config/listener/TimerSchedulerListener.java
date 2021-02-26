package cn.stylefeng.guns.config.listener;

import org.quartz.*;
import org.slf4j.LoggerFactory;

/**
 * SchedulerListener会在Scheduler的生命周期中关键事件发生时被调用。与Scheduler有关的事件包括：增加一个job/trigger，
 * 删除一个job/trigger，scheduler发生严重错误，关闭scheduler等
 */
public class TimerSchedulerListener implements SchedulerListener {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(TimerSchedulerListener.class);

    @Override
    public void jobScheduled(Trigger trigger) {
        String jobName = trigger.getJobKey().getName();
        logger.info(jobName +"部署");
    }

    @Override
    public void jobUnscheduled(TriggerKey triggerKey) {
        logger.info(triggerKey + "卸载");
//        System.out.println(triggerKey + "卸载");
    }

    @Override
    public void triggerFinalized(Trigger trigger) {
        logger.info("触发完成" + trigger.getJobKey().getName());
    }

    @Override
    public void triggerPaused(TriggerKey triggerKey) {
        logger.info(triggerKey + "暂停");
    }

    @Override
    public void triggersPaused(String triggerGroup) {
        logger.info("trigger group "+triggerGroup + "暂停");
    }

    @Override
    public void triggerResumed(TriggerKey triggerKey) {
        logger.info(triggerKey + "从暂停中恢复");
    }

    @Override
    public void triggersResumed(String triggerGroup) {
        logger.info("trigger group "+triggerGroup + "从暂停中恢复");
    }

    @Override
    public void jobAdded(JobDetail jobDetail) {
        logger.info(jobDetail.getKey()+"增加");
    }

    @Override
    public void jobDeleted(JobKey jobKey) {
        logger.info(jobKey+"删除");
    }

    @Override
    public void jobPaused(JobKey jobKey) {
        logger.info(jobKey+"暂停");
    }

    @Override
    public void jobsPaused(String jobGroup) {
        logger.info("job group "+jobGroup+"暂停");
    }

    @Override
    public void jobResumed(JobKey jobKey) {
        logger.info(jobKey+"从暂停上恢复");
    }

    @Override
    public void jobsResumed(String jobGroup) {
        logger.info("job group "+jobGroup+"从暂停上恢复");
    }

    @Override
    public void schedulerError(String msg, SchedulerException cause) {
        logger.error(msg, cause.getUnderlyingException());
        logger.info("正常运行期间产生一个严重错误");
    }

    @Override
    public void schedulerInStandbyMode() {
        logger.info("Scheduler处于StandBy模式");
    }

    @Override
    public void schedulerStarted() {
        logger.info("scheduler开启完成");
    }

    @Override
    public void schedulerStarting() {
        logger.info("scheduler正在开启");
    }

    @Override
    public void schedulerShutdown() {
        logger.info("scheduler停止");
    }

    @Override
    public void schedulerShuttingdown() {
        logger.info("scheduler正在停止");
    }

    @Override
    public void schedulingDataCleared() {
        logger.info("Scheduler中的数据被清除");
    }

}
