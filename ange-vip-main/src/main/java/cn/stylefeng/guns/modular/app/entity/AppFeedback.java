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
 * 应用反馈列表
 * </p>
 *
 * @author shenyang.ou
 * @since 2021-08-06
 */
@TableName("ag_app_feedback")
public class AppFeedback implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 反馈id
     */
    @TableId(value = "feedback_id", type = IdType.ID_WORKER)
    private Long feedbackId;

    /**
     * 应用id
     */
    @TableField("app_id")
    private Long appId;

    /**
     * 反馈的内容
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
     * 联系方式
     */
    @TableField("links")
    private String links;

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


    public Long getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Long feedbackId) {
        this.feedbackId = feedbackId;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
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

    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
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
        return "AppFeedback{" +
        "feedbackId=" + feedbackId +
        ", appId=" + appId +
        ", context=" + context +
        ", edition=" + edition +
        ", model=" + model +
        ", mac=" + mac +
        ", links=" + links +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", createUser=" + createUser +
        "}";
    }
}
