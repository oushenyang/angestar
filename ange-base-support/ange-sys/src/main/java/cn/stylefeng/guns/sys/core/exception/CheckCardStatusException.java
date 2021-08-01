package cn.stylefeng.guns.sys.core.exception;

import cn.stylefeng.guns.sys.core.exception.apiResult.ApiManageApi;
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
    private ApiManageApi apiManageApi;
    private Boolean success;

    public CheckCardStatusException(Integer code, Long appId, Object data, Date expireTime, String holdCheck, ApiManageApi apiManageApi, Boolean success) {
//        super(errorMessage);
        this.code = code;
        this.appId = appId;
        this.data = data;
        this.expireTime = expireTime;
        this.holdCheck = holdCheck;
        this.apiManageApi = apiManageApi;
        this.success = success;
    }
}
