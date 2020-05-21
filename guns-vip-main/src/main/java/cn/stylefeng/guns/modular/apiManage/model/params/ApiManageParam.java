package cn.stylefeng.guns.modular.apiManage.model.params;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 接口管理 
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-05-21
 */
@Data
public class ApiManageParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 接口管理id
     */
    private Long apiManageId;

    /**
     * 应用id
     */
    private Long appId;

    /**
     * 调用码
     */
    private String callCode;

    /**
     * 接口状态 0-开启；1-关闭
     */
    private Integer apiStatus;

    /**
     * 接口类别（取字典）
     */
    private String apiType;

    /**
     * 接口名称
     */
    private String apiName;

    /**
     * 接口编码
     */
    private String apiCode;

    /**
     * 参数数量
     */
    private Integer parameterNum;

    /**
     * 参数一
     */
    private String parameterOne;

    /**
     * 参数一说明
     */
    private String parameterOneRemark;

    /**
     * 参数二
     */
    private String parameterTwo;

    /**
     * 参数二说明
     */
    private String parameterTwoRemark;

    /**
     * 参数三
     */
    private String parameterThree;

    /**
     * 参数三说明
     */
    private String parameterThreeRemark;

    /**
     * 参数四
     */
    private String parameterFour;

    /**
     * 参数四说明
     */
    private String parameterFourRemark;

    /**
     * 参数五
     */
    private String parameterFive;

    /**
     * 参数五说明
     */
    private String parameterFiveRemark;

    /**
     * 参数六
     */
    private String parameterSix;

    /**
     * 参数六说明
     */
    private String parameterSixRemark;

    /**
     * 参数七
     */
    private String parameterSeven;

    /**
     * 参数七说明
     */
    private String parameterSevenRemark;

    /**
     * 返回说明
     */
    private String returnRemark;

    /**
     * 备注
     */
    private String remark;

    /**
     * 排序
     */
    private Integer sort;

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
