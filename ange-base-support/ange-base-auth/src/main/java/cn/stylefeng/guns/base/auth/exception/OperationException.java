package cn.stylefeng.guns.base.auth.exception;

import cn.stylefeng.roses.kernel.model.exception.AbstractBaseExceptionEnum;
import lombok.Data;

import static cn.stylefeng.guns.base.auth.exception.enums.AuthExceptionEnum.NO_PERMISSION;

/**
 * 操作异常
 *
 * @author fengshuonan
 * @Date 2019/7/18 22:18
 */
@Data
public class OperationException extends RuntimeException {

    private Integer code;
    private String errorMessage;

    public OperationException(Integer code, String errorMessage) {
        super(errorMessage);
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public OperationException(AbstractBaseExceptionEnum exception) {
        super(exception.getMessage());
        this.code = exception.getCode();
        this.errorMessage = exception.getMessage();
    }

    public OperationException(String errorMessage) {
        super(errorMessage);
        this.code = 400;
        this.errorMessage = errorMessage;
    }

}
