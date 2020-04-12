package cn.stylefeng.guns.modular.appEdition.model.params;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 应用版本表 
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-04-12
 */
@Data
public class AppEditionParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 版本id
     */
    private Long editionId;

    /**
     * 版本编号
     */
    private String editionSerial;

    /**
     * 软件id
     */
    private Long appId;

    /**
     * 版本名称
     */
    private String editionName;

    /**
     * 版本号
     */
    private String editionNum;

    /**
     * 版本状态 0-启用；1-关闭；2-测试；
     */
    private Integer editionStatus;

    /**
     * 强制更新 0-否；1-是；
     */
    private Boolean needUpdate;

    /**
     * 版本MD5
     */
    private String editionMd5;

    /**
     * 更新地址
     */
    private String updateUrl;

    /**
     * 更新描述
     */
    private String updateDescribe;

    /**
     * 备注
     */
    private String remark;

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
