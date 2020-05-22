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
 * 代理卡密
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-05-22
 */
@TableName("ag_agent_card")
public class AgentCard implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 代理卡密id
     */
    @TableId(value = "agent_card_id", type = IdType.ID_WORKER)
    private Long agentCardId;

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
     * 卡类Id
     */
    @TableField("card_type_id")
    private Long cardTypeId;

    /**
     * 卡类名称
     */
    @TableField("card_type_name")
    private String cardTypeName;

    /**
     * 卡类类型 0-单码卡密；1-通用卡密；2-注册卡密
     */
    @TableField("card_type")
    private Integer cardType;

    /**
     * 市场价格
     */
    @TableField("market_price")
    private BigDecimal marketPrice;

    /**
     * 代理价格
     */
    @TableField("agent_price")
    private BigDecimal agentPrice;

    /**
     * 下级代理价格
     */
    @TableField("level_agent_price")
    private BigDecimal levelAgentPrice;

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


    public Long getAgentCardId() {
        return agentCardId;
    }

    public void setAgentCardId(Long agentCardId) {
        this.agentCardId = agentCardId;
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

    public Long getCardTypeId() {
        return cardTypeId;
    }

    public void setCardTypeId(Long cardTypeId) {
        this.cardTypeId = cardTypeId;
    }

    public String getCardTypeName() {
        return cardTypeName;
    }

    public void setCardTypeName(String cardTypeName) {
        this.cardTypeName = cardTypeName;
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public BigDecimal getAgentPrice() {
        return agentPrice;
    }

    public void setAgentPrice(BigDecimal agentPrice) {
        this.agentPrice = agentPrice;
    }

    public BigDecimal getLevelAgentPrice() {
        return levelAgentPrice;
    }

    public void setLevelAgentPrice(BigDecimal levelAgentPrice) {
        this.levelAgentPrice = levelAgentPrice;
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
        return "AgentCard{" +
        "agentCardId=" + agentCardId +
        ", agentAppId=" + agentAppId +
        ", appId=" + appId +
        ", cardTypeId=" + cardTypeId +
        ", cardTypeName=" + cardTypeName +
        ", cardType=" + cardType +
        ", marketPrice=" + marketPrice +
        ", agentPrice=" + agentPrice +
        ", levelAgentPrice=" + levelAgentPrice +
        ", revision=" + revision +
        ", createUser=" + createUser +
        ", createTime=" + createTime +
        ", updateUser=" + updateUser +
        ", updateTime=" + updateTime +
        "}";
    }
}
