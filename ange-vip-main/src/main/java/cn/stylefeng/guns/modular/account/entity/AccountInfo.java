package cn.stylefeng.guns.modular.account.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 用户账号表
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-05-14
 */
@TableName("ag_account_info")
public class AccountInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户账号id
     */
    @TableId(value = "account_id", type = IdType.ID_WORKER)
    private Long accountId;

    /**
     * 应用ID
     */
    @TableField("app_id")
    private Long appId;

    /**
     * 账号
     */
    @TableField("account")
    private String account;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;

    /**
     * QQ号
     */
    @TableField("qq")
    private String qq;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 状态 0-正常；1-冻结；2-已删除
     */
    @TableField("account_status")
    private Integer accountStatus;

    /**
     * 注册时间
     */
    @TableField("registration_time")
    private Date registrationTime;

    /**
     * 到期时间
     */
    @TableField("expire_time")
    private Date expireTime;

    /**
     * 用户点数
     */
    @TableField("account_point")
    private Integer accountPoint;

    /**
     * 剩余点数
     */
    @TableField("surplus_point")
    private Integer surplusPoint;

    /**
     * 推荐人
     */
    @TableField("referrer")
    private String referrer;

    /**
     * 推荐码
     */
    @TableField("referrer_code")
    private String referrerCode;

    /**
     * 用户数据
     */
    @TableField("account_data")
    private String accountData;

    /**
     * 用户token
     */
    @TableField("account_token")
    private String accountToken;

    /**
     * 绑机配置 0-默认；1-关闭；2-MAC；3-IP；4-混合；
     */
    @TableField("account_bind_type")
    private Integer accountBindType;

    /**
     * 绑机数量
     */
    @TableField("account_bind_num")
    private Integer accountBindNum;

    /**
     * 多开开关 0-默认；1-关闭；2-开启
     */
    @TableField("account_open_range")
    private Integer accountOpenRange;

    /**
     * 多开数量
     */
    @TableField("account_open_num")
    private Integer accountOpenNum;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

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


    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Integer accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Date getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Date registrationTime) {
        this.registrationTime = registrationTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Integer getAccountPoint() {
        return accountPoint;
    }

    public void setAccountPoint(Integer accountPoint) {
        this.accountPoint = accountPoint;
    }

    public Integer getSurplusPoint() {
        return surplusPoint;
    }

    public void setSurplusPoint(Integer surplusPoint) {
        this.surplusPoint = surplusPoint;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public String getReferrerCode() {
        return referrerCode;
    }

    public void setReferrerCode(String referrerCode) {
        this.referrerCode = referrerCode;
    }

    public String getAccountData() {
        return accountData;
    }

    public void setAccountData(String accountData) {
        this.accountData = accountData;
    }

    public String getAccountToken() {
        return accountToken;
    }

    public void setAccountToken(String accountToken) {
        this.accountToken = accountToken;
    }

    public Integer getAccountBindType() {
        return accountBindType;
    }

    public void setAccountBindType(Integer accountBindType) {
        this.accountBindType = accountBindType;
    }

    public Integer getAccountBindNum() {
        return accountBindNum;
    }

    public void setAccountBindNum(Integer accountBindNum) {
        this.accountBindNum = accountBindNum;
    }

    public Integer getAccountOpenRange() {
        return accountOpenRange;
    }

    public void setAccountOpenRange(Integer accountOpenRange) {
        this.accountOpenRange = accountOpenRange;
    }

    public Integer getAccountOpenNum() {
        return accountOpenNum;
    }

    public void setAccountOpenNum(Integer accountOpenNum) {
        this.accountOpenNum = accountOpenNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        return "AccountInfo{" +
        "accountId=" + accountId +
        ", appId=" + appId +
        ", account=" + account +
        ", email=" + email +
        ", phone=" + phone +
        ", qq=" + qq +
        ", password=" + password +
        ", accountStatus=" + accountStatus +
        ", registrationTime=" + registrationTime +
        ", expireTime=" + expireTime +
        ", accountPoint=" + accountPoint +
        ", surplusPoint=" + surplusPoint +
        ", referrer=" + referrer +
        ", referrerCode=" + referrerCode +
        ", accountData=" + accountData +
        ", accountToken=" + accountToken +
        ", accountBindType=" + accountBindType +
        ", accountBindNum=" + accountBindNum +
        ", accountOpenRange=" + accountOpenRange +
        ", accountOpenNum=" + accountOpenNum +
        ", remark=" + remark +
        ", createUser=" + createUser +
        ", createTime=" + createTime +
        ", updateUser=" + updateUser +
        ", updateTime=" + updateTime +
        "}";
    }
}
