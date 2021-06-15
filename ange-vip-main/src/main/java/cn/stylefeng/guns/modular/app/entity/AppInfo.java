package cn.stylefeng.guns.modular.app.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 软件表 
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-04-01
 */
@TableName("ag_app_info")
@Data
public class AppInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 软件id
     */
    @TableId(value = "app_id", type = IdType.ID_WORKER)
    private Long appId;

    /**
     * 应用编号(api接口用到)
     */
    @TableField("app_num")
    private String appNum;

    /**
     * 应用编号
     */
    @TableField("app_no")
    private String appNo;


    /**
     * 应用快捷页面编号
     */
    @TableField("app_quick")
    private String appQuick;

    /**
     * 应用头像
     */
    @TableField("app_head")
    private String appHead;

    /**
     * 开发者用户id
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 应用名
     */
    @TableField("app_name")
    private String appName;

    /**
     * 运行状态 0-收费；1-免费；2-关闭
     */
    @TableField("cydia_flag")
    private Integer cydiaFlag;

    /**
     * 软件公告
     */
    @TableField("app_notice")
    private String appNotice;

    /**
     * 卡密数量
     */
    @TableField("card_num")
    private Integer cardNum;

    /**
     * 账号数量
     */
    @TableField("account_num")
    private Integer accountNum;

    /**
     * 应用自定义数据1
     */
    @TableField("custom_data1")
    private String customData1;

    /**
     * 单码绑机策略 0-关闭；1-MAC；2-IP；3-混合
     */
    @TableField("code_bind_type")
    private Integer codeBindType;

    /**
     * 单码绑机选项 0-每天；1-永久；
     */
    @TableField("code_bind_option")
    private Integer codeBindOption;

    /**
     * 单码绑机数量
     */
    @TableField("code_bind_num")
    private Integer codeBindNum;

    /**
     * 单码重绑次数
     */
    @TableField("code_afresh_bind_num")
    private Integer codeAfreshBindNum;

    /**
     * 单码重绑扣时
     */
    @TableField("code_afresh_bind_time")
    private Integer codeAfreshBindTime;

    /**
     * 账号绑机策略 0-关闭；1-MAC；2-IP；3-混合
     */
    @TableField("account_bind_type")
    private Integer accountBindType;

    /**
     * 账号绑机选项 0-每天；1-永久；
     */
    @TableField("account_bind_option")
    private Integer accountBindOption;

    /**
     * 账号绑机数量
     */
    @TableField("account_bind_num")
    private Integer accountBindNum;

    /**
     * 账号重绑次数
     */
    @TableField("account_again_bind_num")
    private Integer accountAfreshBindNum;

    /**
     * 账号重绑扣时
     */
    @TableField("account_again_bind_time")
    private Integer accountAfreshBindTime;

    /**
     * 单码多开范围 0-关闭；1-单设备；2-单IP；3-所有设备
     */
    @TableField("code_open_range")
    private Integer codeOpenRange;

    /**
     * 单码登录方式 0-非顶号；1-顶号；
     */
    @TableField("code_sign_type")
    private Integer codeSignType;

    /**
     * 卡密清理间隔（取字典clear_space）
     */
    @TableField("code_clear_space")
    private Integer codeClearSpace;

    /**
     * 单码多开数量
     */
    @TableField("code_open_num")
    private Integer codeOpenNum;

    /**
     * 账号多开范围 0-关闭；1-单设备；2-单IP；3-所有设备
     */
    @TableField("account_open_range")
    private Integer accountOpenRange;

    /**
     * 账号登录方式 0-非顶号；1-顶号；
     */
    @TableField("account_sign_type")
    private Integer accountSignType;

    /**
     * 卡密清理间隔（取字典clear_space）
     */
    @TableField("account_clear_space")
    private Integer accountClearSpace;

    /**
     * 账号多开数量
     */
    @TableField("account_open_num")
    private Integer accountOpenNum;

    /**
     * 单码试用策略 0-关闭；1-时间；2-次数；
     */
    @TableField("code_try_type")
    private Integer codeTryType;

    /**
     * 单码试用时长
     */
    @TableField("code_try_time")
    private Integer codeTryTime;

    /**
     * 账号用户注册开关 0-否；1-是
     */
    @TableField("account_register_switch")
    private Boolean accountRegisterSwitch;

    /**
     * 账号注册限制 0-关闭；1-每天；2-永久；
     */
    @TableField("account_register_limit")
    private Integer accountRegisterLimit;

    /**
     * 账号注册次数
     */
    @TableField("account_register_num")
    private Integer accountRegisterNum;

    /**
     * 账号试用时间
     */
    @TableField("account_register_time")
    private Integer accountRegisterTime;


    /**
     * webApi加密范围 0-全部加密；1-仅加密api参数提交；2-仅加密api参数返回；
     */
    @TableField("web_algorithm_range")
    private Integer webAlgorithmRange;

    /**
     * webApi加密算法 0-关闭；1-DES；2-AES；3-RC4；4-Blowfish
     */
    @TableField("web_algorithm_type")
    private Integer webAlgorithmType;

    /**
     * 加密模式：0-ECB；1-CBC；2-CTR；3-CTS；4-CFB；5-OFB；6-PCBC
     */
    @TableField("encryption_mode")
    private Integer encryptionMode;

    /**
     * 填充：0-PKCS5Padding；1-PKCS7Padding；2-PKCS1Padding；3-ISO10126Padding；4-SSL3Padding；5-ZeroPadding；6-OAEPPadding；7-NoPadding；
     */
    @TableField("fill")
    private Integer fill;

    /**
     * 输出：0-base64；1-hex；
     */
    @TableField("web_algorithm_output")
    private Integer webAlgorithmOutput;

    /**
     * webApi加密key
     */
    @TableField("web_key")
    private String webKey;

    /**
     * webApi签名盐
     */
    @TableField("web_salt")
    private String webSalt;

    /**
     * Sign验证开关
     */
    @TableField("sign_flag")
    private Boolean signFlag;

    /**
     * 外部验证：0-关闭；1-易游；2-万捷；
     */
    @TableField("other_sign")
    private Integer otherSign;

    /**
     * 卡密查询地址
     */
    @TableField("proving_url")
    private String provingUrl;

    /**
     * 单码登录地址
     */
    @TableField("card_login_url")
    private String cardLoginUrl;

    /**
     * 版本号id
     */
    @TableField("version_num")
    private Long versionNum;

    /**
     * 创建人
     */
    @TableField(value = "create_user", fill = FieldFill.INSERT)
    private Long createUser;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新人
     */
    @TableField(value = "update_user", fill = FieldFill.UPDATE)
    private Long updateUser;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;
}
