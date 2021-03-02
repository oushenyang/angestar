package cn.stylefeng.guns.modular.apiManage.model.result;

import lombok.Data;


/**
 * <p>
 * 接口管理 
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-06-02
 */
@Data
public class ApiManageApi{
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
     * 接口编码
     */
    private String apiCode;

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
     * 参数四
     */
    private String parameterFour;

    /**
     * 参数五
     */
    private String parameterFive;

    /**
     * 参数六
     */
    private String parameterSix;

    /**
     * 参数七
     */
    private String parameterSeven;

}
