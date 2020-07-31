package cn.stylefeng.guns.sys.modular.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 接口自定义返回
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-07-31
 */
@TableName("ag_api_result")
public class ApiResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 接口自定义返回id
     */
    @TableId(value = "api_result_id", type = IdType.ID_WORKER)
    private Long apiResultId;

    /**
     * 应用id
     */
    @TableField("app_id")
    private Long appId;

    /**
     * 返回类别
     */
    @TableField("result_type")
    private String resultType;

    /**
     * 是否成功：0-否；1-是
     */
    @TableField("result_success")
    private Boolean resultSuccess;

    /**
     * 变量编码集合
     */
    @TableField("result_variables")
    private String resultVariables;

    /**
     * 默认返回码
     */
    @TableField("result_code")
    private String resultCode;

    /**
     * 默认返回数据
     */
    @TableField("result_data")
    private String resultData;

    /**
     * 自定义返回数据
     */
    @TableField("custom_result_data")
    private String customResultData;

    /**
     * 返回说明
     */
    @TableField("result_remark")
    private String resultRemark;

    /**
     * 是否可编辑：0-否；1-是
     */
    @TableField("whether_edit")
    private Boolean whetherEdit;

    /**
     * 是否返回json  0-否；1-是
     */
    @TableField("whether_result_json")
    private Boolean whetherResultJson;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

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


    public Long getApiResultId() {
        return apiResultId;
    }

    public void setApiResultId(Long apiResultId) {
        this.apiResultId = apiResultId;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public Boolean getResultSuccess() {
        return resultSuccess;
    }

    public void setResultSuccess(Boolean resultSuccess) {
        this.resultSuccess = resultSuccess;
    }

    public String getResultVariables() {
        return resultVariables;
    }

    public void setResultVariables(String resultVariables) {
        this.resultVariables = resultVariables;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultData() {
        return resultData;
    }

    public void setResultData(String resultData) {
        this.resultData = resultData;
    }

    public String getCustomResultData() {
        return customResultData;
    }

    public void setCustomResultData(String customResultData) {
        this.customResultData = customResultData;
    }

    public String getResultRemark() {
        return resultRemark;
    }

    public void setResultRemark(String resultRemark) {
        this.resultRemark = resultRemark;
    }

    public Boolean getWhetherEdit() {
        return whetherEdit;
    }

    public void setWhetherEdit(Boolean whetherEdit) {
        this.whetherEdit = whetherEdit;
    }

    public Boolean getWhetherResultJson() {
        return whetherResultJson;
    }

    public void setWhetherResultJson(Boolean whetherResultJson) {
        this.whetherResultJson = whetherResultJson;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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
        return "ApiResult{" +
        "apiResultId=" + apiResultId +
        ", appId=" + appId +
        ", resultType=" + resultType +
        ", resultSuccess=" + resultSuccess +
        ", resultVariables=" + resultVariables +
        ", resultCode=" + resultCode +
        ", resultData=" + resultData +
        ", customResultData=" + customResultData +
        ", resultRemark=" + resultRemark +
        ", whetherEdit=" + whetherEdit +
        ", whetherResultJson=" + whetherResultJson +
        ", sort=" + sort +
        ", createUser=" + createUser +
        ", createTime=" + createTime +
        ", updateUser=" + updateUser +
        ", updateTime=" + updateTime +
        "}";
    }
}
