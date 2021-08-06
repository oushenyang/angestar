package cn.stylefeng.guns.modular.app.model.params;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 应用反馈列表
 * </p>
 *
 * @author shenyang.ou
 * @since 2021-08-06
 */
@Data
public class AppFeedbackParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 反馈id
     */
    private Long feedbackId;

    /**
     * 应用id
     */
    private Long appId;

    /**
     * 反馈的内容
     */
    private String context;

    /**
     * 版本号
     */
    private String edition;

    /**
     * 设备型号
     */
    private String model;

    /**
     * 机器码
     */
    private String mac;

    /**
     * 联系方式
     */
    private String links;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建人
     */
    private Long createUser;

    @Override
    public String checkParam() {
        return null;
    }

}
