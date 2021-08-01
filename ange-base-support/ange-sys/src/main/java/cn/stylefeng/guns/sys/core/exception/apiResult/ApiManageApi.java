package cn.stylefeng.guns.sys.core.exception.apiResult;

import com.baomidou.mybatisplus.annotation.TableField;
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

}
