package cn.stylefeng.guns.sys.core.exception;

import lombok.Data;

import java.util.Date;

/**
 * 检测单码用户状态
 *
 * @author fengshuonan
 * @Date 2019/7/18 22:18
 */
@Data
public class CheckCardStatusException extends RuntimeException {

    private Integer code;
    private Long appId;
    private Object data;
    private Date expireTime;
    private String holdCheck;
    private AppInfoApi appInfoApi;
    private Boolean success;

    public CheckCardStatusException(Integer code, Long appId, Object data, Date expireTime, String holdCheck, AppInfoApi appInfoApi, Boolean success) {
//        super(errorMessage);
        this.code = code;
        this.appId = appId;
        this.data = data;
        this.expireTime = expireTime;
        this.holdCheck = holdCheck;
        this.appInfoApi = appInfoApi;
        this.success = success;
    }
}
