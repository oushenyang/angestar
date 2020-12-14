package cn.stylefeng.guns.modular.agent.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 代理软件表 
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-05-20
 */
@TableName("ag_agent_app")
public class AgentApp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 代理应用id
     */
    @TableId(value = "agent_app_id", type = IdType.ID_WORKER)
    private Long agentAppId;

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
    @TableField("rose")
    private Boolean rose;

    /**
     * 代理等级
     */
    @TableField("agent_grade")
    private Integer agentGrade;

    /**
     * 父代理应用id
     */
    @TableField("pid")
    private Long pid;

    /**
     * 父级ids
     */
    @TableField("pids")
    private String pids;

    /**
     * 余额
     */
    @TableField("balance")
    private BigDecimal balance;

    /**
     * 状态 0-正常；1-冻结
     */
    @TableField("status")
    private Integer status;

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


    public Long getAgentAppId() {
        return agentAppId;
    }

    public void setAgentAppId(Long agentAppId) {
        this.agentAppId = agentAppId;
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

    public Boolean getRose() {
        return rose;
    }

    public void setRose(Boolean rose) {
        this.rose = rose;
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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
        return "AgentApp{" +
        "agentAppId=" + agentAppId +
        ", appId=" + appId +
        ", developerUserId=" + developerUserId +
        ", agentUserId=" + agentUserId +
        ", agentUserName=" + agentUserName +
        ", agentUserAccount=" + agentUserAccount +
        ", rose=" + rose +
        ", agentGrade=" + agentGrade +
        ", pid=" + pid +
        ", pids=" + pids +
        ", balance=" + balance +
        ", status=" + status +
        ", createUser=" + createUser +
        ", createTime=" + createTime +
        ", updateUser=" + updateUser +
        ", updateTime=" + updateTime +
        "}";
    }
}
