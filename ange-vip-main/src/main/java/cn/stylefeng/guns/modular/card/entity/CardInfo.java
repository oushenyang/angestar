package cn.stylefeng.guns.modular.card.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 卡密表
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-04-20
 */
@TableName("ag_card_info")
public class CardInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 卡密ID
     */
    @TableId(value = "card_id", type = IdType.ID_WORKER)
    private Long cardId;

    /**
     * 应用ID
     */
    @TableField("app_id")
    private Long appId;

    /**
     * 卡类ID
     */
    @TableField("card_type_id")
    private Long cardTypeId;

    /**
     * 申请人ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 申请人名称
     */
    @TableField("user_name")
    private String userName;

    /**
     * 是否通用 0-否；1-是
     */
    @TableField("is_universal")
    private Boolean isUniversal;

    /**
     * 卡密
     */
    @TableField("card_code")
    private String cardCode;

    /**
     * 是否自定义时间
     */
    @TableField("is_custom_time")
    private Boolean isCustomTime;

    /**
     * 自定义时间值(天)
     */
    @TableField("custom_time_num")
    private Integer customTimeNum;

    /**
     * 状态 0-未激活；1-已激活；2-已过期；3-已禁用；4-已删除
     */
    @TableField("card_status")
    private Integer cardStatus;

    /**
     * 绑定mac
     */
    @TableField("card_mac")
    private String cardMac;

    /**
     * 绑定ip
     */
    @TableField("card_ip")
    private String cardIp;

    /**
     * 卡密token
     */
    @TableField("card_token")
    private String cardToken;

    /**
     * 激活时间
     */
    @TableField("active_time")
    private Date activeTime;

    /**
     * 过期时间
     */
    @TableField("expire_time")
    private Date expireTime;

    /**
     * 绑机配置 0-默认；1-关闭；2-MAC；3-IP；4-混合；
     */
    @TableField("card_bind_type")
    private Integer cardBindType;

    /**
     * 绑机数量
     */
    @TableField("card_bind_num")
    private Integer cardBindNum;

    /**
     * 多开开关 0-默认；1-关闭；2-开启
     */
    @TableField("card_open_range")
    private Integer cardOpenRange;

    /**
     * 单码登录方式 0-非顶号；1-顶号；
     */
    @TableField("card_sign_type")
    private Integer cardSignType;

    /**
     * 多开数量
     */
    @TableField("card_open_num")
    private Integer cardOpenNum;

    /**
     * 卡密备注
     */
    @TableField("card_remark")
    private String cardRemark;

    /**
     * 禁用备注
     */
    @TableField("prohibit_remark")
    private String prohibitRemark;

    /**
     * 是否加时 0-否；1-是
     */
    @TableField("whether_add_time")
    private Boolean whetherAddTime;

    /**
     * 加时天数
     */
    @TableField("add_day_num")
    private Integer addDayNum;

    /**
     * 加时小时
     */
    @TableField("add_hour_num")
    private Integer addHourNum;

    /**
     * 加时分钟
     */
    @TableField("add_minute_num")
    private Integer addMinuteNum;

    /**
     * 加时时间
     */
    @TableField("add_time")
    private Date addTime;

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


    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getUniversal() {
        return isUniversal;
    }

    public void setUniversal(Boolean isUniversal) {
        this.isUniversal = isUniversal;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public Boolean getCustomTime() {
        return isCustomTime;
    }

    public void setCustomTime(Boolean isCustomTime) {
        this.isCustomTime = isCustomTime;
    }

    public Integer getCustomTimeNum() {
        return customTimeNum;
    }

    public void setCustomTimeNum(Integer customTimeNum) {
        this.customTimeNum = customTimeNum;
    }

    public Integer getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(Integer cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getCardMac() {
        return cardMac;
    }

    public void setCardMac(String cardMac) {
        this.cardMac = cardMac;
    }

    public String getCardIp() {
        return cardIp;
    }

    public void setCardIp(String cardIp) {
        this.cardIp = cardIp;
    }

    public String getCardToken() {
        return cardToken;
    }

    public void setCardToken(String cardToken) {
        this.cardToken = cardToken;
    }

    public Date getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(Date activeTime) {
        this.activeTime = activeTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Integer getCardBindType() {
        return cardBindType;
    }

    public void setCardBindType(Integer cardBindType) {
        this.cardBindType = cardBindType;
    }

    public Integer getCardBindNum() {
        return cardBindNum;
    }

    public void setCardBindNum(Integer cardBindNum) {
        this.cardBindNum = cardBindNum;
    }

    public Integer getCardOpenRange() {
        return cardOpenRange;
    }

    public void setCardOpenRange(Integer cardOpenRange) {
        this.cardOpenRange = cardOpenRange;
    }

    public Integer getCardSignType() {
        return cardSignType;
    }

    public void setCardSignType(Integer cardSignType) {
        this.cardSignType = cardSignType;
    }

    public Integer getCardOpenNum() {
        return cardOpenNum;
    }

    public void setCardOpenNum(Integer cardOpenNum) {
        this.cardOpenNum = cardOpenNum;
    }

    public String getCardRemark() {
        return cardRemark;
    }

    public void setCardRemark(String cardRemark) {
        this.cardRemark = cardRemark;
    }

    public String getProhibitRemark() {
        return prohibitRemark;
    }

    public void setProhibitRemark(String prohibitRemark) {
        this.prohibitRemark = prohibitRemark;
    }

    public Boolean getWhetherAddTime() {
        return whetherAddTime;
    }

    public void setWhetherAddTime(Boolean whetherAddTime) {
        this.whetherAddTime = whetherAddTime;
    }

    public Integer getAddDayNum() {
        return addDayNum;
    }

    public void setAddDayNum(Integer addDayNum) {
        this.addDayNum = addDayNum;
    }

    public Integer getAddHourNum() {
        return addHourNum;
    }

    public void setAddHourNum(Integer addHourNum) {
        this.addHourNum = addHourNum;
    }

    public Integer getAddMinuteNum() {
        return addMinuteNum;
    }

    public void setAddMinuteNum(Integer addMinuteNum) {
        this.addMinuteNum = addMinuteNum;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
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
        return "CardInfo{" +
        "cardId=" + cardId +
        ", appId=" + appId +
        ", cardTypeId=" + cardTypeId +
        ", userId=" + userId +
        ", userName=" + userName +
        ", isUniversal=" + isUniversal +
        ", cardCode=" + cardCode +
        ", isCustomTime=" + isCustomTime +
        ", customTimeNum=" + customTimeNum +
        ", cardStatus=" + cardStatus +
        ", cardMac=" + cardMac +
        ", cardIp=" + cardIp +
        ", cardToken=" + cardToken +
        ", activeTime=" + activeTime +
        ", expireTime=" + expireTime +
        ", cardBindType=" + cardBindType +
        ", cardOpenRange=" + cardOpenRange +
        ", cardOpenNum=" + cardOpenNum +
        ", cardRemark=" + cardRemark +
        ", prohibitRemark=" + prohibitRemark +
        ", whetherAddTime=" + whetherAddTime +
        ", addDayNum=" + addDayNum +
        ", addHourNum=" + addHourNum +
        ", addMinuteNum=" + addMinuteNum +
        ", addTime=" + addTime +
        ", createUser=" + createUser +
        ", createTime=" + createTime +
        ", updateUser=" + updateUser +
        ", updateTime=" + updateTime +
        "}";
    }
}
