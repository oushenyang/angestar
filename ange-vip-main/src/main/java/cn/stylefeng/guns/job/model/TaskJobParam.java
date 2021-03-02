package cn.stylefeng.guns.job.model;
import lombok.Data;
import java.util.Date;

/**
 * <p>
 * 定时任务调度表
 * </p>
 *
 * @author JT
 * @since 2019-12-02
 */
@Data
public class TaskJobParam{
    /**
     * 任务ID
     */
	private Long jobId;
    /**
     * 任务名称
     */
	private String jobName;
    /**
     * 任务组名
     */
	private String jobGroup;
    /**
     * 调用目标字符串
     */
	private String invokeTarget;
    /**
     * cron执行表达式
     */
	private String cronExpression;
    /**
     * 计划执行错误策略（1立即执行 2执行一次 3放弃执行）
     */
	private String misfirePolicy;
    /**
     * 状态（0正常 1禁止）
     */
	private String status;
    /**
     * 创建者
     */
	private String createBy;
    /**
     * 创建时间
     */
	private Date createTime;
    /**
     * 更新者
     */
	private String updateBy;
    /**
     * 更新时间
     */
	private Date updateTime;
    /**
     * 备注信息
     */
	private String remark;

	/**
	 * 是否暂停 1是 2否
	 */
	private Integer suspende;
}
