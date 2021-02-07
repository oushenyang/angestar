package cn.stylefeng.guns.job.service;

import cn.stylefeng.guns.job.entity.TaskJob;
import cn.stylefeng.guns.job.entity.TaskJobLog;
import cn.stylefeng.guns.job.model.TaskJobLogParam;
import cn.stylefeng.guns.job.model.TaskJobParam;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 定时任务调度表 服务类
 * </p>
 *
 * @author JT
 * @since 2019-12-02
 */
@Service
public interface ITaskJobService extends IService<TaskJob>{

    /**
     * JOB 列表
     */
//    void findForPage(Page<FormMap> toPage, Map map);

    TaskJob findJobById(Long jobId);

    List<TaskJob> selectJobAll();

    void insertCronLog(TaskJobLog taskJobLog);

    void deleteLog();

    List<Map<String, Object>> findListBySpec(Page page, TaskJobParam taskJobParam);

    List<Map<String, Object>> findLogListBySpec(Page page, TaskJobLogParam taskJobLogParam);

    TaskJob update(TaskJobParam taskJobParam);

    void add(TaskJobParam taskJobParam);
}
