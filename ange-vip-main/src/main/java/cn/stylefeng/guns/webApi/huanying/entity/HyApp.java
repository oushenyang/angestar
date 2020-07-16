package cn.stylefeng.guns.webApi.huanying.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

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

    @TableField("name")
    private String name;

    @TableField("package")
    private String packAge;

    @TableField("fakedata")
    private Integer fakedata;


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

    @Override
    public String toString() {
        return "HyApp{" +
        "appinfoid=" + appinfoid +
        ", utDid=" + utDid +
        ", appuserid=" + appuserid +
        ", name=" + name +
        ", package=" + packAge +
        ", fakedata=" + fakedata +
        "}";
    }
}
