package cn.stylefeng.guns.sys.core.exception;

import lombok.Data;

/**
 * 检查版本更新
 *
 * @author fengshuonan
 * @Date 2019/7/18 22:18
 */
@Data
public class CheckUpdateException extends RuntimeException {

    private Integer code;
    private String errorMessage;
    private String timestamp;
    private ApiManageApi apiManageApi;
    private ApiAppEdition apiAppEdition;
    private Boolean success;

    public CheckUpdateException(Integer code, String errorMessage, String timestamp, ApiManageApi apiManageApi, ApiAppEdition apiAppEdition, Boolean success) {
        super(errorMessage);
        this.code = code;
        this.errorMessage = errorMessage;
        this.timestamp = timestamp;
        this.apiManageApi = apiManageApi;
        this.apiAppEdition = apiAppEdition;
        this.success = success;
    }
}
