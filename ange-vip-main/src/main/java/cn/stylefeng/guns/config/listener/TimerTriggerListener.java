package cn.stylefeng.guns.config.listener;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;
import org.slf4j.LoggerFactory;

/**
 * 跟触发器有关的事件包括：触发器被触发，触发器触发失败，以及触发器触发完成（触发器完成后作业任务开始运行）
 */
public class TimerTriggerListener implements TriggerListener {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(TimerTriggerListener.class);

    private String timerTriggerName="";

    public TimerTriggerListener(String timerTriggerName) {
        this.timerTriggerName = timerTriggerName;
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        logger.info(timerTriggerName);
        System.out.println(timerTriggerName);
        return timerTriggerName;
    }

    //触发器触发完成
    //(4) 任务完成时触发
    @Override
    public void triggerComplete(Trigger arg0, JobExecutionContext arg1, Trigger.CompletedExecutionInstruction arg2) {
        // TODO Auto-generated method stub
        logger.info("触发器触发完成");
        System.out.println("触发器触发完成");
    }

    //触发器被触发
    //(1)Trigger被激发 它关联的job即将被运行
    @Override
    public void triggerFired(Trigger arg0, JobExecutionContext arg1) {
        // TODO Auto-generated method stub
        logger.info("Trigger被激发 它关联的job即将被运行");
    }

    //触发器触发失败
    //(3) 当Trigger错过被激发时执行,比如当前时间有很多触发器都需要执行，但是线程池中的有效线程都在工作，
    //那么有的触发器就有可能超时，错过这一轮的触发。
    @Override
    public void triggerMisfired(Trigger arg0) {
        // TODO Auto-generated method stub
        logger.info("触发器触发失败");
    }

    //(2)Trigger被激发 它关联的job即将被运行,先执行(1)，在执行(2) 如果返回TRUE 那么任务job会被终止
    @Override
    public boolean vetoJobExecution(Trigger arg0, JobExecutionContext arg1) {
        // TODO Auto-generated method stub
        return false;
    }

}