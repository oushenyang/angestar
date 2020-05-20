package cn.stylefeng.guns.modular.agent.model.params;

import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 代理充值查询参数
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-05-20
 */
@Data
public class AgentAppRechargeParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 代理应用id
     */
    private Long agentAppId;

    /**
     * 充值类型 0-充值;1-扣款
     */
    private Integer rechargeType;

    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 充值余额
     */
    private BigDecimal rechargeBalance;

    @Override
    public String checkParam() {
        return null;
    }

}
