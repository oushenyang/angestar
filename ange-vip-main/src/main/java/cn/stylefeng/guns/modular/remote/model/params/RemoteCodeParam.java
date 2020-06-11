package cn.stylefeng.guns.modular.remote.model.params;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 远程代码
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-05-19
 */
@Data
public class RemoteCodeParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 代码id
     */
    private Long codeId;

    /**
     * 应用ID
     */
    private Long appId;

    /**
     * 代码编码
     */
    private String codeCode;

    /**
     * 参数一名
     */
    private String parameterOne;

    /**
     * 参数二名
     */
    private String parameterTwo;

    /**
     * 参数三名
     */
    private String parameterThree;

    /**
     * 参数四名
     */
    private String parameterFour;

    /**
     * 代码值
     */
    private String codeValue;

    /**
     * 代码文本
     */
    private String codeText;

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
