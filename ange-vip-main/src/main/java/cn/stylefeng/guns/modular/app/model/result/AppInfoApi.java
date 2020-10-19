package cn.stylefeng.guns.modular.app.model.result;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 软件表 
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-04-01
 */
@Data
public class AppInfoApi{

    /**
     * 应用id
     */
    private Long appId;

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
     * 单码绑机策略 0-关闭；1-MAC；2-IP；3-混合
     */
    private Integer codeBindType;

    /**
     * 单码绑机选项 0-每天；1-永久；
     */
    private Integer codeBindOption;

    /**
     * 单码重绑次数
     */
    private Integer codeBindNum;

    /**
     * 单码重绑扣时
     */
    private Integer codeBindTime;

    /**
     * 账号绑机策略 0-关闭；1-MAC；2-IP；3-混合
     */
    private Integer accountBindType;

    /**
     * 账号绑机选项 0-每天；1-永久；
     */
    private Integer accountBindOption;

    /**
     * 账号重绑次数
     */
    private Integer accountBindNum;

    /**
     * 账号重绑扣时
     */
    private Integer accountBindTime;

    /**
     * 单码多开范围 0-关闭；1-单设备；2-单IP；3-所有设备
     */
    private Integer codeOpenRange;

    /**
     * 单码登录方式 0-非顶号；1-顶号；
     */
    private Integer codeSignType;

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
     * webApi加密算法 0-关闭；1-DES；2-AES；
     */
    private Integer webAlgorithmType;

    /**
     * webApi加密key
     */
    private String webKey;

    /**
     * webApi签名盐
     */
    private String webSalt;

    /**
     * 接收Sign验证
     */
    private String signReceive;

    /**
     * 输出Sign验证
     */
    private String signOutput;

    /**
     * 外部验证：0-关闭；1-易游；2-万捷；
     */
    private Integer otherSign;

    /**
     * 验证地址
     */
    private String provingUrl;

    /**
     * 创建人
     */
    private Long createUser;
}
