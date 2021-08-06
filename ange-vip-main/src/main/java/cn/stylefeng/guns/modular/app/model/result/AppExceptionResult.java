package cn.stylefeng.guns.modular.app.model.result;

import lombok.Data;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 应用异常列表
 * </p>
 *
 * @author shenyang.ou
 * @since 2021-08-06
 */
@Data
public class AppExceptionResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 异常id
     */
    private Long exceptionId;

    /**
     * 应用id
     */
    private Long appId;

    /**
     * 应用名称
     */
    private Long appName;

    /**
     * 异常的标题
     */
    private String title;

    /**
     * 异常的内容
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
     * 异常次数
     */
    private Integer exceptionNum;

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

}
