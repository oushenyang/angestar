package cn.stylefeng.guns.sys.core.exception;

import lombok.Data;

import java.util.Date;

/**
 * 单码登录
 *
 * @author fengshuonan
 * @Date 2019/7/18 22:18
 */
@Data
public class TrialException extends RuntimeException {

    private Integer code;
    private Long appId;
    private Object data;
    private String expireTimeOrNum;
    private String appCode;
    private Boolean success;

    public TrialException(Integer code, Long appId, Object data, String expireTimeOrNum, String appCode, Boolean success) {
//        super(errorMessage);
        this.code = code;
        this.appId = appId;
        this.data = data;
        this.expireTimeOrNum = expireTimeOrNum;
        this.appCode = appCode;
        this.success = success;
    }
}
