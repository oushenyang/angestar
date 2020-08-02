package cn.stylefeng.guns.modular.card.model.result;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 卡密表
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-04-20
 */
@Data
public class CardInfoApi implements Serializable {

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
     * 多开数量
     */
    private Integer cardOpenNum;

    /**
     * 卡密备注
     */
    private String cardRemark;

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
     * 卡类时间类型  0-分；1-时；2-天；3-周；4-月；5-年
     */
    private Integer cardTimeType;

    /**
     * 卡值
     */
    private Integer cardTypeData;

}
