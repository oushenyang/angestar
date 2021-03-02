package cn.stylefeng.guns.modular.account.model.result;

import lombok.Data;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 用户账号表
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-05-14
 */
@Data
public class AccountInfoResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 用户账号id
     */
    private Long accountId;

    /**
     * 应用ID
     */
    private Long appId;

    /**
     * 账号
     */
    private String account;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * QQ号
     */
    private String qq;

    /**
     * 密码
     */
    private String password;

    /**
     * 状态 0-正常；1-冻结；2-已删除
     */
    private Integer accountStatus;

    /**
     * 注册时间
     */
    private Date registrationTime;

    /**
     * 到期时间
     */
    private Date expireTime;

    /**
     * 用户点数
     */
    private Integer accountPoint;

    /**
     * 剩余点数
     */
    private Integer surplusPoint;

    /**
     * 推荐人
     */
    private String referrer;

    /**
     * 推荐码
     */
    private String referrerCode;

    /**
     * 用户数据
     */
    private String accountData;

    /**
     * 用户token
     */
    private String accountToken;

    /**
     * 绑机配置 0-默认；1-关闭；2-MAC；3-IP；4-混合；
     */
    private Integer accountBindType;

    /**
     * 绑机数量
     */
    private Integer accountBindNum;

    /**
     * 多开开关 0-默认；1-关闭；2-开启
     */
    private Integer accountOpenRange;

    /**
     * 多开数量
     */
    private Integer accountOpenNum;

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
