package cn.stylefeng.guns.sys.modular.system.model.result;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户操作日志
 * </p>
 *
 * @author stylefeng
 * @since 2019-04-01
 */
@Data
public class UserOperationLogResult{
    /**
     * 主键
     */
    private Long userOperationLogId;

    /**
     * 日志类型：1-应用相关；2-卡密相关；3-账号相关；4-代理相关；5-试用相关；6-数据相关；7-接口相关
     */
    private Integer logType;

    /**
     * 日志名称
     */
    private String logName;

    /**
     * 用户id
     */
    private Long userId;
    private String userName;

    /**
     * 创建时间
     */
    private Date createTime;
    private String createTimeName;

    /**
     * 短消息
     */
    private String shortMessage;

    /**
     * 消息
     */
    private String message;

    /**
     * 开发者
     */
    private Long developerUser;
}
