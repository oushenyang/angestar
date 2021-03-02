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
 * 代理权限
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-05-20
 */
@TableName("ag_agent_power")
public class AgentPower implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 代理权限id
     */
    @TableId(value = "agent_power_id", type = IdType.ID_WORKER)
    private Long agentPowerId;

    /**
     * 代理应用id
     */
    @TableField("agent_app_id")
    private Long agentAppId;

    /**
     * 应用id
     */
    @TableField("app_id")
    private Long appId;

    /**
     * 卡密生成 0-否；1-是；
     */
    @TableField("card_create")
    private Boolean cardCreate;

    /**
     * 卡密禁用 0-否；1-是；
     */
    @TableField("card_disable")
    private Boolean cardDisable;

    /**
     * 卡密查看 0-否；1-是；
     */
    @TableField("card_look")
    private Boolean cardLook;

    /**
     * 卡密数据 0-否；1-是；
     */
    @TableField("card_data")
    private Boolean cardData;

    /**
     * 卡密加时 0-否；1-是；
     */
    @TableField("card_time")
    private Boolean cardTime;

    /**
     * 卡密删除 0-否；1-是；
     */
    @TableField("card_delete")
    private Boolean cardDelete;

    /**
     * 卡密编辑 0-否；1-是；
     */
    @TableField("card_edit")
    private Boolean cardEdit;

    /**
     * 卡密配置 0-否；1-是；
     */
    @TableField("card_config")
    private Boolean cardConfig;

    /**
     * 卡密解绑 0-否；1-是；
     */
    @TableField("card_relieve")
    private Boolean cardRelieve;

    /**
     * 充值卡生成 0-否；1-是；
     */
    @TableField("account_create")
    private Boolean accountCreate;

    /**
     * 账号禁用 0-否；1-是；
     */
    @TableField("account_disable")
    private Boolean accountDisable;

    /**
     * 修改密码 0-否；1-是；
     */
    @TableField("account_edit_password")
    private Boolean accountEditPassword;

    /**
     * 账号删除 0-否；1-是；
     */
    @TableField("account_delete")
    private Boolean accountDelete;

    /**
     * 账号加时 0-否；1-是；
     */
    @TableField("account_time")
    private Boolean accountTime;

    /**
     * 账号数据 0-否；1-是；
     */
    @TableField("account_data")
    private Boolean accountData;

    /**
     * 查询用户 0-否；1-是；
     */
    @TableField("account_query")
    private Boolean accountQuery;

    /**
     * 乐观锁
     */
    @TableField("revision")
    private Integer revision;

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


    public Long getAgentPowerId() {
        return agentPowerId;
    }

    public void setAgentPowerId(Long agentPowerId) {
        this.agentPowerId = agentPowerId;
    }

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

    public Boolean getCardCreate() {
        return cardCreate;
    }

    public void setCardCreate(Boolean cardCreate) {
        this.cardCreate = cardCreate;
    }

    public Boolean getCardDisable() {
        return cardDisable;
    }

    public void setCardDisable(Boolean cardDisable) {
        this.cardDisable = cardDisable;
    }

    public Boolean getCardLook() {
        return cardLook;
    }

    public void setCardLook(Boolean cardLook) {
        this.cardLook = cardLook;
    }

    public Boolean getCardData() {
        return cardData;
    }

    public void setCardData(Boolean cardData) {
        this.cardData = cardData;
    }

    public Boolean getCardTime() {
        return cardTime;
    }

    public void setCardTime(Boolean cardTime) {
        this.cardTime = cardTime;
    }

    public Boolean getCardDelete() {
        return cardDelete;
    }

    public void setCardDelete(Boolean cardDelete) {
        this.cardDelete = cardDelete;
    }

    public Boolean getCardEdit() {
        return cardEdit;
    }

    public void setCardEdit(Boolean cardEdit) {
        this.cardEdit = cardEdit;
    }

    public Boolean getCardConfig() {
        return cardConfig;
    }

    public void setCardConfig(Boolean cardConfig) {
        this.cardConfig = cardConfig;
    }

    public Boolean getCardRelieve() {
        return cardRelieve;
    }

    public void setCardRelieve(Boolean cardRelieve) {
        this.cardRelieve = cardRelieve;
    }

    public Boolean getAccountCreate() {
        return accountCreate;
    }

    public void setAccountCreate(Boolean accountCreate) {
        this.accountCreate = accountCreate;
    }

    public Boolean getAccountDisable() {
        return accountDisable;
    }

    public void setAccountDisable(Boolean accountDisable) {
        this.accountDisable = accountDisable;
    }

    public Boolean getAccountEditPassword() {
        return accountEditPassword;
    }

    public void setAccountEditPassword(Boolean accountEditPassword) {
        this.accountEditPassword = accountEditPassword;
    }

    public Boolean getAccountDelete() {
        return accountDelete;
    }

    public void setAccountDelete(Boolean accountDelete) {
        this.accountDelete = accountDelete;
    }

    public Boolean getAccountTime() {
        return accountTime;
    }

    public void setAccountTime(Boolean accountTime) {
        this.accountTime = accountTime;
    }

    public Boolean getAccountData() {
        return accountData;
    }

    public void setAccountData(Boolean accountData) {
        this.accountData = accountData;
    }

    public Boolean getAccountQuery() {
        return accountQuery;
    }

    public void setAccountQuery(Boolean accountQuery) {
        this.accountQuery = accountQuery;
    }

    public Integer getRevision() {
        return revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
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
        return "AgentPower{" +
        "agentPowerId=" + agentPowerId +
        ", agentAppId=" + agentAppId +
        ", appId=" + appId +
        ", cardCreate=" + cardCreate +
        ", cardDisable=" + cardDisable +
        ", cardLook=" + cardLook +
        ", cardData=" + cardData +
        ", cardTime=" + cardTime +
        ", cardDelete=" + cardDelete +
        ", cardEdit=" + cardEdit +
        ", cardConfig=" + cardConfig +
        ", cardRelieve=" + cardRelieve +
        ", accountCreate=" + accountCreate +
        ", accountDisable=" + accountDisable +
        ", accountEditPassword=" + accountEditPassword +
        ", accountDelete=" + accountDelete +
        ", accountTime=" + accountTime +
        ", accountData=" + accountData +
        ", accountQuery=" + accountQuery +
        ", revision=" + revision +
        ", createUser=" + createUser +
        ", createTime=" + createTime +
        ", updateUser=" + updateUser +
        ", updateTime=" + updateTime +
        "}";
    }
}
