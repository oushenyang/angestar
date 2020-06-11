package cn.stylefeng.guns.modular.account.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 注册码表
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-05-29
 */
@TableName("ag_serial_info")
public class SerialInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 注册码id
     */
    @TableId(value = "serial_id", type = IdType.ID_WORKER)
    private Long serialId;

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
     * 注册码
     */
    @TableField("serial_code")
    private String serialCode;

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
     * 换绑数
     */
    @TableField("change_bind_num")
    private Integer changeBindNum;

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
     * 备注
     */
    @TableField("remark")
    private String remark;

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


    public Long getSerialId() {
        return serialId;
    }

    public void setSerialId(Long serialId) {
        this.serialId = serialId;
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

    public String getSerialCode() {
        return serialCode;
    }

    public void setSerialCode(String serialCode) {
        this.serialCode = serialCode;
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

    public Integer getChangeBindNum() {
        return changeBindNum;
    }

    public void setChangeBindNum(Integer changeBindNum) {
        this.changeBindNum = changeBindNum;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        return "SerialInfo{" +
        "serialId=" + serialId +
        ", appId=" + appId +
        ", cardTypeId=" + cardTypeId +
        ", userId=" + userId +
        ", userName=" + userName +
        ", isUniversal=" + isUniversal +
        ", serialCode=" + serialCode +
        ", isCustomTime=" + isCustomTime +
        ", customTimeNum=" + customTimeNum +
        ", cardStatus=" + cardStatus +
        ", changeBindNum=" + changeBindNum +
        ", activeTime=" + activeTime +
        ", expireTime=" + expireTime +
        ", remark=" + remark +
        ", revision=" + revision +
        ", createUser=" + createUser +
        ", createTime=" + createTime +
        ", updateUser=" + updateUser +
        ", updateTime=" + updateTime +
        "}";
    }
}
