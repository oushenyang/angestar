package cn.stylefeng.guns.sys.core.exception;

import lombok.Data;

/**
 * 在线人数
 *
 * @author fengshuonan
 * @Date 2019/7/18 22:18
 */
@Data
public class OnlineNumException extends RuntimeException {

    private Integer code;
    private String errorMessage;
    private String timestamp;
    private ApiManageApi apiManageApi;
    private Integer onlineNum;
    private Boolean success;

    public OnlineNumException(Integer code, String errorMessage, String timestamp, ApiManageApi apiManageApi, Integer onlineNum, Boolean success) {
        super(errorMessage);
        this.code = code;
        this.errorMessage = errorMessage;
        this.timestamp = timestamp;
        this.apiManageApi = apiManageApi;
        this.onlineNum = onlineNum;
        this.success = success;
    }
}
