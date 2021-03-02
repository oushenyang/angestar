package cn.stylefeng.guns.modular.remote.model.result;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 远程数据
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-05-12
 */
@Data
public class RemoteDataResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 数据id
     */
    private Long dataId;

    /**
     * 应用ID
     */
    private Long appId;

    /**
     * 数据编码
     */
    private String dataCode;

    /**
     * 参数一
     */
    private String parameterOne;

    /**
     * 参数二
     */
    private String parameterTwo;

    /**
     * 参数三
     */
    private String parameterThree;

    /**
     * 数据值
     */
    private String dataValue;

    /**
     * 数据值文本
     */
    private String dataValueText;

    /**
     * 创建类型 0-手动添加；1-接口生成
     */
    private Integer createType;

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
