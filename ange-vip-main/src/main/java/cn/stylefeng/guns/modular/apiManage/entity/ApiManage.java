package cn.stylefeng.guns.modular.apiManage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 接口管理 
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-06-02
 */
@TableName("ag_api_manage")
public class ApiManage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 接口管理id
     */
    @TableId(value = "api_manage_id", type = IdType.ID_WORKER)
    private Long apiManageId;

    /**
     * 应用id
     */
    @TableField("app_id")
    private Long appId;

    /**
     * 调用码
     */
    @TableField("call_code")
    private String callCode;

    /**
     * 接口状态 0-开启；1-关闭
     */
    @TableField("api_status")
    private Integer apiStatus;

    /**
     * 接口类别（取字典）
     */
    @TableField("api_type")
    private String apiType;

    /**
     * 接口名称
     */
    @TableField("api_name")
    private String apiName;

    /**
     * 接口编码
     */
    @TableField("api_code")
    private String apiCode;

    /**
     * 参数数量
     */
    @TableField("parameter_num")
    private Integer parameterNum;

    /**
     * 参数一
     */
    @TableField("parameter_one")
    private String parameterOne;

    /**
     * 参数一说明
     */
    @TableField("parameter_one_remark")
    private String parameterOneRemark;

    /**
     * 参数一注释
     */
    @TableField("parameter_one_note")
    private String parameterOneNote;

    /**
     * 参数一是否必填；0-是；1-否
     */
    @TableField("parameter_one_required")
    private Boolean parameterOneRequired;

    /**
     * 参数二
     */
    @TableField("parameter_two")
    private String parameterTwo;

    /**
     * 参数二说明
     */
    @TableField("parameter_two_remark")
    private String parameterTwoRemark;

    /**
     * 参数二注释
     */
    @TableField("parameter_two_note")
    private String parameterTwoNote;

    /**
     * 参数二是否必填；0-是；1-否
     */
    @TableField("parameter_two_required")
    private Boolean parameterTwoRequired;

    /**
     * 参数三
     */
    @TableField("parameter_three")
    private String parameterThree;

    /**
     * 参数三说明
     */
    @TableField("parameter_three_remark")
    private String parameterThreeRemark;

    /**
     * 参数三注释
     */
    @TableField("parameter_three_note")
    private String parameterThreeNote;

    /**
     * 参数三是否必填；0-是；1-否
     */
    @TableField("parameter_three_required")
    private Boolean parameterThreeRequired;

    /**
     * 参数四
     */
    @TableField("parameter_four")
    private String parameterFour;

    /**
     * 参数四说明
     */
    @TableField("parameter_four_remark")
    private String parameterFourRemark;

    /**
     * 参数四注释
     */
    @TableField("parameter_four_note")
    private String parameterFourNote;

    /**
     * 参数四是否必填；0-是；1-否
     */
    @TableField("parameter_four_required")
    private Boolean parameterFourRequired;

    /**
     * 参数五
     */
    @TableField("parameter_five")
    private String parameterFive;

    /**
     * 参数五说明
     */
    @TableField("parameter_five_remark")
    private String parameterFiveRemark;

    /**
     * 参数五注释
     */
    @TableField("parameter_five_note")
    private String parameterFiveNote;

    /**
     * 参数五是否必填；0-是；1-否
     */
    @TableField("parameter_five_required")
    private Boolean parameterFiveRequired;

    /**
     * 参数六
     */
    @TableField("parameter_six")
    private String parameterSix;

    /**
     * 参数六说明
     */
    @TableField("parameter_six_remark")
    private String parameterSixRemark;

    /**
     * 参数六注释
     */
    @TableField("parameter_six_note")
    private String parameterSixNote;

    /**
     * 参数六是否必填；0-是；1-否
     */
    @TableField("parameter_six_required")
    private Boolean parameterSixRequired;

    /**
     * 参数七
     */
    @TableField("parameter_seven")
    private String parameterSeven;

    /**
     * 参数七说明
     */
    @TableField("parameter_seven_remark")
    private String parameterSevenRemark;

    /**
     * 参数七注释
     */
    @TableField("parameter_seven_note")
    private String parameterSevenNote;

    /**
     * 参数七是否必填；0-是；1-否
     */
    @TableField("parameter_seven_required")
    private Boolean parameterSevenRequired;

    /**
     * 返回说明
     */
    @TableField("return_remark")
    private String returnRemark;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

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


    public Long getApiManageId() {
        return apiManageId;
    }

    public void setApiManageId(Long apiManageId) {
        this.apiManageId = apiManageId;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getCallCode() {
        return callCode;
    }

    public void setCallCode(String callCode) {
        this.callCode = callCode;
    }

    public Integer getApiStatus() {
        return apiStatus;
    }

    public void setApiStatus(Integer apiStatus) {
        this.apiStatus = apiStatus;
    }

    public String getApiType() {
        return apiType;
    }

    public void setApiType(String apiType) {
        this.apiType = apiType;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getApiCode() {
        return apiCode;
    }

    public void setApiCode(String apiCode) {
        this.apiCode = apiCode;
    }

    public Integer getParameterNum() {
        return parameterNum;
    }

    public void setParameterNum(Integer parameterNum) {
        this.parameterNum = parameterNum;
    }

    public String getParameterOne() {
        return parameterOne;
    }

    public void setParameterOne(String parameterOne) {
        this.parameterOne = parameterOne;
    }

    public String getParameterOneRemark() {
        return parameterOneRemark;
    }

    public void setParameterOneRemark(String parameterOneRemark) {
        this.parameterOneRemark = parameterOneRemark;
    }

    public String getParameterOneNote() {
        return parameterOneNote;
    }

    public void setParameterOneNote(String parameterOneNote) {
        this.parameterOneNote = parameterOneNote;
    }

    public Boolean getParameterOneRequired() {
        return parameterOneRequired;
    }

    public void setParameterOneRequired(Boolean parameterOneRequired) {
        this.parameterOneRequired = parameterOneRequired;
    }

    public String getParameterTwo() {
        return parameterTwo;
    }

    public void setParameterTwo(String parameterTwo) {
        this.parameterTwo = parameterTwo;
    }

    public String getParameterTwoRemark() {
        return parameterTwoRemark;
    }

    public void setParameterTwoRemark(String parameterTwoRemark) {
        this.parameterTwoRemark = parameterTwoRemark;
    }

    public String getParameterTwoNote() {
        return parameterTwoNote;
    }

    public void setParameterTwoNote(String parameterTwoNote) {
        this.parameterTwoNote = parameterTwoNote;
    }

    public Boolean getParameterTwoRequired() {
        return parameterTwoRequired;
    }

    public void setParameterTwoRequired(Boolean parameterTwoRequired) {
        this.parameterTwoRequired = parameterTwoRequired;
    }

    public String getParameterThree() {
        return parameterThree;
    }

    public void setParameterThree(String parameterThree) {
        this.parameterThree = parameterThree;
    }

    public String getParameterThreeRemark() {
        return parameterThreeRemark;
    }

    public void setParameterThreeRemark(String parameterThreeRemark) {
        this.parameterThreeRemark = parameterThreeRemark;
    }

    public String getParameterThreeNote() {
        return parameterThreeNote;
    }

    public void setParameterThreeNote(String parameterThreeNote) {
        this.parameterThreeNote = parameterThreeNote;
    }

    public Boolean getParameterThreeRequired() {
        return parameterThreeRequired;
    }

    public void setParameterThreeRequired(Boolean parameterThreeRequired) {
        this.parameterThreeRequired = parameterThreeRequired;
    }

    public String getParameterFour() {
        return parameterFour;
    }

    public void setParameterFour(String parameterFour) {
        this.parameterFour = parameterFour;
    }

    public String getParameterFourRemark() {
        return parameterFourRemark;
    }

    public void setParameterFourRemark(String parameterFourRemark) {
        this.parameterFourRemark = parameterFourRemark;
    }

    public String getParameterFourNote() {
        return parameterFourNote;
    }

    public void setParameterFourNote(String parameterFourNote) {
        this.parameterFourNote = parameterFourNote;
    }

    public Boolean getParameterFourRequired() {
        return parameterFourRequired;
    }

    public void setParameterFourRequired(Boolean parameterFourRequired) {
        this.parameterFourRequired = parameterFourRequired;
    }

    public String getParameterFive() {
        return parameterFive;
    }

    public void setParameterFive(String parameterFive) {
        this.parameterFive = parameterFive;
    }

    public String getParameterFiveRemark() {
        return parameterFiveRemark;
    }

    public void setParameterFiveRemark(String parameterFiveRemark) {
        this.parameterFiveRemark = parameterFiveRemark;
    }

    public String getParameterFiveNote() {
        return parameterFiveNote;
    }

    public void setParameterFiveNote(String parameterFiveNote) {
        this.parameterFiveNote = parameterFiveNote;
    }

    public Boolean getParameterFiveRequired() {
        return parameterFiveRequired;
    }

    public void setParameterFiveRequired(Boolean parameterFiveRequired) {
        this.parameterFiveRequired = parameterFiveRequired;
    }

    public String getParameterSix() {
        return parameterSix;
    }

    public void setParameterSix(String parameterSix) {
        this.parameterSix = parameterSix;
    }

    public String getParameterSixRemark() {
        return parameterSixRemark;
    }

    public void setParameterSixRemark(String parameterSixRemark) {
        this.parameterSixRemark = parameterSixRemark;
    }

    public String getParameterSixNote() {
        return parameterSixNote;
    }

    public void setParameterSixNote(String parameterSixNote) {
        this.parameterSixNote = parameterSixNote;
    }

    public Boolean getParameterSixRequired() {
        return parameterSixRequired;
    }

    public void setParameterSixRequired(Boolean parameterSixRequired) {
        this.parameterSixRequired = parameterSixRequired;
    }

    public String getParameterSeven() {
        return parameterSeven;
    }

    public void setParameterSeven(String parameterSeven) {
        this.parameterSeven = parameterSeven;
    }

    public String getParameterSevenRemark() {
        return parameterSevenRemark;
    }

    public void setParameterSevenRemark(String parameterSevenRemark) {
        this.parameterSevenRemark = parameterSevenRemark;
    }

    public String getParameterSevenNote() {
        return parameterSevenNote;
    }

    public void setParameterSevenNote(String parameterSevenNote) {
        this.parameterSevenNote = parameterSevenNote;
    }

    public Boolean getParameterSevenRequired() {
        return parameterSevenRequired;
    }

    public void setParameterSevenRequired(Boolean parameterSevenRequired) {
        this.parameterSevenRequired = parameterSevenRequired;
    }

    public String getReturnRemark() {
        return returnRemark;
    }

    public void setReturnRemark(String returnRemark) {
        this.returnRemark = returnRemark;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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
        return "ApiManage{" +
        "apiManageId=" + apiManageId +
        ", appId=" + appId +
        ", callCode=" + callCode +
        ", apiStatus=" + apiStatus +
        ", apiType=" + apiType +
        ", apiName=" + apiName +
        ", apiCode=" + apiCode +
        ", parameterNum=" + parameterNum +
        ", parameterOne=" + parameterOne +
        ", parameterOneRemark=" + parameterOneRemark +
        ", parameterOneNote=" + parameterOneNote +
        ", parameterOneRequired=" + parameterOneRequired +
        ", parameterTwo=" + parameterTwo +
        ", parameterTwoRemark=" + parameterTwoRemark +
        ", parameterTwoNote=" + parameterTwoNote +
        ", parameterTwoRequired=" + parameterTwoRequired +
        ", parameterThree=" + parameterThree +
        ", parameterThreeRemark=" + parameterThreeRemark +
        ", parameterThreeNote=" + parameterThreeNote +
        ", parameterThreeRequired=" + parameterThreeRequired +
        ", parameterFour=" + parameterFour +
        ", parameterFourRemark=" + parameterFourRemark +
        ", parameterFourNote=" + parameterFourNote +
        ", parameterFourRequired=" + parameterFourRequired +
        ", parameterFive=" + parameterFive +
        ", parameterFiveRemark=" + parameterFiveRemark +
        ", parameterFiveNote=" + parameterFiveNote +
        ", parameterFiveRequired=" + parameterFiveRequired +
        ", parameterSix=" + parameterSix +
        ", parameterSixRemark=" + parameterSixRemark +
        ", parameterSixNote=" + parameterSixNote +
        ", parameterSixRequired=" + parameterSixRequired +
        ", parameterSeven=" + parameterSeven +
        ", parameterSevenRemark=" + parameterSevenRemark +
        ", parameterSevenNote=" + parameterSevenNote +
        ", parameterSevenRequired=" + parameterSevenRequired +
        ", returnRemark=" + returnRemark +
        ", remark=" + remark +
        ", sort=" + sort +
        ", revision=" + revision +
        ", createUser=" + createUser +
        ", createTime=" + createTime +
        ", updateUser=" + updateUser +
        ", updateTime=" + updateTime +
        "}";
    }
}
