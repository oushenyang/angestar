package cn.stylefeng.guns.sys.core.exception;

import cn.stylefeng.guns.sys.core.exception.apiResult.ApiManageApi;
import cn.stylefeng.guns.sys.core.exception.apiResult.CardInfoApi;
import lombok.Data;

/**
 * 获取卡密信息
 *
 * @author fengshuonan
 * @Date 2019/7/18 22:18
 */
@Data
public class GetCardInfoException extends RuntimeException {

    private Integer code;
    private String errorMessage;
    private String timestamp;
    private ApiManageApi apiManageApi;
    private CardInfoApi cardInfoApi;
    private Boolean success;

    public GetCardInfoException(Integer code, String timestamp, ApiManageApi apiManageApi, CardInfoApi cardInfoApi, Boolean success) {
        this.code = code;
        this.timestamp = timestamp;
        this.apiManageApi = apiManageApi;
        this.cardInfoApi = cardInfoApi;
        this.success = success;
    }
}
