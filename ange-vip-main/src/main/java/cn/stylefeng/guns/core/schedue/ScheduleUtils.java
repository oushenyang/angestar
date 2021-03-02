package cn.stylefeng.guns.core.schedue;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.file.FileWriter;
import cn.stylefeng.guns.base.consts.ConstantsContext;
import cn.stylefeng.guns.config.listener.TimerJobListener;
import cn.stylefeng.guns.config.listener.TimerSchedulerListener;
import cn.stylefeng.guns.config.listener.TimerTriggerListener;
import cn.stylefeng.guns.job.controller.ScheduleConstants;
import cn.stylefeng.guns.job.entity.TaskJob;
import cn.stylefeng.guns.job.entity.TaskJobLog;
import cn.stylefeng.guns.job.service.ITaskJobService;
import cn.stylefeng.guns.sys.modular.system.service.LoginLogService;
import cn.stylefeng.guns.sys.modular.system.service.OperationLogService;
import cn.stylefeng.roses.core.util.SpringContextHolder;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Component
public class ScheduleUtils {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private static ITaskJobService jobService;

    public boolean validCron(String cronVal){
        return CronExpression.isValidExpression(cronVal);
    }

    public void  changeCronJob(TaskJob taskJob) {
        Long jobId = taskJob.getJobId();
        String jobGroup = taskJob.getJobGroup();
        // 判断是否存在
        JobKey jobKey = getJobKey(jobId, jobGroup);
        try {
            if (scheduler.checkExists(jobKey)){
                // 防止创建时存在数据问题 先移除，然后在执行创建操作
                scheduler.deleteJob(jobKey);
            }
            startjob(taskJob);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goRun(TaskJob taskJob) throws SchedulerException {
        // 参数
        JobDataMap dataMap = new JobDataMap();
        dataMap.put(ScheduleConstants.TASK_PROPERTIES, taskJob);
        scheduler.triggerJob(getJobKey(taskJob.getJobId(), taskJob.getJobGroup()), dataMap);
    }


    public void stopRun(Long jobId,String jobGroup){
        try {
            scheduler.deleteJob(getJobKey(jobId, jobGroup));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }


    public void changeStatus(TaskJob taskJob) throws SchedulerException {
        Integer suspende = taskJob.getSuspende();
        if (ScheduleConstants.Suspende.NORMAL.getValue()==suspende){
            resumeJob(taskJob);
        }
        else if (ScheduleConstants.Suspende.PAUSE.getValue()==suspende){
            pauseJob(taskJob);
        }
    }

    //暂停任务 - 禁用
    public void pauseJob(TaskJob job) throws SchedulerException{
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        scheduler.pauseJob(getJobKey(jobId, jobGroup));
        scheduler.pauseTrigger(getTriggerKey(jobId, jobGroup));
    }


    //恢复任务  - 激活
    public void resumeJob(TaskJob job) throws SchedulerException{
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        scheduler.resumeJob(getJobKey(jobId, jobGroup));
        scheduler.resumeTrigger(getTriggerKey(jobId, jobGroup));
    }



    public void startjob(TaskJob taskJob) throws Exception {
        // 定义jobdetail
        Class jobclass = Class.forName(ScheduleConstants.RUNPACKAGE+taskJob.getInvokeTarget());
        JobDetail job = JobBuilder.newJob(jobclass).withIdentity(getJobKey(taskJob.getJobId(), taskJob.getJobGroup())).build();
        // 放入参数，运行时的方法可以获取
        job.getJobDataMap().put(ScheduleConstants.TASK_PROPERTIES, job);

        // 表达式调度构建器
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(taskJob.getCronExpression());
        cronScheduleBuilder = handleCronScheduleMisfirePolicy(taskJob, cronScheduleBuilder);

        // 按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(taskJob.getJobId(), taskJob.getJobGroup()))
                .withSchedule(cronScheduleBuilder).build();

        // 判断是否存在
        if (scheduler.checkExists(getJobKey(taskJob.getJobId(), taskJob.getJobGroup()))){
            // 防止创建时存在数据问题 先移除，然后在执行创建操作
            scheduler.deleteJob(getJobKey(taskJob.getJobId(), taskJob.getJobGroup()));
        }
        scheduler.scheduleJob(job, trigger);//定义的JobDetail和trigger注册到scheduler里

        // 暂停任务
        if (taskJob.getSuspende()==ScheduleConstants.Suspende.PAUSE.getValue())
        {
            scheduler.pauseJob(getJobKey(taskJob.getJobId(), taskJob.getJobGroup()));
            scheduler.pauseTrigger(getTriggerKey(taskJob.getJobId(), taskJob.getJobGroup()));
        }

        // 禁止任务
        if (taskJob.getStatus().equals(ScheduleConstants.Status.PAUSE.getValue()))
        {
            scheduler.deleteJob(getJobKey(taskJob.getJobId(), taskJob.getJobGroup()));
        }

        // 创建并注册一个全局的Job Listener
        scheduler.getListenerManager().addJobListener(new TimerJobListener(), GroupMatcher.jobGroupEquals(taskJob.getJobGroup()));
        // 把作业和触发器注册到任务调度中
        scheduler.getListenerManager().addTriggerListener(new TimerTriggerListener("TIMERTRIGGERLISTENER"), GroupMatcher.groupEquals(taskJob.getJobGroup()));
        // 创建SchedulerListener
        scheduler.getListenerManager().addSchedulerListener(new TimerSchedulerListener());
        // 移除对应的SchedulerListener
        scheduler.getListenerManager().removeSchedulerListener(new TimerSchedulerListener());
    }



    /**
     * 构建任务键对象
     */
    public JobKey getJobKey(Long jobId, String jobGroup){
        return JobKey.jobKey(ScheduleConstants.TASK_CLASS_NAME + jobId, jobGroup);
    }

    /**
     * 构建任务触发对象
     */
    public TriggerKey getTriggerKey(Long jobId, String jobGroup)
    {
        return TriggerKey.triggerKey(ScheduleConstants.TASK_CLASS_NAME + jobId, jobGroup);
    }


    /**
     * 设置定时任务策略
     */
    public CronScheduleBuilder handleCronScheduleMisfirePolicy(TaskJob job, CronScheduleBuilder cb) throws Exception {
        switch (job.getMisfirePolicy())
        {
            case ScheduleConstants.MISFIRE_DEFAULT:
                return cb;
            case ScheduleConstants.MISFIRE_IGNORE_MISFIRES:
                //以错过的第一个频率时间立刻开始执行
                //——重做错过的所有频率周期后
                //——当下一次触发频率发生时间大于当前时间后，再按照正常的Cron频率依次执行
                return cb.withMisfireHandlingInstructionIgnoreMisfires();
            case ScheduleConstants.MISFIRE_FIRE_AND_PROCEED:
                //以当前时间为触发频率立刻触发一次执行
                //——然后按照Cron频率依次执行
                return cb.withMisfireHandlingInstructionFireAndProceed();
            case ScheduleConstants.MISFIRE_DO_NOTHING:
                //不触发立即执行
                //——等待下次Cron触发频率到达时刻开始按照Cron频率依次执行
                return cb.withMisfireHandlingInstructionDoNothing();
            default:
                throw new Exception("定时任务运行出错，没有 "+job.getMisfirePolicy()+" 的执行策略!");
        }
    }

    public static Long witerlog(JobExecutionContext context,Integer status,String msg){
        String titlename = context.getJobDetail().getKey().getName();
        Long id = IdWorker.getId();
        //写入数据
        ITaskJobService jobService = null;
        try {
            jobService = (ITaskJobService)
                    SpringContextHolder.getBean(Class.forName("cn.stylefeng.guns.job.service.ITaskJobService"));
            TaskJobLog taskJobLog = new TaskJobLog();
            taskJobLog.setId(id);
            taskJobLog.setJobId(Long.valueOf(titlename.substring(ScheduleConstants.TASK_CLASS_NAME.length(),titlename.length())));
            taskJobLog.setJobKey(titlename);
            taskJobLog.setStatus(status);
            taskJobLog.setMsg(msg);
            taskJobLog.setUpdateTime(new Date());
            taskJobLog.setCreatTime(new Date());
            jobService.insertCronLog(taskJobLog);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return id;
    }

    public static void witerListLogToTxt(JobExecutionContext context, List<Object> list, Long id){
        if (CollectionUtil.isNotEmpty(list)){
            String titlename = context.getJobDetail().getKey().getName();
            //写入数据
            FileWriter writer = new FileWriter(ConstantsContext.getLogFileUploadPath() + titlename + "/" +id + ".txt");
            writer.writeLines(list);
        }
    }

    public static void delLog(){
        ITaskJobService jobService = null;
        OperationLogService operationLogService = null;
        LoginLogService loginLogService = null;
        try {
            jobService = (ITaskJobService)
                    SpringContextHolder.getBean(Class.forName("cn.stylefeng.guns.job.service.ITaskJobService"));
            operationLogService = (OperationLogService)
                    SpringContextHolder.getBean(Class.forName("cn.stylefeng.guns.sys.modular.system.service.OperationLogService"));
            loginLogService = (LoginLogService)
                    SpringContextHolder.getBean(Class.forName("cn.stylefeng.guns.sys.modular.system.service.LoginLogService"));
            jobService.deleteLog();
            operationLogService.deleteLog();
            loginLogService.deleteLog();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws ParseException, ClassNotFoundException, SchedulerException {
//        CronExpression cronExpression = new CronExpression("30 46 16 * * ?");
        System.out.println( CronExpression.isValidExpression("30 46 16 * * ?") );
    }



}
