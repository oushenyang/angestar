package cn.stylefeng.guns.job.model;



import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
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
public class TaskJobLogParam {

	/**
	 * id
	 */
	private Long id;

    /**
     * 任务ID
     */
	private Long jobId;

    /**
     * jobKey
     */
	private String jobKey;
    /**
     * 0-成功；1-失败
     */
	private Integer status;
    /**
     * 信息
     */
	private String msg;
    /**
     * filesUrl
     */
	private String filesUrl;
    /**
     * 最近更新时间
     */
	private Date updateTime;
    /**
     * 创建者
     */
	private Date creatTime;
	private String beginTime;
	private String endTime;
}
