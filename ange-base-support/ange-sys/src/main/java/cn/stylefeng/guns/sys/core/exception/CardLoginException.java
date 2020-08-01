package cn.stylefeng.guns.sys.core.exception;

import cn.stylefeng.roses.kernel.model.exception.AbstractBaseExceptionEnum;
import lombok.Data;

/**
 * 单码登录
 *
 * @author fengshuonan
 * @Date 2019/7/18 22:18
 */
@Data
public class CardLoginException extends RuntimeException {

    private Integer code;
    private Long appId;
    private Object data;
    private Boolean success;

    public CardLoginException(Integer code, Long appId, Object data, Boolean success) {
//        super(errorMessage);
        this.code = code;
        this.appId = appId;
        this.data = data;
        this.success = success;
    }
}
