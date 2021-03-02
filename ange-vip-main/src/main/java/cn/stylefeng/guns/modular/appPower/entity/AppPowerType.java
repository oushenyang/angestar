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
 * 应用类型表 
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-10-29
 */
@TableName("ag_app_power_type")
public class AppPowerType implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 应用授权id
     */
    @TableId(value = "app_power_type_id", type = IdType.ID_WORKER)
    private Long appPowerTypeId;

    /**
     * 分类应用名
     */
    @TableField("app_name")
    private String appName;

    /**
     * 分类编码
     */
    @TableField("app_code")
    private String appCode;

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


    public Long getAppPowerTypeId() {
        return appPowerTypeId;
    }

    public void setAppPowerTypeId(Long appPowerTypeId) {
        this.appPowerTypeId = appPowerTypeId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
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

    @Override
    public String toString() {
        return "AppPowerType{" +
        "appPowerTypeId=" + appPowerTypeId +
        ", appName=" + appName +
        ", appCode=" + appCode +
        ", customData=" + customData +
        ", createUser=" + createUser +
        ", createTime=" + createTime +
        ", updateUser=" + updateUser +
        ", updateTime=" + updateTime +
        "}";
    }
}
