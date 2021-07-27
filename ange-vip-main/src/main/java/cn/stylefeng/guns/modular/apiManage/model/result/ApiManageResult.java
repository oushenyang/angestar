package cn.stylefeng.guns.modular.apiManage.model.result;

import lombok.Data;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 接口管理 
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-06-02
 */
@Data
public class ApiManageResult implements Serializable {

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
     * 参数一注释
     */
    private String parameterOneNote;

    /**
     * 参数一是否必填；0-是；1-否
     */
    private Boolean parameterOneRequired;

    /**
     * 参数二
     */
    private String parameterTwo;

    /**
     * 参数二说明
     */
    private String parameterTwoRemark;

    /**
     * 参数二注释
     */
    private String parameterTwoNote;

    /**
     * 参数二是否必填；0-是；1-否
     */
    private Boolean parameterTwoRequired;

    /**
     * 参数三
     */
    private String parameterThree;

    /**
     * 参数三说明
     */
    private String parameterThreeRemark;

    /**
     * 参数三注释
     */
    private String parameterThreeNote;

    /**
     * 参数三是否必填；0-是；1-否
     */
    private Boolean parameterThreeRequired;

    /**
     * 参数四
     */
    private String parameterFour;

    /**
     * 参数四说明
     */
    private String parameterFourRemark;

    /**
     * 参数四注释
     */
    private String parameterFourNote;

    /**
     * 参数四是否必填；0-是；1-否
     */
    private Boolean parameterFourRequired;

    /**
     * 参数五
     */
    private String parameterFive;

    /**
     * 参数五说明
     */
    private String parameterFiveRemark;

    /**
     * 参数五注释
     */
    private String parameterFiveNote;

    /**
     * 参数五是否必填；0-是；1-否
     */
    private Boolean parameterFiveRequired;

    /**
     * 参数六
     */
    private String parameterSix;

    /**
     * 参数六说明
     */
    private String parameterSixRemark;

    /**
     * 参数六注释
     */
    private String parameterSixNote;

    /**
     * 参数六是否必填；0-是；1-否
     */
    private Boolean parameterSixRequired;

    /**
     * 参数七
     */
    private String parameterSeven;

    /**
     * 参数七说明
     */
    private String parameterSevenRemark;

    /**
     * 参数七注释
     */
    private String parameterSevenNote;

    /**
     * 参数七是否必填；0-是；1-否
     */
    private Boolean parameterSevenRequired;

    /**
     * 返回说明
     */
    private String returnRemark;

    /**
     * 备注
     */
    private String remark;

    /**
     * webApi加密范围 0-全部加密；1-仅加密api参数提交；2-仅加密api参数返回；
     */
    private Integer webAlgorithmRange;

    /**
     * 传参加密方式：0-请求参数封装为json再加密；1-分别为每个请求参数加密
     */
    private Integer postType;

    /**
     * webApi加密算法 0-关闭；1-DES；2-AES；3-DESede；4-SM4；5-RSA
     */
    private Integer webAlgorithmType;

    /**
     * 加密模式：0-ECB；1-CBC；2-CTR；3-CTS；4-CFB；5-OFB；
     */
    private Integer encryptionMode;

    /**
     * 字符集：0-utf8；1-gb2312；2-gbk；
     */
    private Integer characterSet;

    /**
     * 填充：0-PKCS5Padding；1-PKCS7Padding；2-ISO10126Padding；3-ZeroPadding；4-NoPadding
     */
    private Integer fill;

    /**
     * 输出：0-base64；1-hex；
     */
    private Integer webAlgorithmOutput;

    /**
     * webApi加密key
     */
    private String webKey;

    /**
     * webApi签名盐
     */
    private String webSalt;

    /**
     * 公钥
     */
    private String publicKey;

    /**
     * 私匙
     */
    private String privateKey;

    /**
     * Sign验证开关
     */
    private Boolean signFlag;

    /**
     * 数据包超时时间
     */
    private Integer dataOvertime;

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

}
