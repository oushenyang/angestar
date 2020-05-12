package cn.stylefeng.guns.modular.remote.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 远程数据
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-05-12
 */
@TableName("ag_remote_data")
public class RemoteData implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数据id
     */
    @TableId(value = "data_id", type = IdType.ID_WORKER)
    private Long dataId;

    /**
     * 应用ID
     */
    @TableField("app_id")
    private Long appId;

    /**
     * 数据编码
     */
    @TableField("data_code")
    private String dataCode;

    /**
     * 参数一
     */
    @TableField("parameter_one")
    private String parameterOne;

    /**
     * 参数二
     */
    @TableField("parameter_two")
    private String parameterTwo;

    /**
     * 参数三
     */
    @TableField("parameter_three")
    private String parameterThree;

    /**
     * 数据值
     */
    @TableField("data_value")
    private String dataValue;

    /**
     * 创建类型 0-手动添加；1-接口生成
     */
    @TableField("create_type")
    private Integer createType;

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


    public Long getDataId() {
        return dataId;
    }

    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getDataCode() {
        return dataCode;
    }

    public void setDataCode(String dataCode) {
        this.dataCode = dataCode;
    }

    public String getParameterOne() {
        return parameterOne;
    }

    public void setParameterOne(String parameterOne) {
        this.parameterOne = parameterOne;
    }

    public String getParameterTwo() {
        return parameterTwo;
    }

    public void setParameterTwo(String parameterTwo) {
        this.parameterTwo = parameterTwo;
    }

    public String getParameterThree() {
        return parameterThree;
    }

    public void setParameterThree(String parameterThree) {
        this.parameterThree = parameterThree;
    }

    public String getDataValue() {
        return dataValue;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }

    public Integer getCreateType() {
        return createType;
    }

    public void setCreateType(Integer createType) {
        this.createType = createType;
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
        return "RemoteData{" +
        "dataId=" + dataId +
        ", appId=" + appId +
        ", dataCode=" + dataCode +
        ", dataValue=" + dataValue +
        ", createType=" + createType +
        ", createUser=" + createUser +
        ", createTime=" + createTime +
        ", updateUser=" + updateUser +
        ", updateTime=" + updateTime +
        "}";
    }
}
