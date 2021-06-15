package cn.stylefeng.guns.modular.app.model.result;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 软件表 
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-04-01
 */
@Data
public class AppInfoResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 软件id
     */
    private Long appId;

    /**
     * 应用编号(api接口用到)
     */
    private String appNum;

    /**
     * 应用编号
     */
    private String appNo;

    /**
     * 应用快捷页面编号
     */
    private String appQuick;

    /**
     * 应用头像
     */
    private String appHead;

    /**
     * 开发者用户id
     */
    private Long userId;

    /**
     * 应用名
     */
    private String appName;

    /**
     * 运行状态 0-收费；1-免费；2-关闭
     */
    private Integer cydiaFlag;

    /**
     * 软件公告
     */
    private String appNotice;

    /**
     * 卡密数量
     */
    private Integer cardNum;

    /**
     * 账号数量
     */
    private Integer accountNum;

    /**
     * 版本号
     */
    private String editionNum;

    /**
     * 应用自定义数据1
     */
    private String customData1;

    /**
     * 单码绑机策略 0-关闭；1-MAC；2-IP；3-混合
     */
    private Integer codeBindType;

    /**
     * 单码绑机选项 0-每天；1-永久；
     */
    private Integer codeBindOption;

    /**
     * 单码绑机数量
     */
    private Integer codeBindNum;

    /**
     * 单码重绑次数
     */
    private Integer codeAfreshBindNum;

    /**
     * 单码重绑扣时
     */
    private Integer codeAfreshBindTime;

    /**
     * 账号绑机策略 0-关闭；1-MAC；2-IP；3-混合
     */
    private Integer accountBindType;

    /**
     * 账号绑机选项 0-每天；1-永久；
     */
    private Integer accountBindOption;

    /**
     * 账号绑机数量
     */
    private Integer accountBindNum;

    /**
     * 账号重绑次数
     */
    private Integer accountAfreshBindNum;

    /**
     * 账号重绑扣时
     */
    private Integer accountAfreshBindTime;

    /**
     * 单码多开范围 0-关闭；1-单设备；2-单IP；3-所有设备
     */
    private Integer codeOpenRange;

    /**
     * 单码登录方式 0-非顶号；1-顶号；
     */
    private Integer codeSignType;

    /**
     * 卡密清理间隔（取字典clear_space）
     */
    private Integer codeClearSpace;

    /**
     * 单码多开数量
     */
    private Integer codeOpenNum;

    /**
     * 账号多开范围 0-关闭；1-单设备；2-单IP；3-所有设备
     */
    private Integer accountOpenRange;

    /**
     * 账号登录方式 0-非顶号；1-顶号；
     */
    private Integer accountSignType;

    /**
     * 账号清理间隔（取字典clear_space）
     */
    private Integer accountClearSpace;

    /**
     * 账号多开数量
     */
    private Integer accountOpenNum;

    /**
     * 单码试用策略 0-关闭；1-时间；2-次数；
     */
    private Integer codeTryType;

    /**
     * 单码试用时长
     */
    private Integer codeTryTime;

    /**
     * 账号用户注册开关 0-否；1-是
     */
    private Boolean accountRegisterSwitch;

    /**
     * 账号注册限制 0-关闭；1-每天；2-永久；
     */
    private Integer accountRegisterLimit;

    /**
     * 账号注册次数
     */
    private Integer accountRegisterNum;

    /**
     * 账号试用时间
     */
    private Integer accountRegisterTime;

    /**
     * webApi加密范围 0-全部加密；1-仅加密api参数提交；2-仅加密api参数返回；
     */
    private Integer webAlgorithmRange;

    /**
     * webApi加密算法 0-关闭；1-DES；2-AES；3-RC4；4-Blowfish
     */
    private Integer webAlgorithmType;

    /**
     * 加密模式：0-ECB；1-CBC；2-CTR；3-CTS；4-CFB；5-OFB；6-PCBC
     */
    private Integer encryptionMode;

    /**
     * 填充：0-PKCS5Padding；1-PKCS7Padding；2-PKCS1Padding；3-ISO10126Padding；4-SSL3Padding；5-ZeroPadding；6-OAEPPadding；7-NoPadding；
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
     * Sign验证开关
     */
    private Boolean signFlag;

    /**
     * 外部验证：0-关闭；1-易游；2-万捷；
     */
    private Integer otherSign;

    /**
     * 卡密查询地址
     */
    private String provingUrl;

    /**
     * 单码登录地址
     */
    private String cardLoginUrl;

    /**
     * 版本号id
     */
    private Long versionNum;

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
