package cn.stylefeng.guns.modular.card.model.params;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
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
public class CardInfoParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 卡密ID
     */
    private Long cardId;

    /**
     * 导入范围 0-单个卡密；1-多个卡密
     */
    private Integer operateFlag;

    /**
     * 多个卡密 格式：卡密#卡密类型#激活时间#到期时间
     */
    private String txtMoreCard;

    private Integer type;

    /**
     * 应用ID
     */
    private Long appId;

    /**
     * 代理应用id
     */
    private Long agentAppId;

    /**
     * 代理应用ID
     */
    private Long actCardAppId;

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
    private Long userIdParam;

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
     * 卡密前缀
     */
    private String cardTypePrefix;

    /**
     * 新增数量
     */
    private Integer addNum;

    /**
     * 是否生成后激活
     */
    private Boolean isActivation;

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
    private String activeTimeStr;
    private String activeTimeStart;
    private String activeTimeEnd;

    /**
     * 过期时间
     */
    private Date expireTime;
    private String expireTimeStr;
    private String expireTimeStart;
    private String expireTimeEnd;

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
     * 创建人
     */
    private Long createUser;

    /**
     * 开发者Id
     */
    private Long developerUserId;

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
     * 创建时间
     */
    private Date createTime;
    private String createTimeStr;
    private String createTimeStart;
    private String createTimeEnd;

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
