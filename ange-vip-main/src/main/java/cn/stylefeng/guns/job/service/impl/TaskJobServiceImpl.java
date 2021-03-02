package cn.stylefeng.guns.job.service.impl;

import cn.stylefeng.guns.job.entity.TaskJob;
import cn.stylefeng.guns.job.entity.TaskJobLog;
import cn.stylefeng.guns.job.mapper.TaskJobMapper;
import cn.stylefeng.guns.job.model.TaskJobLogParam;
import cn.stylefeng.guns.job.model.TaskJobParam;
import cn.stylefeng.guns.job.service.ITaskJobService;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 定时任务调度表 服务实现类
 * </p>
 *
 * @author JT
 * @since 2019-12-02
 */
@Service
public class TaskJobServiceImpl extends ServiceImpl<TaskJobMapper, TaskJob> implements ITaskJobService {

    @Resource
    TaskJobMapper jobMapper;

//    @Override
//    public void findForPage(Page<FormMap> toPage, Map map) {
//        List<FormMap> listDtos = jobMapper.findForPage(toPage, map);
//        toPage.setRecords(listDtos);
//
//    }


    @Override
    public TaskJob findJobById(Long jobId) {
        return jobMapper.findJobById(jobId);
    }

    //初始化 任务
    @Override
    public List<TaskJob> selectJobAll() {
        List<TaskJob> jobList = jobMapper.selectJobAll();
        return jobList;
    }

    @Override
    public void insertCronLog(TaskJobLog taskJobLog) {
        jobMapper.insertCronLog(taskJobLog);
    }

    @Override
    public void deleteLog() {
        jobMapper.deleteLog();
    }

    @Override
    public List<Map<String, Object>> findListBySpec(Page page, TaskJobParam taskJobParam) {
        return jobMapper.findListBySpec(page,taskJobParam);
    }

    @Override
    public List<Map<String, Object>> findLogListBySpec(Page page, TaskJobLogParam taskJobLogParam) {
        return jobMapper.findLogListBySpec(page,taskJobLogParam);
    }

    @Override
    public TaskJob update(TaskJobParam taskJobParam) {
        TaskJob oldEntity = getOldEntity(taskJobParam);
        TaskJob newEntity = getEntity(taskJobParam);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
        return newEntity;
    }

    @Override
    public void add(TaskJobParam taskJobParam) {
        TaskJob entity = getEntity(taskJobParam);
        this.save(entity);
    }

    private TaskJob getOldEntity(TaskJobParam param) {
        return this.getById(param.getJobId());
    }

    private TaskJob getEntity(TaskJobParam param) {
        TaskJob entity = new TaskJob();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }


}
