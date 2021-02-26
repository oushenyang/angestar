package cn.stylefeng.guns.job.entity;



import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 定时任务调度表
 * </p>
 *
 * @author JT
 * @since 2019-12-02
 */
@TableName("sys_task_job")
public class TaskJob extends Model<TaskJob> {

    private static final long serialVersionUID = 1L;

    /**
     * 任务ID
     */
	@TableId(value="job_id", type = IdType.ID_WORKER)
	private Long jobId;
    /**
     * 任务名称
     */
	@TableField("job_name")
	private String jobName;
    /**
     * 任务组名
     */
	@TableField("job_group")
	private String jobGroup;
    /**
     * 调用目标字符串
     */
	@TableField("invoke_target")
	private String invokeTarget;
    /**
     * cron执行表达式
     */
	@TableField("cron_expression")
	private String cronExpression;
    /**
     * 计划执行错误策略（1立即执行 2执行一次 3放弃执行）
     */
	@TableField("misfire_policy")
	private String misfirePolicy;
    /**
     * 状态（0正常 1禁止）
     */
	private String status;
    /**
     * 创建者
     */
	@TableField("create_by")
	private String createBy;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;
    /**
     * 更新者
     */
	@TableField("update_by")
	private String updateBy;
    /**
     * 更新时间
     */
	@TableField("update_time")
	private Date updateTime;
    /**
     * 备注信息
     */
	private String remark;

	/**
	 * 是否暂停 1是 2否
	 */
	@TableField("suspende")
	private Integer suspende;

	public Integer getSuspende() {
		return suspende;
	}

	public void setSuspende(Integer suspende) {
		this.suspende = suspende;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getInvokeTarget() {
		return invokeTarget;
	}

	public void setInvokeTarget(String invokeTarget) {
		this.invokeTarget = invokeTarget;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getMisfirePolicy() {
		return misfirePolicy;
	}

	public void setMisfirePolicy(String misfirePolicy) {
		this.misfirePolicy = misfirePolicy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	protected Serializable pkVal() {
		return this.jobId;
	}

	@Override
	public String toString() {
		return
			"jobId=" + jobId +
			", jobName=" + jobName +
			", jobGroup=" + jobGroup +
			", invokeTarget=" + invokeTarget +
			", cronExpression=" + cronExpression +
			", misfirePolicy=" + misfirePolicy +
			", status=" + status +
			", createBy=" + createBy +
			", createTime=" + createTime +
			", updateBy=" + updateBy +
			", updateTime=" + updateTime +
			", remark=" + remark ;
	}

	public String toString1() {
		return
				"jobId=" + jobId +
						"&jobName=" + jobName +
						"&jobGroup=" + jobGroup +
						"&invokeTarget=" + invokeTarget +
						"&cronExpression=" + cronExpression +
						"&misfirePolicy=" + misfirePolicy +
						"&status=" + status +
						"&createBy=" + createBy +
						"&createTime=" + createTime +
						"&updateBy=" + updateBy +
						"&updateTime=" + updateTime +
						"&remark=" + remark ;
	}
}
