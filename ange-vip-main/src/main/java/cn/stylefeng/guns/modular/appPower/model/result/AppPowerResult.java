package cn.stylefeng.guns.modular.appPower.model.result;

import lombok.Data;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 应用授权表 
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-10-29
 */
@Data
public class AppPowerResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 应用授权id
     */
    private Long appPowerId;

    /**
     * 应用名
     */
    private String appName;

    /**
     * 应用入口
     */
    private String applicationName;

    /**
     * 应用分类编码
     */
    private String appTypeCode;

    /**
     * 签名验证
     */
    private String sign;

    /**
     * 是否授权 0-否；1-是
     */
    private Boolean whetherLegal;

    /**
     * 是否显示 0-否；1-是
     */
    private Boolean whetherShow;

    /**
     * 是否制裁 0-否；1-是
     */
    private Boolean whetherSanction;

    /**
     * 应用自定义数据
     */
    private String customData;

    /**
     * 创建人
     */
    private Long createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private Long updateUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 制裁时间
     */
    private Date sanctionTime;

}
