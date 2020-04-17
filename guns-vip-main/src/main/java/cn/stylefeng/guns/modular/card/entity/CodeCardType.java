package cn.stylefeng.guns.modular.card.entity;

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
 * 单码卡类列表 
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-04-16
 */
@TableName("ag_code_card_type")
public class CodeCardType implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 卡类Id
     */
    @TableId(value = "card_type_id", type = IdType.ID_WORKER)
    private Long cardTypeId;

    /**
     * 所属软件
     */
    @TableField("app_id")
    private Long appId;

    /**
     * 卡类名称
     */
    @TableField("card_type_name")
    private String cardTypeName;

    /**
     * 卡类时间类型  0-分；1-时；2-天；3-周；4-月；5-季；6-年
     */
    @TableField("card_time_type")
    private Integer cardTimeType;

    /**
     * 卡值
     */
    @TableField("card_type_data")
    private Integer cardTypeData;

    /**
     * 卡密前缀
     */
    @TableField("card_type_prefix")
    private String cardTypePrefix;

    /**
     * 卡密规则 0-大写字母+数字；1-小写字母+数字；2-全大写字母；3-全小写字母；4-全数字；
     */
    @TableField("card_type_rule")
    private Integer cardTypeRule;

    /**
     * 卡密长度 0-32位；1-16位；2-8位；
     */
    @TableField("card_type_length")
    private Integer cardTypeLength;

    /**
     * 售价
     */
    @TableField("card_type_price")
    private BigDecimal cardTypePrice;

    /**
     * 代理售价
     */
    @TableField("card_type_agent_price")
    private BigDecimal cardTypeAgentPrice;

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


    public Long getCardTypeId() {
        return cardTypeId;
    }

    public void setCardTypeId(Long cardTypeId) {
        this.cardTypeId = cardTypeId;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getCardTypeName() {
        return cardTypeName;
    }

    public void setCardTypeName(String cardTypeName) {
        this.cardTypeName = cardTypeName;
    }

    public Integer getCardTimeType() {
        return cardTimeType;
    }

    public void setCardTimeType(Integer cardTimeType) {
        this.cardTimeType = cardTimeType;
    }

    public Integer getCardTypeData() {
        return cardTypeData;
    }

    public void setCardTypeData(Integer cardTypeData) {
        this.cardTypeData = cardTypeData;
    }

    public String getCardTypePrefix() {
        return cardTypePrefix;
    }

    public void setCardTypePrefix(String cardTypePrefix) {
        this.cardTypePrefix = cardTypePrefix;
    }

    public Integer getCardTypeRule() {
        return cardTypeRule;
    }

    public void setCardTypeRule(Integer cardTypeRule) {
        this.cardTypeRule = cardTypeRule;
    }

    public Integer getCardTypeLength() {
        return cardTypeLength;
    }

    public void setCardTypeLength(Integer cardTypeLength) {
        this.cardTypeLength = cardTypeLength;
    }

    public BigDecimal getCardTypePrice() {
        return cardTypePrice;
    }

    public void setCardTypePrice(BigDecimal cardTypePrice) {
        this.cardTypePrice = cardTypePrice;
    }

    public BigDecimal getCardTypeAgentPrice() {
        return cardTypeAgentPrice;
    }

    public void setCardTypeAgentPrice(BigDecimal cardTypeAgentPrice) {
        this.cardTypeAgentPrice = cardTypeAgentPrice;
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
        return "CodeCardType{" +
        "cardTypeId=" + cardTypeId +
        ", appId=" + appId +
        ", cardTypeName=" + cardTypeName +
        ", cardTimeType=" + cardTimeType +
        ", cardTypeData=" + cardTypeData +
        ", cardTypePrefix=" + cardTypePrefix +
        ", cardTypeRule=" + cardTypeRule +
        ", cardTypeLength=" + cardTypeLength +
        ", cardTypePrice=" + cardTypePrice +
        ", cardTypeAgentPrice=" + cardTypeAgentPrice +
        ", revision=" + revision +
        ", createUser=" + createUser +
        ", createTime=" + createTime +
        ", updateUser=" + updateUser +
        ", updateTime=" + updateTime +
        "}";
    }
}
