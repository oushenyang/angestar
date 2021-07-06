package cn.stylefeng.guns.job.controller;


import cn.hutool.core.io.file.FileReader;
import cn.stylefeng.guns.base.consts.ConstantsContext;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.core.schedue.ScheduleUtils;
import cn.stylefeng.guns.job.entity.TaskJob;
import cn.stylefeng.guns.job.model.TaskJobLogParam;
import cn.stylefeng.guns.job.model.TaskJobParam;
import cn.stylefeng.guns.job.service.ITaskJobService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.data.SqlExe;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 定时任务调度表
 * </p>
 *
 * @author JT
 * @since 2019-12-02
 */
//@Validated
@Controller
@RequestMapping("/taskJob")
public class TaskJobController extends BaseController {

    private String PREFIX = "/modular/job/";

    @Autowired
    private ITaskJobService taskJobService;

    @Resource
    private ScheduleUtils scheduleUtils;

    /**
     * 跳转到查看管理员列表的页面
     *
     * @author shenyang.ou
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "job.html";
    }

    /**
     * 新增页面
     *
     * @author shenyang.ou
     * @Date 2020-06-16
     */
    @RequestMapping("/job_add")
    public String add() {
        return PREFIX + "job_add.html";
    }

    /**
     * 新增接口
     *
     * @author shenyang.ou
     * @Date 2020-05-20
     */
    @RequestMapping("/add")
    @ResponseBody
    public ResponseData addItem(TaskJobParam taskJobParam) {
        boolean a = scheduleUtils.validCron(taskJobParam.getCronExpression());
        if (!a){
            return ResponseData.error("Cron表达式不正确");
        }
        this.taskJobService.add(taskJobParam);
        return ResponseData.success();
    }

    @RequestMapping("/job_edit")
    public String edit() {
        return PREFIX + "job_edit.html";
    }

    @RequestMapping("/job_log")
    public String log() {
        return PREFIX + "job_log.html";
    }

    /**
     * 更新接口
     *
     * @author shenyang.ou
     * @Date 2020-05-20
     */
    @RequestMapping("/update")
    @ResponseBody
    public ResponseData update(TaskJobParam taskJobParam) {
        boolean a = scheduleUtils.validCron(taskJobParam.getCronExpression());
        if (!a){
            return ResponseData.error("Cron表达式不正确");
        }
        TaskJob taskJob =this.taskJobService.update(taskJobParam);
        //调用任务接口
        if(taskJob.getStatus().equals("0")){
            scheduleUtils.changeCronJob(taskJob);
        }else if(taskJob.getStatus().equals("1")){
            scheduleUtils.stopRun(taskJob.getJobId(),taskJob.getJobGroup());
        }
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author shenyang.ou
     * @Date 2020-05-20
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(Long jobId) {
        TaskJob taskJob = taskJobService.getById(jobId);
        return ResponseData.success(taskJob);
    }

    /**
     * 查看日志详情接口
     *
     * @author shenyang.ou
     * @Date 2020-05-20
     */
    @RequestMapping("/logDetail")
    @ResponseBody
    public ResponseData logDetail(Long id, String jobKey) {
        FileReader fileReader = new FileReader(ConstantsContext.getLogFileUploadPath() + jobKey + "/" +id + ".txt");
        List<String> result = fileReader.readLines();
        return ResponseData.success(result);
    }



    /**
     * 查询列表
     *
     * @author shenyang.ou
     * @Date 2020-06-16
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(TaskJobParam taskJobParam) {
        //获取分页参数
        Page page = LayuiPageFactory.defaultPage();
        //根据条件查询操作日志
        List<Map<String, Object>> result = taskJobService.findListBySpec(page, taskJobParam);
        page.setRecords(result);
        return LayuiPageFactory.createPageInfo(page);
    }

    /**
     * 查询日志列表
     *
     * @author shenyang.ou
     * @Date 2020-06-16
     */
    @ResponseBody
    @RequestMapping("/logList")
    public LayuiPageInfo logList(TaskJobLogParam taskJobLogParam) {
        //获取分页参数
        Page page = LayuiPageFactory.defaultPage();
        //根据条件查询操作日志
        List<Map<String, Object>> result = taskJobService.findLogListBySpec(page, taskJobLogParam);
        page.setRecords(result);
        return LayuiPageFactory.createPageInfo(page);
    }

    /**
     * 清空日志
     *
     * @author shenyang.ou
     * @Date 2018/12/23 5:51 PM
     */
    @RequestMapping("/delLog")
    @ResponseBody
    public Object delLog(TaskJobLogParam taskJobLogParam) {
        SqlExe.delete("delete from sys_task_job_log where jobId = " + taskJobLogParam.getJobId() , null);
        return SUCCESS_TIP;
    }


    /**
     * 功能操作 0终止 - 1执行 - 2禁用/激活
     */
    @PostMapping("/runJob")
    @ResponseBody
    @Transactional(rollbackFor = { Exception.class })
    public ResponseData runJob(Long jobId, Integer type, Integer suspende) {
        try {
            TaskJob job = taskJobService.findJobById(jobId);
            if(type==0){
                job.setJobId(jobId);
                job.setStatus("1");
                taskJobService.updateById(job);
                //调用接口
                scheduleUtils.stopRun(job.getJobId(),job.getJobGroup());
            }else if(type==1){
                //调用接口
                scheduleUtils.goRun(job);
            }else if(type==2){
                //变更状态
                job.setJobId(jobId);
                job.setSuspende(suspende);
                taskJobService.updateById(job);
                //调用接口
                scheduleUtils.changeStatus(job);
            }else if(type==3){//激活
                job.setJobId(jobId);
                job.setStatus("0");
                job.setSuspende(2);
                taskJobService.updateById(job);
                //调用接口
                scheduleUtils.changeCronJob(job);
            }
            return ResponseData.success();
        }catch (Exception e){
            return ResponseData.error(e.getMessage());
        }
    }

}
