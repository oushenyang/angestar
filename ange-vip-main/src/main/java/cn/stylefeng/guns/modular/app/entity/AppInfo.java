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
 * 软件表 
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-04-01
 */
@TableName("ag_app_info")
public class AppInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 软件id
     */
    @TableId(value = "app_id", type = IdType.ID_WORKER)
    private Long appId;

    /**
     * 应用编号
     */
    @TableField("app_num")
    private String appNum;

    /**
     * 应用头像
     */
    @TableField("app_head")
    private String appHead;

    /**
     * 开发者用户id
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 应用名
     */
    @TableField("app_name")
    private String appName;

    /**
     * 运行状态 0-收费；1-免费；2-关闭
     */
    @TableField("cydia_flag")
    private Integer cydiaFlag;

    /**
     * 软件公告
     */
    @TableField("app_notice")
    private String appNotice;

    /**
     * 卡密数量
     */
    @TableField("card_num")
    private Integer cardNum;

    /**
     * 账号数量
     */
    @TableField("account_num")
    private Integer accountNum;

    /**
     * 应用自定义数据1
     */
    @TableField("custom_data1")
    private String customData1;

    /**
     * 应用自定义数据2
     */
    @TableField("custom_data2")
    private String customData2;

    /**
     * 应用自定义数据2
     */
    @TableField("custom_data3")
    private String customData3;

    /**
     * 单码绑机策略 0-关闭；1-MAC；2-IP；3-混合
     */
    @TableField("code_bind_type")
    private Integer codeBindType;

    /**
     * 单码绑机选项 0-每天；1-永久；
     */
    @TableField("code_bind_option")
    private Integer codeBindOption;

    /**
     * 单码重绑次数
     */
    @TableField("code_bind_num")
    private Integer codeBindNum;

    /**
     * 单码重绑扣时
     */
    @TableField("code_bind_time")
    private Integer codeBindTime;

    /**
     * 账号绑机策略 0-关闭；1-MAC；2-IP；3-混合
     */
    @TableField("account_bind_type")
    private Integer accountBindType;

    /**
     * 账号绑机选项 0-每天；1-永久；
     */
    @TableField("account_bind_option")
    private Integer accountBindOption;

    /**
     * 账号重绑次数
     */
    @TableField("account_bind_num")
    private Integer accountBindNum;

    /**
     * 账号重绑扣时
     */
    @TableField("account_bind_time")
    private Integer accountBindTime;

    /**
     * 单码多开范围 0-关闭；1-单设备；2-单IP；3-所有设备
     */
    @TableField("code_open_range")
    private Integer codeOpenRange;

    /**
     * 单码登录方式 0-非顶号；1-顶号；
     */
    @TableField("code_sign_type")
    private Integer codeSignType;

    /**
     * 卡密清理间隔（取字典clear_space）
     */
    @TableField("code_clear_space")
    private Integer codeClearSpace;

    /**
     * 单码多开数量
     */
    @TableField("code_open_num")
    private Integer codeOpenNum;

    /**
     * 账号多开范围 0-关闭；1-单设备；2-单IP；3-所有设备
     */
    @TableField("account_open_range")
    private Integer accountOpenRange;

    /**
     * 账号登录方式 0-非顶号；1-顶号；
     */
    @TableField("account_sign_type")
    private Integer accountSignType;

    /**
     * 卡密清理间隔（取字典clear_space）
     */
    @TableField("account_clear_space")
    private Integer accountClearSpace;

    /**
     * 账号多开数量
     */
    @TableField("account_open_num")
    private Integer accountOpenNum;

    /**
     * 单码试用策略 0-关闭；1-时间；2-次数；
     */
    @TableField("code_try_type")
    private Integer codeTryType;

    /**
     * 单码试用时长
     */
    @TableField("code_try_time")
    private Integer codeTryTime;

    /**
     * 账号用户注册开关 0-否；1-是
     */
    @TableField("account_register_switch")
    private Boolean accountRegisterSwitch;

    /**
     * 账号注册限制 0-关闭；1-每天；2-永久；
     */
    @TableField("account_register_limit")
    private Integer accountRegisterLimit;

    /**
     * 账号注册次数
     */
    @TableField("account_register_num")
    private Integer accountRegisterNum;

    /**
     * 账号试用时间
     */
    @TableField("account_register_time")
    private Integer accountRegisterTime;


    /**
     * webApi加密范围 0-全部加密；1-仅加密api参数提交；2-仅加密api参数返回；
     */
    @TableField("web_algorithm_range")
    private Integer webAlgorithmRange;

    /**
     * webApi加密算法 0-关闭；1-DES；2-AES；
     */
    @TableField("web_algorithm_type")
    private Integer webAlgorithmType;

    /**
     * webApi加密key
     */
    @TableField("web_key")
    private String webKey;

    /**
     * webApi签名盐
     */
    @TableField("web_salt")
    private String webSalt;

    /**
     * 接收Sgin验证
     */
    @TableField("sign_receive")
    private String signReceive;

    /**
     * 输出Sgin验证
     */
    @TableField("sign_output")
    private String signOutput;

    /**
     * 外部验证：0-关闭；1-易游；2-万捷；
     */
    @TableField("other_sign")
    private Integer otherSign;

    /**
     * 验证地址
     */
    @TableField("proving_url")
    private String provingUrl;

    /**
     * 版本号id
     */
    @TableField("version_num")
    private Long versionNum;

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


    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getAppNum() {
        return appNum;
    }

    public void setAppNum(String appNum) {
        this.appNum = appNum;
    }

    public String getAppHead() {
        return appHead;
    }

    public void setAppHead(String appHead) {
        this.appHead = appHead;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Integer getCydiaFlag() {
        return cydiaFlag;
    }

    public void setCydiaFlag(Integer cydiaFlag) {
        this.cydiaFlag = cydiaFlag;
    }

    public String getAppNotice() {
        return appNotice;
    }

    public void setAppNotice(String appNotice) {
        this.appNotice = appNotice;
    }

    public Integer getCardNum() {
        return cardNum;
    }

    public void setCardNum(Integer cardNum) {
        this.cardNum = cardNum;
    }

    public Integer getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(Integer accountNum) {
        this.accountNum = accountNum;
    }

    public String getCustomData1() {
        return customData1;
    }

    public void setCustomData1(String customData1) {
        this.customData1 = customData1;
    }

    public String getCustomData2() {
        return customData2;
    }

    public void setCustomData2(String customData2) {
        this.customData2 = customData2;
    }

    public String getCustomData3() {
        return customData3;
    }

    public void setCustomData3(String customData3) {
        this.customData3 = customData3;
    }

    public Integer getCodeBindType() {
        return codeBindType;
    }

    public void setCodeBindType(Integer codeBindType) {
        this.codeBindType = codeBindType;
    }

    public Integer getCodeBindOption() {
        return codeBindOption;
    }

    public void setCodeBindOption(Integer codeBindOption) {
        this.codeBindOption = codeBindOption;
    }

    public Integer getCodeBindNum() {
        return codeBindNum;
    }

    public void setCodeBindNum(Integer codeBindNum) {
        this.codeBindNum = codeBindNum;
    }

    public Integer getCodeBindTime() {
        return codeBindTime;
    }

    public void setCodeBindTime(Integer codeBindTime) {
        this.codeBindTime = codeBindTime;
    }

    public Integer getAccountBindType() {
        return accountBindType;
    }

    public void setAccountBindType(Integer accountBindType) {
        this.accountBindType = accountBindType;
    }

    public Integer getAccountBindOption() {
        return accountBindOption;
    }

    public void setAccountBindOption(Integer accountBindOption) {
        this.accountBindOption = accountBindOption;
    }

    public Integer getAccountBindNum() {
        return accountBindNum;
    }

    public void setAccountBindNum(Integer accountBindNum) {
        this.accountBindNum = accountBindNum;
    }

    public Integer getAccountBindTime() {
        return accountBindTime;
    }

    public void setAccountBindTime(Integer accountBindTime) {
        this.accountBindTime = accountBindTime;
    }

    public Integer getCodeOpenRange() {
        return codeOpenRange;
    }

    public void setCodeOpenRange(Integer codeOpenRange) {
        this.codeOpenRange = codeOpenRange;
    }

    public Integer getCodeSignType() {
        return codeSignType;
    }

    public void setCodeSignType(Integer codeSignType) {
        this.codeSignType = codeSignType;
    }

    public Integer getCodeClearSpace() {
        return codeClearSpace;
    }

    public void setCodeClearSpace(Integer codeClearSpace) {
        this.codeClearSpace = codeClearSpace;
    }

    public Integer getCodeOpenNum() {
        return codeOpenNum;
    }

    public void setCodeOpenNum(Integer codeOpenNum) {
        this.codeOpenNum = codeOpenNum;
    }

    public Integer getAccountOpenRange() {
        return accountOpenRange;
    }

    public void setAccountOpenRange(Integer accountOpenRange) {
        this.accountOpenRange = accountOpenRange;
    }

    public Integer getAccountSignType() {
        return accountSignType;
    }

    public void setAccountSignType(Integer accountSignType) {
        this.accountSignType = accountSignType;
    }

    public Integer getAccountClearSpace() {
        return accountClearSpace;
    }

    public void setAccountClearSpace(Integer accountClearSpace) {
        this.accountClearSpace = accountClearSpace;
    }

    public Integer getAccountOpenNum() {
        return accountOpenNum;
    }

    public void setAccountOpenNum(Integer accountOpenNum) {
        this.accountOpenNum = accountOpenNum;
    }

    public Integer getCodeTryType() {
        return codeTryType;
    }

    public void setCodeTryType(Integer codeTryType) {
        this.codeTryType = codeTryType;
    }

    public Integer getCodeTryTime() {
        return codeTryTime;
    }

    public void setCodeTryTime(Integer codeTryTime) {
        this.codeTryTime = codeTryTime;
    }

    public Boolean getAccountRegisterSwitch() {
        return accountRegisterSwitch;
    }

    public void setAccountRegisterSwitch(Boolean accountRegisterSwitch) {
        this.accountRegisterSwitch = accountRegisterSwitch;
    }

    public Integer getAccountRegisterLimit() {
        return accountRegisterLimit;
    }

    public void setAccountRegisterLimit(Integer accountRegisterLimit) {
        this.accountRegisterLimit = accountRegisterLimit;
    }

    public Integer getAccountRegisterNum() {
        return accountRegisterNum;
    }

    public void setAccountRegisterNum(Integer accountRegisterNum) {
        this.accountRegisterNum = accountRegisterNum;
    }

    public Integer getAccountRegisterTime() {
        return accountRegisterTime;
    }

    public void setAccountRegisterTime(Integer accountRegisterTime) {
        this.accountRegisterTime = accountRegisterTime;
    }

    public Integer getWebAlgorithmRange() {
        return webAlgorithmRange;
    }

    public void setWebAlgorithmRange(Integer webAlgorithmRange) {
        this.webAlgorithmRange = webAlgorithmRange;
    }

    public Integer getWebAlgorithmType() {
        return webAlgorithmType;
    }

    public void setWebAlgorithmType(Integer webAlgorithmType) {
        this.webAlgorithmType = webAlgorithmType;
    }

    public String getWebKey() {
        return webKey;
    }

    public void setWebKey(String webKey) {
        this.webKey = webKey;
    }

    public String getWebSalt() {
        return webSalt;
    }

    public void setWebSalt(String webSalt) {
        this.webSalt = webSalt;
    }

    public String getSignReceive() {
        return signReceive;
    }

    public void setSignReceive(String signReceive) {
        this.signReceive = signReceive;
    }

    public String getSignOutput() {
        return signOutput;
    }

    public void setSignOutput(String signOutput) {
        this.signOutput = signOutput;
    }

    public Integer getOtherSign() {
        return otherSign;
    }

    public void setOtherSign(Integer otherSign) {
        this.otherSign = otherSign;
    }

    public String getProvingUrl() {
        return provingUrl;
    }

    public void setProvingUrl(String provingUrl) {
        this.provingUrl = provingUrl;
    }

    public Long getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(Long versionNum) {
        this.versionNum = versionNum;
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
        return "AppInfo{" +
        "appId=" + appId +
        ", appNum=" + appNum +
        ", userId=" + userId +
        ", appName=" + appName +
        ", appHead=" + appHead +
        ", cydiaFlag=" + cydiaFlag +
        ", appNotice=" + appNotice +
        ", customData1=" + customData1 +
        ", customData2=" + customData2 +
        ", customData3=" + customData3 +
        ", codeBindType=" + codeBindType +
        ", codeBindOption=" + codeBindOption +
        ", codeBindNum=" + codeBindNum +
        ", codeBindTime=" + codeBindTime +
        ", accountBindType=" + accountBindType +
        ", accountBindOption=" + accountBindOption +
        ", accountBindNum=" + accountBindNum +
        ", accountBindTime=" + accountBindTime +
        ", codeOpenRange=" + codeOpenRange +
        ", codeClearSpace=" + codeClearSpace +
        ", codeSignType=" + codeSignType +
        ", codeOpenNum=" + codeOpenNum +
        ", accountOpenRange=" + accountOpenRange +
        ", accountSignType=" + accountSignType +
        ", accountClearSpace=" + accountClearSpace +
        ", accountOpenNum=" + accountOpenNum +
        ", codeTryType=" + codeTryType +
        ", codeTryTime=" + codeTryTime +
        ", accountRegisterSwitch=" + accountRegisterSwitch +
        ", accountRegisterLimit=" + accountRegisterLimit +
        ", accountRegisterNum=" + accountRegisterNum +
        ", accountRegisterTime=" + accountRegisterTime +
        ", webAlgorithmType=" + webAlgorithmType +
        ", webKey=" + webKey +
        ", webSalt=" + webSalt +
        ", versionNum=" + versionNum +
        ", createUser=" + createUser +
        ", createTime=" + createTime +
        ", updateUser=" + updateUser +
        ", updateTime=" + updateTime +
        "}";
    }
}
