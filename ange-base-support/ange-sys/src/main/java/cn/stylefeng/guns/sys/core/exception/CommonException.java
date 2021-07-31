package cn.stylefeng.guns.sys.core.exception;

import lombok.Data;

/**
 * 公共相关
 *
 * @author fengshuonan
 * @Date 2019/7/18 22:18
 */
@Data
public class CommonException extends RuntimeException {

    private Integer code;
    private String errorMessage;
    private String timestamp;
    private ApiManageApi apiManageApi;
    private Boolean success;

    public CommonException(Integer code, String timestamp, ApiManageApi apiManageApi,Boolean success) {
        this.code = code;
        this.timestamp = timestamp;
        this.apiManageApi = apiManageApi;
        this.success = success;
    }
}
