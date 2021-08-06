package cn.stylefeng.guns.modular.app.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 应用异常列表
 * </p>
 *
 * @author shenyang.ou
 * @since 2021-08-06
 */
@TableName("ag_app_exception")
public class AppException implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 异常id
     */
    @TableId(value = "exception_id", type = IdType.ID_WORKER)
    private Long exceptionId;

    /**
     * 应用id
     */
    @TableField("app_id")
    private Long appId;

    /**
     * 异常的标题
     */
    @TableField("title")
    private String title;

    /**
     * 异常的内容
     */
    @TableField("context")
    private String context;

    /**
     * 版本号
     */
    @TableField("edition")
    private String edition;

    /**
     * 设备型号
     */
    @TableField("model")
    private String model;

    /**
     * 机器码
     */
    @TableField("mac")
    private String mac;

    /**
     * 异常次数
     */
    @TableField("exception_num")
    private Integer exceptionNum;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 创建人
     */
    @TableField(value = "create_user", fill = FieldFill.INSERT)
    private Long createUser;


    public Long getExceptionId() {
        return exceptionId;
    }

    public void setExceptionId(Long exceptionId) {
        this.exceptionId = exceptionId;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Integer getExceptionNum() {
        return exceptionNum;
    }

    public void setExceptionNum(Integer exceptionNum) {
        this.exceptionNum = exceptionNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    @Override
    public String toString() {
        return "AppException{" +
        "exceptionId=" + exceptionId +
        ", appId=" + appId +
        ", title=" + title +
        ", context=" + context +
        ", edition=" + edition +
        ", model=" + model +
        ", mac=" + mac +
        ", exceptionNum=" + exceptionNum +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", createUser=" + createUser +
        "}";
    }
}
