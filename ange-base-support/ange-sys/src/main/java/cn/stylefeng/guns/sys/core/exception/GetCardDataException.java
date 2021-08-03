package cn.stylefeng.guns.sys.core.exception;

import cn.stylefeng.guns.sys.core.exception.apiResult.ApiManageApi;
import lombok.Data;

/**
 * 获取卡密数据
 *
 * @author fengshuonan
 * @Date 2019/7/18 22:18
 */
@Data
public class GetCardDataException extends RuntimeException {

    private Integer code;
    private String timestamp;
    private String cardData;
    private ApiManageApi apiManageApi;
    private Boolean success;

    public GetCardDataException(Integer code, String timestamp, String cardData, ApiManageApi apiManageApi,Boolean success) {
        this.code = code;
        this.timestamp = timestamp;
        this.cardData = cardData;
        this.apiManageApi = apiManageApi;
        this.success = success;
    }
}
