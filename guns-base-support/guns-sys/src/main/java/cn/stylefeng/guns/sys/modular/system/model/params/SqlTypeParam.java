package cn.stylefeng.guns.sys.modular.system.model.params;

import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * sql类型表
 * </p>
 *
 * @author stylefeng
 * @since 2019-03-13
 */
@Data
public class SqlTypeParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * sql类型id
     */
    private Long sqlTypeId;

    /**
     * 是否是系统sql，Y-是，N-否
     */
    private String systemFlag;

    /**
     * sql类型编码
     */
    private String code;

    /**
     * sql类型名称
     */
    private String name;

    /**
     * sql描述
     */
    private String description;

    /**
     * 状态(字典)
     */
    private String status;

    /**
     * 查询条件
     */
    private String condition;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * sql类型编码
     */
    private String sqlTypeCode;

    @Override
    public String checkParam() {
        return null;
    }

}
