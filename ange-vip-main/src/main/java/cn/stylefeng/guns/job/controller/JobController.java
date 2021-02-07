package cn.stylefeng.guns.job.controller;

import cn.stylefeng.guns.core.schedue.ScheduleUtils;
import cn.stylefeng.guns.job.entity.TaskJob;
import cn.stylefeng.guns.job.service.ITaskJobService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Validated
@RequestMapping(value = "/job")
@RestController
public class JobController {

    @Resource
    ITaskJobService jobService;

    @Resource
    ScheduleUtils scheduleUtils;




//    初始化任务
    @PostConstruct
    public void init() throws Exception {
        List<TaskJob> jobList = jobService.selectJobAll();
        for (TaskJob job : jobList){
            scheduleUtils.changeCronJob(job);
        }
    }
}
