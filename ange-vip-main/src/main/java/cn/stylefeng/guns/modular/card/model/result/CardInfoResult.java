package cn.stylefeng.guns.modular.card.model.result;

import lombok.Data;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 卡密表
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-04-20
 */
@Data
public class CardInfoResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 卡密ID
     */
    private Long cardId;

    /**
     * 应用ID
     */
    private Long appId;

    /**
     * 批次号
     */
    private String batchNo;

    /**
     * 卡类名称
     */
    private String cardTypeName;

    /**
     * 卡类ID
     */
    private Long cardTypeId;

    /**
     * 申请人ID
     */
    private Long userId;

    /**
     * 申请人名称
     */
    private String userName;

    /**
     * 是否通用 0-否；1-是
     */
    private Boolean isUniversal;

    /**
     * 卡密
     */
    private String cardCode;

    /**
     * 是否自定义时间
     */
    private Boolean isCustomTime;

    /**
     * 自定义时间值(天)
     */
    private Integer customTimeNum;

    /**
     * 状态 0-未激活；1-已激活；2-已过期；3-已禁用；4-已删除
     */
    private Integer cardStatus;

    /**
     * 绑定mac
     */
    private String cardMac;

    /**
     * 绑定ip
     */
    private String cardIp;

    /**
     * 卡密token
     */
    private String cardToken;

    /**
     * 激活时间
     */
    private Date activeTime;

    /**
     * 过期时间
     */
    private Date expireTime;

    /**
     * 绑机配置 0-默认；1-关闭；2-MAC；3-IP；4-混合；
     */
    private Integer cardBindType;

    /**
     * 绑机数量
     */
    private Integer cardBindNum;

    /**
     * 多开开关 0-默认；1-关闭；2-开启
     */
    private Integer cardOpenRange;

    /**
     * 单码登录方式 0-非顶号；1-顶号；
     */
    private Integer cardSignType;

    /**
     * 多开数量
     */
    private Integer cardOpenNum;

    /**
     * 卡密备注
     */
    private String cardRemark;

    /**
     * 卡密数据
     */
    private String cardData;

    /**
     * 禁用备注
     */
    private String prohibitRemark;

    /**
     * 是否加时 0-否；1-是
     */
    private Boolean whetherAddTime;

    /**
     * 加时天数
     */
    private Integer addDayNum;

    /**
     * 加时小时
     */
    private Integer addHourNum;

    /**
     * 加时分钟
     */
    private Integer addMinuteNum;

    /**
     * 加时时间
     */
    private Date addTime;

    /**
     * 登录次数
     */
    private Integer loginNum;

    /**
     * 解绑次数
     */
    private Integer unbindNum;

    /**
     * 解绑扣时（分钟）
     */
    private Integer unbindBuckleTime;

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
