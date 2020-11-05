package cn.stylefeng.guns.modular.appPower.model.params;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
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
public class AppPowerParam implements Serializable, BaseValidatingParam {

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
     * 是否制裁 0-否；1-是
     */
    private Boolean whetherSanction;

    /**
     * 是否显示 0-否；1-是
     */
    private Boolean whetherShow;

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

    @Override
    public String checkParam() {
        return null;
    }

}
