package cn.stylefeng.guns.modular.agent.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 代理审核表
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-12-09
 */
@TableName("ag_agent_examine")
public class AgentExamine implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 代理审核id
     */
    @TableId(value = "agent_examine_id", type = IdType.ID_WORKER)
    private Long agentExamineId;

    /**
     * 应用id
     */
    @TableField("app_id")
    private Long appId;

    /**
     * 开发者用户id
     */
    @TableField("developer_user_id")
    private Long developerUserId;

    /**
     * 代理用户id
     */
    @TableField("agent_user_id")
    private Long agentUserId;

    /**
     * 代理用户名称
     */
    @TableField("agent_user_name")
    private String agentUserName;

    /**
     * 代理用户账号
     */
    @TableField("agent_user_account")
    private String agentUserAccount;

    /**
     * 代理等级
     */
    @TableField("agent_grade")
    private Integer agentGrade;

    /**
     * 父用户id
     */
    @TableField("pid")
    private Long pid;

    /**
     * 父级ids
     */
    @TableField("pids")
    private String pids;

    /**
     * 申请理由
     */
    @TableField("apply_reason")
    private String applyReason;

    /**
     * 申请类型：1-申请代理；2-邀请代理
     */
    @TableField("apply_type")
    private Integer applyType;

    /**
     * 审核状态：1-等待开发者审核；2-等待代理审核；3-开发者拒绝；4-代理拒绝；5-代理成功
     */
    @TableField("examine_status")
    private Integer examineStatus;

    /**
     * 审核时间
     */
    @TableField("examine_time")
    private Date examineTime;

    /**
     * 创建人
     */
    @TableField(value = "create_user", fill = FieldFill.INSERT)
    private Long createUser;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新人
     */
    @TableField(value = "update_user", fill = FieldFill.UPDATE)
    private Long updateUser;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;


    public Long getAgentExamineId() {
        return agentExamineId;
    }

    public void setAgentExamineId(Long agentExamineId) {
        this.agentExamineId = agentExamineId;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public Long getDeveloperUserId() {
        return developerUserId;
    }

    public void setDeveloperUserId(Long developerUserId) {
        this.developerUserId = developerUserId;
    }

    public Long getAgentUserId() {
        return agentUserId;
    }

    public void setAgentUserId(Long agentUserId) {
        this.agentUserId = agentUserId;
    }

    public String getAgentUserName() {
        return agentUserName;
    }

    public void setAgentUserName(String agentUserName) {
        this.agentUserName = agentUserName;
    }

    public String getAgentUserAccount() {
        return agentUserAccount;
    }

    public void setAgentUserAccount(String agentUserAccount) {
        this.agentUserAccount = agentUserAccount;
    }

    public Integer getAgentGrade() {
        return agentGrade;
    }

    public void setAgentGrade(Integer agentGrade) {
        this.agentGrade = agentGrade;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getPids() {
        return pids;
    }

    public void setPids(String pids) {
        this.pids = pids;
    }

    public String getApplyReason() {
        return applyReason;
    }

    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }

    public Integer getApplyType() {
        return applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }

    public Integer getExamineStatus() {
        return examineStatus;
    }

    public void setExamineStatus(Integer examineStatus) {
        this.examineStatus = examineStatus;
    }

    public Date getExamineTime() {
        return examineTime;
    }

    public void setExamineTime(Date examineTime) {
        this.examineTime = examineTime;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "AgentExamine{" +
        "agentExamineId=" + agentExamineId +
        ", appId=" + appId +
        ", developerUserId=" + developerUserId +
        ", agentUserId=" + agentUserId +
        ", agentUserName=" + agentUserName +
        ", agentUserAccount=" + agentUserAccount +
        ", agentGrade=" + agentGrade +
        ", pid=" + pid +
        ", pids=" + pids +
        ", applyReason=" + applyReason +
        ", applyType=" + applyType +
        ", examineStatus=" + examineStatus +
        ", examineTime=" + examineTime +
        ", createUser=" + createUser +
        ", createTime=" + createTime +
        ", updateUser=" + updateUser +
        ", updateTime=" + updateTime +
        "}";
    }
}
