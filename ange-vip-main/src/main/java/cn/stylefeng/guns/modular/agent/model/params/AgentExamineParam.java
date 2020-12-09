package cn.stylefeng.guns.modular.agent.model.params;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 代理审核表
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-12-09
 */
@Data
public class AgentExamineParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 代理审核id
     */
    private Long agentExamineId;

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
     * 申请理由
     */
    private String applyReason;

    /**
     * 申请类型：1-申请代理；2-邀请代理
     */
    private Integer applyType;

    /**
     * 审核状态：1-等待开发者审核；2-等待代理审核；3-开发者拒绝；4-代理拒绝；5-代理成功
     */
    private Integer examineStatus;

    /**
     * 审核时间
     */
    private Date examineTime;

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
