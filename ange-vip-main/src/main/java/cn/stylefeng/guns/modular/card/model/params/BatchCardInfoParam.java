package cn.stylefeng.guns.modular.card.model.params;

import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 卡密表
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-04-20
 */
@Data
public class BatchCardInfoParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /**
     * 卡密Ids
     */
    private String ids;

    /**
     * 操作范围
     */
    private Integer operateFlag;

    /**
     * 操作类型
     */
    private String event;

    /**
     * 导出类型 0-txt；1-excel
     */
    private Integer exportFlag;

    /**
     * 导出字段 1-仅卡密；2-卡密|激活时间|到期时间；3-卡密|卡类名称|激活时间|到期时间；
     */
    private Integer exportField;

    /**
     * 分割符号
     */
    private String splitSymbol;

    /**
     * 卡密ID
     */
    private Long cardId;

    /**
     * 应用ID
     */
    private Long operateApp;

    /**
     * 卡类名称
     */
    private String cardTypeName;

    /**
     * 卡类ID
     */
    private Long cardType;

    /**
     * 状态 0-未激活；1-已激活；2-已过期；3-已禁用；4-已删除
     */
    private Integer cardStatus;

    /**
     * 卡密备注
     */
    private String cardRemark;

    /**
     * 卡密备注
     */
    private String cardData;

    /**
     * 禁用备注
     */
    private String prohibitRemark;

    /**
     * 加时天数
     */
    private Integer addDayNum;

    /**
     * 加时小时
     */
    private Integer addHourNum;

    /**
     * 加时分钟
     */
    private Integer addMinuteNum;

    /**
     * 加时时间
     */
    private Date addTime;

    /**
     * 创建人
     */
    private Long createUser;

    @Override
    public String checkParam() {
        return null;
    }

}
