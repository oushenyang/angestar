package cn.stylefeng.guns.modular.agent.model.result;

import lombok.Data;
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
public class AgentBuyCardResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 代理审核id
     */
    private Long agentBuyCardId;

    /**
     * 代理应用id
     */
    private Long agentAppId;

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

}
