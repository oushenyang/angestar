package cn.stylefeng.guns.modular.trial.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 试用信息
 * </p>
 *
 * @author shenyang.ou
 * @since 2021-03-04
 */
@TableName("ag_trial")
public class Trial implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 试用id
     */
    @TableId(value = "trial_id", type = IdType.ID_WORKER)
    private Long trialId;

    /**
     * 应用id
     */
    @TableField("app_id")
    private Long appId;

    /**
     * mac
     */
    @TableField("mac")
    private String mac;

    /**
     * ip
     */
    @TableField("ip")
    private String ip;

    /**
     * 设备型号
     */
    @TableField("model")
    private String model;

    /**
     * 试用次数
     */
    @TableField("trial_num")
    private Integer trialNum;

    /**
     * 试用时间
     */
    @TableField("trial_time")
    private Date trialTime;

    /**
     * 是否到期；0-未到期；1-已到期
     */
    @TableField("expire")
    private Boolean expire;

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


    public Long getTrialId() {
        return trialId;
    }

    public void setTrialId(Long trialId) {
        this.trialId = trialId;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
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

    public Integer getTrialNum() {
        return trialNum;
    }

    public void setTrialNum(Integer trialNum) {
        this.trialNum = trialNum;
    }

    public Date getTrialTime() {
        return trialTime;
    }

    public void setTrialTime(Date trialTime) {
        this.trialTime = trialTime;
    }

    public Boolean getExpire() {
        return expire;
    }

    public void setExpire(Boolean expire) {
        this.expire = expire;
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
        return "Trial{" +
        "trialId=" + trialId +
        ", appId=" + appId +
        ", mac=" + mac +
        ", ip=" + ip +
        ", model=" + model +
        ", trialNum=" + trialNum +
        ", trialTime=" + trialTime +
        ", expire=" + expire +
        ", createUser=" + createUser +
        ", createTime=" + createTime +
        ", updateUser=" + updateUser +
        ", updateTime=" + updateTime +
        "}";
    }
}
