package cn.stylefeng.guns.sys.modular.system.model.result;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * sql类型表
 * </p>
 *
 * @author stylefeng
 * @since 2019-03-13
 */
@Data
public class SqlTypeResult implements Serializable {

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
     * sql语句
     */
    private String description;

    /**
     * 状态(字典)
     */
    private String status;

    /**
     * 添加时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private Long createUser;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 修改人
     */
    private Long updateUser;

    /**
     * 排序
     */
    private Integer sort;

}
