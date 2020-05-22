package cn.stylefeng.guns.modular.agent.model.result;

import lombok.Data;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 代理卡密
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-05-22
 */
@Data
public class AgentCardResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 代理卡密id
     */
    private Long agentCardId;

    /**
     * 代理应用id
     */
    private Long agentAppId;

    /**
     * 应用id
     */
    private Long appId;

    /**
     * 卡类Id
     */
    private Long cardTypeId;

    /**
     * 卡类名称
     */
    private String cardTypeName;

    /**
     * 卡类类型 0-单码卡密；1-通用卡密；2-注册卡密
     */
    private Integer cardType;

    /**
     * 市场价格
     */
    private BigDecimal marketPrice;

    /**
     * 代理价格
     */
    private BigDecimal agentPrice;

    /**
     * 下级代理价格
     */
    private BigDecimal levelAgentPrice;

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

}
