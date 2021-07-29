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
 * 应用版本表 
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-04-12
 */
@TableName("ag_app_edition")
public class AppEdition implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 版本id
     */
    @TableId(value = "edition_id", type = IdType.ID_WORKER)
    private Long editionId;

    /**
     * 版本编号
     */
    @TableField("edition_serial")
    private String editionSerial;

    /**
     * 软件id
     */
    @TableField("app_id")
    private Long appId;

    /**
     * 版本名称
     */
    @TableField("edition_name")
    private String editionName;

    /**
     * 版本号
     */
    @TableField("edition_num")
    private String editionNum;

    /**
     * 版本状态 0-启用；1-测试；2-关闭；
     */
    @TableField("edition_status")
    private Integer editionStatus;

    /**
     * 强制更新 0-否；1-是；
     */
    @TableField("need_update")
    private Boolean needUpdate;

    /**
     * 版本MD5
     */
    @TableField("edition_md5")
    private String editionMd5;

    /**
     * 更新地址
     */
    @TableField("update_url")
    private String updateUrl;

    /**
     * 更新描述
     */
    @TableField("update_describe")
    private String updateDescribe;

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


    public Long getEditionId() {
        return editionId;
    }

    public void setEditionId(Long editionId) {
        this.editionId = editionId;
    }

    public String getEditionSerial() {
        return editionSerial;
    }

    public void setEditionSerial(String editionSerial) {
        this.editionSerial = editionSerial;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getEditionName() {
        return editionName;
    }

    public void setEditionName(String editionName) {
        this.editionName = editionName;
    }

    public String getEditionNum() {
        return editionNum;
    }

    public void setEditionNum(String editionNum) {
        this.editionNum = editionNum;
    }

    public Integer getEditionStatus() {
        return editionStatus;
    }

    public void setEditionStatus(Integer editionStatus) {
        this.editionStatus = editionStatus;
    }

    public Boolean getNeedUpdate() {
        return needUpdate;
    }

    public void setNeedUpdate(Boolean needUpdate) {
        this.needUpdate = needUpdate;
    }

    public String getEditionMd5() {
        return editionMd5;
    }

    public void setEditionMd5(String editionMd5) {
        this.editionMd5 = editionMd5;
    }

    public String getUpdateUrl() {
        return updateUrl;
    }

    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
    }

    public String getUpdateDescribe() {
        return updateDescribe;
    }

    public void setUpdateDescribe(String updateDescribe) {
        this.updateDescribe = updateDescribe;
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
        return "AppEdition{" +
        "editionId=" + editionId +
        ", editionSerial=" + editionSerial +
        ", appId=" + appId +
        ", editionName=" + editionName +
        ", editionNum=" + editionNum +
        ", editionStatus=" + editionStatus +
        ", needUpdate=" + needUpdate +
        ", editionMd5=" + editionMd5 +
        ", updateUrl=" + updateUrl +
        ", updateDescribe=" + updateDescribe +
        ", remark=" + remark +
        ", revision=" + revision +
        ", createUser=" + createUser +
        ", createTime=" + createTime +
        ", updateUser=" + updateUser +
        ", updateTime=" + updateTime +
        "}";
    }
}
