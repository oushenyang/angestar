package cn.stylefeng.guns.modular.appPower.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 应用授权表 
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-10-29
 */
@TableName("ag_app_power")
public class AppPower implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 应用授权id
     */
    @TableId(value = "app_power_id", type = IdType.ID_WORKER)
    private Long appPowerId;

    /**
     * 应用名
     */
    @TableField("app_name")
    private String appName;

    /**
     * 应用入口
     */
    @TableField("application_name")
    private String applicationName;

    /**
     * 应用分类编码
     */
    @TableField("app_type_code")
    private String appTypeCode;

    /**
     * 签名验证
     */
    @TableField("sign")
    private String sign;

    /**
     * 是否授权 0-否；1-是
     */
    @TableField("whether_legal")
    private Boolean whetherLegal;

    /**
     * 是否显示 0-否；1-是
     */
    @TableField("whether_show")
    private Boolean whetherShow;

    /**
     * 是否制裁 0-否；1-是
     */
    @TableField("whether_sanction")
    private Boolean whetherSanction;

    /**
     * 应用自定义数据
     */
    @TableField("custom_data")
    private String customData;

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

    /**
     * 制裁时间
     */
    @TableField("sanction_time")
    private Date sanctionTime;


    public Long getAppPowerId() {
        return appPowerId;
    }

    public void setAppPowerId(Long appPowerId) {
        this.appPowerId = appPowerId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getAppTypeCode() {
        return appTypeCode;
    }

    public void setAppTypeCode(String appTypeCode) {
        this.appTypeCode = appTypeCode;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Boolean getWhetherLegal() {
        return whetherLegal;
    }

    public void setWhetherLegal(Boolean whetherLegal) {
        this.whetherLegal = whetherLegal;
    }

    public Boolean getWhetherSanction() {
        return whetherSanction;
    }

    public void setWhetherSanction(Boolean whetherSanction) {
        this.whetherSanction = whetherSanction;
    }

    public Boolean getWhetherShow() {
        return whetherShow;
    }

    public void setWhetherShow(Boolean whetherShow) {
        this.whetherShow = whetherShow;
    }

    public String getCustomData() {
        return customData;
    }

    public void setCustomData(String customData) {
        this.customData = customData;
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

    public Date getSanctionTime() {
        return sanctionTime;
    }

    public void setSanctionTime(Date sanctionTime) {
        this.sanctionTime = sanctionTime;
    }

    @Override
    public String toString() {
        return "AppPower{" +
        "appPowerId=" + appPowerId +
        ", appName=" + appName +
        ", applicationName=" + applicationName +
        ", appTypeCode=" + appTypeCode +
        ", sign=" + sign +
        ", whetherLegal=" + whetherLegal +
        ", whetherShow=" + whetherShow +
        ", whetherSanction=" + whetherSanction +
        ", customData=" + customData +
        ", createUser=" + createUser +
        ", createTime=" + createTime +
        ", updateUser=" + updateUser +
        ", updateTime=" + updateTime +
        ", sanctionTime=" + sanctionTime +
        "}";
    }
}
