package cn.stylefeng.guns.modular.agent.model.params;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 代理权限
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-05-20
 */
@Data
public class AgentPowerParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单类型：1-开发者菜单；2-代理者菜单
     */
    private Integer type;

    /**
     * 代理权限id
     */
    private Long agentPowerId;

    /**
     * 代理应用id
     */
    private Long agentAppId;

    /**
     * 代理应用id
     */
    private Long AppId;

    /**
     * 卡密生成 0-否；1-是；
     */
    private Boolean cardCreate;

    /**
     * 卡密禁用 0-否；1-是；
     */
    private Boolean cardDisable;

    /**
     * 卡密查看 0-否；1-是；
     */
    private Boolean cardLook;

    /**
     * 卡密数据 0-否；1-是；
     */
    private Boolean cardData;

    /**
     * 卡密加时 0-否；1-是；
     */
    private Boolean cardTime;

    /**
     * 卡密删除 0-否；1-是；
     */
    private Boolean cardDelete;

    /**
     * 充值卡生成 0-否；1-是；
     */
    private Boolean accountCreate;

    /**
     * 账号禁用 0-否；1-是；
     */
    private Boolean accountDisable;

    /**
     * 修改密码 0-否；1-是；
     */
    private Boolean accountEditPassword;

    /**
     * 账号删除 0-否；1-是；
     */
    private Boolean accountDelete;

    /**
     * 账号加时 0-否；1-是；
     */
    private Boolean accountTime;

    /**
     * 账号数据 0-否；1-是；
     */
    private Boolean accountData;

    /**
     * 查询用户 0-否；1-是；
     */
    private Boolean accountQuery;

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
