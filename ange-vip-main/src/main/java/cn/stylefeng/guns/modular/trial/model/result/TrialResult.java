package cn.stylefeng.guns.modular.trial.model.result;

import lombok.Data;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 试用信息
 * </p>
 *
 * @author shenyang.ou
 * @since 2021-03-04
 */
@Data
public class TrialResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 试用id
     */
    private Long trialId;

    /**
     * 应用id
     */
    private Long appId;

    /**
     * mac
     */
    private String mac;

    /**
     * ip
     */
    private String ip;

    /**
     * 设备型号
     */
    private String model;

    /**
     * 试用次数
     */
    private Integer trialNum;

    /**
     * 试用时间
     */
    private Date trialTime;

    /**
     * 是否到期；0-未到期；1-已到期
     */
    private Boolean expire;

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

}
