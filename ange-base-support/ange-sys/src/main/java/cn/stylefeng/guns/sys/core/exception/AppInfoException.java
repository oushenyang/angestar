package cn.stylefeng.guns.sys.core.exception;

import lombok.Data;

/**
 * 应用相关
 *
 * @author fengshuonan
 * @Date 2019/7/18 22:18
 */
@Data
public class AppInfoException extends RuntimeException {

    private Integer code;
    private String errorMessage;
    private String timestamp;
    private ApiManageApi apiManageApi;
    private AppInfoApi appInfoApi;
    private Boolean success;

    public AppInfoException(Integer code, String timestamp, ApiManageApi apiManageApi,AppInfoApi appInfoApi, Boolean success) {
//        super(errorMessage);
        this.code = code;
//        this.errorMessage = errorMessage;
        this.timestamp = timestamp;
        this.apiManageApi = apiManageApi;
        this.appInfoApi = appInfoApi;
        this.success = success;
    }
}
