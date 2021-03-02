package cn.stylefeng.guns.modular.appPower.model.params;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 应用类型表 
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-10-29
 */
@Data
public class AppPowerTypeParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 应用授权id
     */
    private Long appPowerTypeId;

    /**
     * 分类应用名
     */
    private String appName;

    /**
     * 分类编码
     */
    private String appCode;

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

    @Override
    public String checkParam() {
        return null;
    }

}
