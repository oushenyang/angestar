package cn.stylefeng.guns.modular.agent.model.result;

import com.baomidou.mybatisplus.annotation.TableField;
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
     * 代理权限id
     */
    private Long agentPowerId;

    /**
     * 父级代理应用id
     */
    private Long agentAppIdPid;

    /**
     * 父级代理应用ids
     */
    private String agentAppIdPids;

    /**
     * 代理应用编号
     */
    private String agentAppNo;

    /**
     * 应用快捷页面编号
     */
    private String agentAppQuick;

    /**
     * 应用id
     */
    private Long appId;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 应用头像
     */
    private String appHead;

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
     * 上级代理用户名称
     */
    private String pidUserName;

    /**
     * 账号未使用数量
     */
    private String accountNoActiveNum;

    /**
     * 账号已使用数量
     */
    private String accountActiveNum;

    /**
     * 账号总数量
     */
    private String accountAllNum;

    /**
     * 卡密未使用数量
     */
    private String cardNoActiveNum;

    /**
     * 卡密已使用数量
     */
    private String cardActiveNum;

    /**
     * 卡密总数量
     */
    private String cardAllNum;

    /**
     * 代理用户名称
     */
    private String agentUserAccount;

    /**
     * 代理等级
     */
    private Boolean rose;

    /**
     * 代理等级
     */
    private Integer agentGrade;
    private String agentGradeName;

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
