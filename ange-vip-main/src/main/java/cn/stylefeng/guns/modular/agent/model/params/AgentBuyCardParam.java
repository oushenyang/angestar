package cn.stylefeng.guns.modular.agent.model.params;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 代理购卡记录
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-12-11
 */
@Data
public class AgentBuyCardParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 代理审核id
     */
    private Long agentBuyCardId;

    /**
     * 菜单类型：1-开发者菜单；2-代理者菜单
     */
    private Integer type;

    /**
     * 应用id
     */
    private Long appId;

    /**
     * 开发者用户id
     */
    private Long developerUserId;

    /**
     * 代理用户id
     */
    private Long agentUserId;

    /**
     * 代理用户名称
     */
    private String agentUserName;

    /**
     * 代理用户账号
     */
    private String agentUserAccount;

    /**
     * 代理等级
     */
    private Integer agentGrade;

    /**
     * 代理价格
     */
    private BigDecimal agentPrice;

    /**
     * 购卡类型：1-一级代理充值；2-一级代理购卡；3-二级代理充值；4-二级代理购卡返差价
     */
    private Integer buyCardType;

    /**
     * 购买数量
     */
    private Integer buyNum;

    /**
     * 卡密批次号
     */
    private String batchNo;

    /**
     * 卡类Id
     */
    private Long cardTypeId;

    /**
     * 卡密类型：0-单码卡密；1-通用卡密；2-注册卡密
     */
    private Integer cardType;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 审核时间
     */
    private Date examineTime;

    /**
     * 明细
     */
    private String detailed;

    /**
     * 备注
     */
    private String remark;

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
