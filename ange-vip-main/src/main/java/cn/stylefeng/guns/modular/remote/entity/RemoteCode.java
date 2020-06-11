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
 * 远程代码
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-05-19
 */
@TableName("ag_remote_code")
public class RemoteCode implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 代码id
     */
    @TableId(value = "code_id", type = IdType.ID_WORKER)
    private Long codeId;

    /**
     * 应用ID
     */
    @TableField("app_id")
    private Long appId;

    /**
     * 代码编码
     */
    @TableField("code_code")
    private String codeCode;

    /**
     * 参数一名
     */
    @TableField("parameter_one")
    private String parameterOne;

    /**
     * 参数二名
     */
    @TableField("parameter_two")
    private String parameterTwo;

    /**
     * 参数三名
     */
    @TableField("parameter_three")
    private String parameterThree;

    /**
     * 参数四名
     */
    @TableField("parameter_four")
    private String parameterFour;

    /**
     * 代码值
     */
    @TableField("code_value")
    private String codeValue;

    /**
     * 代码文本
     */
    @TableField("code_text")
    private String codeText;

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


    public Long getCodeId() {
        return codeId;
    }

    public void setCodeId(Long codeId) {
        this.codeId = codeId;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getCodeCode() {
        return codeCode;
    }

    public void setCodeCode(String codeCode) {
        this.codeCode = codeCode;
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

    public String getParameterFour() {
        return parameterFour;
    }

    public void setParameterFour(String parameterFour) {
        this.parameterFour = parameterFour;
    }

    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }

    public String getCodeText() {
        return codeText;
    }

    public void setCodeText(String codeText) {
        this.codeText = codeText;
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
        return "RemoteCode{" +
        "codeId=" + codeId +
        ", appId=" + appId +
        ", codeCode=" + codeCode +
        ", parameterOne=" + parameterOne +
        ", parameterTwo=" + parameterTwo +
        ", parameterThree=" + parameterThree +
        ", parameterFour=" + parameterFour +
        ", codeValue=" + codeValue +
        ", codeText=" + codeText +
        ", createUser=" + createUser +
        ", createTime=" + createTime +
        ", updateUser=" + updateUser +
        ", updateTime=" + updateTime +
        "}";
    }
}
