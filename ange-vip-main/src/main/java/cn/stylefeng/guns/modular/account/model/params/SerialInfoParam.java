package cn.stylefeng.guns.modular.account.model.params;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 注册码表
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-05-29
 */
@Data
public class SerialInfoParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 注册码id
     */
    private Long serialId;
    private Integer type;

    /**
     * 应用ID
     */
    private Long appId;

    /**
     * 代理应用ID
     */
    private Long actCardAppId;

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
     * 注册码
     */
    private String serialCode;

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
     * 换绑数
     */
    private Integer changeBindNum;

    /**
     * 激活时间
     */
    private Date activeTime;

    /**
     * 过期时间
     */
    private Date expireTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 乐观锁
     */
    private Integer revision;

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
