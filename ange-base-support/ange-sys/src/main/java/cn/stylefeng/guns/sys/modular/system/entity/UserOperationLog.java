package cn.stylefeng.guns.sys.modular.system.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户操作日志
 * </p>
 *
 * @author stylefeng
 * @since 2019-04-01
 */
@TableName("user_operation_log")
public class UserOperationLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "user_operation_log_id", type = IdType.ID_WORKER)
    private Long userOperationLogId;

    /**
     * 日志类型：1-应用相关；2-卡密相关；3-账号相关；4-代理相关；5-试用相关；6-数据相关；7-接口相关
     */
    @TableField("log_type")
    private Integer logType;

    /**
     * 日志名称
     */
    @TableField("log_name")
    private String logName;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 消息
     */
    @TableField("message")
    private String message;

    /**
     * 开发者
     */
    @TableField("developer_user")
    private Long developerUser;

    public Long getUserOperationLogId() {
        return userOperationLogId;
    }

    public void setUserOperationLogId(Long userOperationLogId) {
        this.userOperationLogId = userOperationLogId;
    }

    public Integer getLogType() {
        return logType;
    }

    public void setLogType(Integer logType) {
        this.logType = logType;
    }

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getDeveloperUser() {
        return developerUser;
    }

    public void setDeveloperUser(Long developerUser) {
        this.developerUser = developerUser;
    }

    @Override
    public String toString() {
        return "UserOperationLog{" +
                "userOperationLogId=" + userOperationLogId +
                ", logType=" + logType +
                ", logName='" + logName + '\'' +
                ", userId=" + userId +
                ", createTime=" + createTime +
                ", message='" + message + '\'' +
                ", developerUser=" + developerUser +
                '}';
    }
}
