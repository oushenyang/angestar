package cn.stylefeng.guns.modular.device.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 绑定设备信息
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-08-02
 */
@TableName("ag_device")
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 设备id
     */
    @TableId(value = "device_id", type = IdType.ID_WORKER)
    private Long deviceId;

    /**
     * 应用id
     */
    @TableField("app_id")
    private Long appId;

    /**
     * 卡密或账号id
     */
    @TableField("card_or_user_id")
    private Long cardOrUserId;

    /**
     * 卡密或账号
     */
    @TableField("card_or_user_code")
    private String cardOrUserCode;

    /**
     * 卡密类型；1-单码；2-注册码
     */
    @TableField("card_type")
    private Integer cardType;

    /**
     * mac地址
     */
    @TableField("mac")
    private String mac;

    /**
     * IP
     */
    @TableField("ip")
    private String ip;

    /**
     * 设备型号
     */
    @TableField("model")
    private String model;

    /**
     * ip详细地址
     */
    @TableField("ip_address")
    private String ipAddress;

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


    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public Long getCardOrUserId() {
        return cardOrUserId;
    }

    public void setCardOrUserId(Long cardOrUserId) {
        this.cardOrUserId = cardOrUserId;
    }

    public String getCardOrUserCode() {
        return cardOrUserCode;
    }

    public void setCardOrUserCode(String cardOrUserCode) {
        this.cardOrUserCode = cardOrUserCode;
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
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
        return "Device{" +
        "deviceId=" + deviceId +
        ", appId=" + appId +
        ", cardOrUserId=" + cardOrUserId +
        ", cardOrUserCode=" + cardOrUserCode +
        ", cardType=" + cardType +
        ", mac=" + mac +
        ", ip=" + ip +
        ", model=" + model +
        ", ipAddress=" + ipAddress +
        ", createUser=" + createUser +
        ", createTime=" + createTime +
        ", updateUser=" + updateUser +
        ", updateTime=" + updateTime +
        "}";
    }
}
