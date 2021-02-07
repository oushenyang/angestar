package cn.stylefeng.guns.job.mapper;

import cn.stylefeng.guns.job.entity.TaskJob;
import cn.stylefeng.guns.job.entity.TaskJobLog;
import cn.stylefeng.guns.job.model.TaskJobLogParam;
import cn.stylefeng.guns.job.model.TaskJobParam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 定时任务调度表 Mapper 接口
 * </p>
 *
 * @author JT
 * @since 2019-12-02
 */
public interface TaskJobMapper extends BaseMapper<TaskJob> {

//    List<FormMap> findForPage(Page<FormMap> toPage, @Param("map") Map map);

    List<TaskJob> selectJobAll();

    TaskJob findJobById(Long jobId);

    int selectCronLogCount(@Param("map") Map map);

    void insertCronLog(@Param("map") TaskJobLog taskJobLog);

    void udateCronLog(@Param("map") Map map);

    List<Map<String, Object>> findListBySpec(@Param("page") Page page, @Param("param") TaskJobParam taskJobParam);

    List<Map<String, Object>> findLogListBySpec(@Param("page") Page page, @Param("param") TaskJobLogParam taskJobLogParam);


    void deleteLog();
}