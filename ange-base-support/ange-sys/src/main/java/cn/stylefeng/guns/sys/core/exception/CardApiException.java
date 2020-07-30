package cn.stylefeng.guns.sys.core.exception;

import cn.stylefeng.roses.kernel.model.exception.AbstractBaseExceptionEnum;
import lombok.Data;

import static cn.stylefeng.guns.base.auth.exception.enums.AuthExceptionEnum.NO_PERMISSION;

/**
 * 没有访问权限
 *
 * @author fengshuonan
 * @Date 2019/7/18 22:18
 */
@Data
public class CardApiException extends RuntimeException {

    private Integer code;
    private String errorMessage;

    public CardApiException(Integer code, String errorMessage) {
        super(errorMessage);
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public CardApiException(AbstractBaseExceptionEnum exception) {
        super(exception.getMessage());
        this.code = exception.getCode();
        this.errorMessage = exception.getMessage();
    }

}
