package cn.stylefeng.guns.sys.core.exception;

import lombok.Data;

import java.util.Date;


/**
 * <p>
 * 接口管理 
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-06-02
 */
@Data
public class ApiAppEdition {

    /**
     * 版本编号
     */
    private String editionSerial;

    /**
     * 版本名称
     */
    private String editionName;

    /**
     * 版本号
     */
    private String editionNum;

    /**
     * 版本状态 0-启用；1-测试；2-关闭；
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
     * 创建时间
     */
    private Date createTime;

}
