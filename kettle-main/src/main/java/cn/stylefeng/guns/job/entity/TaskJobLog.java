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
@TableName("sys_task_job_log")
public class TaskJobLog extends Model<TaskJobLog> {

	/**
	 * id
	 */
	@TableId(value="id", type = IdType.ID_WORKER)
	private Long id;

    /**
     * 任务ID
     */
	@TableId(value="jobId")
	private Long jobId;

    /**
     * jobKey
     */
	@TableField("jobKey")
	private String jobKey;
    /**
     * 0-成功；1-失败
     */
	@TableField("status")
	private Integer status;
    /**
     * 信息
     */
	@TableField("msg")
	private String msg;
    /**
     * filesUrl
     */
	@TableField("filesUrl")
	private String filesUrl;
    /**
     * 最近更新时间
     */
	@TableField("updateTime")
	private Date updateTime;
    /**
     * 创建者
     */
	@TableField("creatTime")
	private Date creatTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public String getJobKey() {
		return jobKey;
	}

	public void setJobKey(String jobKey) {
		this.jobKey = jobKey;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getFilesUrl() {
		return filesUrl;
	}

	public void setFilesUrl(String filesUrl) {
		this.filesUrl = filesUrl;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}
}
