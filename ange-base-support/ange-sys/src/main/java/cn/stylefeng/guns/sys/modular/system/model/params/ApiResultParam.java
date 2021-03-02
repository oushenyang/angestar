package cn.stylefeng.guns.sys.modular.system.model.params;

import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 接口自定义返回
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-07-31
 */
@Data
public class ApiResultParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 接口自定义返回id
     */
    private Long apiResultId;

    /**
     * 应用id
     */
    private Long appId;
    private Integer type;

    /**
     * 返回类别
     */
    private String resultType;

    /**
     * 是否成功：0-否；1-是
     */
    private Boolean resultSuccess;

    /**
     * 变量编码集合
     */
    private String resultVariables;

    /**
     * 默认返回码
     */
    private Integer resultCode;

    /**
     * 默认返回数据
     */
    private String resultData;

    /**
     * 自定义返回数据
     */
    private String customResultData;

    /**
     * 返回说明
     */
    private String resultRemark;

    /**
     * 是否可编辑：0-否；1-是
     */
    private Boolean whetherEdit;

    /**
     * 输出格式：0-JSON；1-XML；2-HTML；3-TEXT
     */
    private Integer outputFormat;

    /**
     * 排序
     */
    private Integer sort;

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
