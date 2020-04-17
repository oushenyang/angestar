package cn.stylefeng.guns.modular.card.model.params;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 单码卡类列表 
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-04-16
 */
@Data
public class CodeCardTypeParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 卡类Id
     */
    private Long cardTypeId;

    /**
     * 所属软件
     */
    private Long appId;

    /**
     * 卡类名称
     */
    private String cardTypeName;

    /**
     * 卡类时间类型  0-分；1-时；2-天；3-周；4-月；5-季；6-年
     */
    private Integer cardTimeType;

    /**
     * 卡值
     */
    private Integer cardTypeData;

    /**
     * 卡密前缀
     */
    private String cardTypePrefix;

    /**
     * 卡密规则 0-大写字母+数字；1-小写字母+数字；2-全大写字母；3-全小写字母；4-全数字；
     */
    private Integer cardTypeRule;

    /**
     * 卡密长度
     */
    private Integer cardTypeLength;

    /**
     * 售价
     */
    private BigDecimal cardTypePrice;

    /**
     * 代理售价
     */
    private BigDecimal cardTypeAgentPrice;

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
