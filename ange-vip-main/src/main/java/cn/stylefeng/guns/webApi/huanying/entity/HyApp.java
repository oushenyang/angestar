package cn.stylefeng.guns.webApi.huanying.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 幻影破解
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-07-16
 */
@TableName("pj_hy_app")
public class HyApp implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "appinfoid", type = IdType.ID_WORKER)
    private String appinfoid;

    @TableField("ut_did")
    private String utDid;

    @TableField("appuserid")
    private Integer appuserid;

    @TableField("app_code")
    private String appCode;

    @TableField("app_name")
    private String appName;

    @TableField("name")
    private String name;

    @TableField("package")
    private String packAge;

    @TableField("fakedata")
    private Integer fakedata;

    @TableField("sign")
    private String sign;

    @TableField("application_name")
    private String applicationName;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;


    public String getAppinfoid() {
        return appinfoid;
    }

    public void setAppinfoid(String appinfoid) {
        this.appinfoid = appinfoid;
    }

    public String getUtDid() {
        return utDid;
    }

    public void setUtDid(String utDid) {
        this.utDid = utDid;
    }

    public Integer getAppuserid() {
        return appuserid;
    }

    public void setAppuserid(Integer appuserid) {
        this.appuserid = appuserid;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackAge() {
        return packAge;
    }

    public void setPackAge(String packAge) {
        this.packAge = packAge;
    }

    public Integer getFakedata() {
        return fakedata;
    }

    public void setFakedata(Integer fakedata) {
        this.fakedata = fakedata;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "HyApp{" +
        "appinfoid=" + appinfoid +
        ", utDid=" + utDid +
        ", appuserid=" + appuserid +
        ", appCode=" + appCode +
        ", appName=" + appName +
        ", name=" + name +
        ", package=" + packAge +
        ", fakedata=" + fakedata +
        ", sign=" + sign +
        ", applicationName=" + applicationName +
        ", createTime=" + createTime +
        "}";
    }
}
