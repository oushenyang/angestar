package cn.stylefeng.guns.sys.core.exception;

import cn.stylefeng.guns.sys.core.exception.apiResult.ApiAppEdition;
import cn.stylefeng.guns.sys.core.exception.apiResult.ApiManageApi;
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

    public CheckUpdateException(Integer code, String timestamp, ApiManageApi apiManageApi, ApiAppEdition apiAppEdition, Boolean success) {
        this.code = code;
        this.timestamp = timestamp;
        this.apiManageApi = apiManageApi;
        this.apiAppEdition = apiAppEdition;
        this.success = success;
    }
}
