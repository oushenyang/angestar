package cn.stylefeng.guns.modular.agent.model.result;

import lombok.Data;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 代理软件表 
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-05-20
 */
@Data
public class AgentAppResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 代理应用id
     */
    private Long agentAppId;

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
     * 父代理应用id
     */
    private Long pid;

    /**
     * 父级ids
     */
    private String pids;

    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 状态 0-正常；1-冻结
     */
    private Integer status;

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