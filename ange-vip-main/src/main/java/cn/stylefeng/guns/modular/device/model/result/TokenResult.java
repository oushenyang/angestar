package cn.stylefeng.guns.modular.device.model.result;

import lombok.Data;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 登录信息
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-08-02
 */
@Data
public class TokenResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 登录id
     */
    private Long tokenId;

    /**
     * 应用id
     */
    private Long appId;

    /**
     * 卡密或账号id
     */
    private Long cardOrUserId;

    /**
     * 卡密或账号
     */
    private String cardOrUserCode;

    /**
     * 卡密类型；1-单码；2-注册码
     */
    private Integer cardType;

    /**
     * 版本号
     */
    private String editionNum;

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
     * 登录次数
     */
    private Integer loginNum;

    /**
     * 登录时间
     */
    private Date loginTime;

    /**
     * 校验时间
     */
    private Date checkTime;

    /**
     * token
     */
    private String token;

    /**
     * 版本编号
     */
    private String editionSerial;

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
