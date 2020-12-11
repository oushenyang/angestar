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
 * 代理购卡记录
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-12-11
 */
@TableName("ag_agent_buy_card")
public class AgentBuyCard implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 代理审核id
     */
    @TableId(value = "agent_buy_card_id", type = IdType.ID_WORKER)
    private Long agentBuyCardId;

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
     * 代理价格
     */
    @TableField("agent_price")
    private BigDecimal agentPrice;

    /**
     * 购卡类型：1-一级代理充值；2-一级代理购卡；3-二级代理充值；4-二级代理购卡返差价
     */
    @TableField("buy_card_type")
    private Integer buyCardType;

    /**
     * 购买数量
     */
    @TableField("buy_num")
    private Integer buyNum;

    /**
     * 卡类Id
     */
    @TableField("card_type_id")
    private Long cardTypeId;

    /**
     * 卡密类型：0-单码卡密；1-通用卡密；2-注册卡密
     */
    @TableField("card_type")
    private Integer cardType;

    /**
     * 金额
     */
    @TableField("amount")
    private BigDecimal amount;

    /**
     * 审核时间
     */
    @TableField("examine_time")
    private Date examineTime;

    /**
     * 明细
     */
    @TableField("detailed")
    private String detailed;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

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


    public Long getAgentBuyCardId() {
        return agentBuyCardId;
    }

    public void setAgentBuyCardId(Long agentBuyCardId) {
        this.agentBuyCardId = agentBuyCardId;
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

    public BigDecimal getAgentPrice() {
        return agentPrice;
    }

    public void setAgentPrice(BigDecimal agentPrice) {
        this.agentPrice = agentPrice;
    }

    public Integer getBuyCardType() {
        return buyCardType;
    }

    public void setBuyCardType(Integer buyCardType) {
        this.buyCardType = buyCardType;
    }

    public Integer getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(Integer buyNum) {
        this.buyNum = buyNum;
    }

    public Long getCardTypeId() {
        return cardTypeId;
    }

    public void setCardTypeId(Long cardTypeId) {
        this.cardTypeId = cardTypeId;
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getExamineTime() {
        return examineTime;
    }

    public void setExamineTime(Date examineTime) {
        this.examineTime = examineTime;
    }

    public String getDetailed() {
        return detailed;
    }

    public void setDetailed(String detailed) {
        this.detailed = detailed;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        return "AgentBuyCard{" +
        "agentBuyCardId=" + agentBuyCardId +
        ", appId=" + appId +
        ", developerUserId=" + developerUserId +
        ", agentUserId=" + agentUserId +
        ", agentUserName=" + agentUserName +
        ", agentUserAccount=" + agentUserAccount +
        ", agentGrade=" + agentGrade +
        ", agentPrice=" + agentPrice +
        ", buyCardType=" + buyCardType +
        ", buyNum=" + buyNum +
        ", cardTypeId=" + cardTypeId +
        ", cardType=" + cardType +
        ", amount=" + amount +
        ", examineTime=" + examineTime +
        ", detailed=" + detailed +
        ", remark=" + remark +
        ", createUser=" + createUser +
        ", createTime=" + createTime +
        ", updateUser=" + updateUser +
        ", updateTime=" + updateTime +
        "}";
    }
}
