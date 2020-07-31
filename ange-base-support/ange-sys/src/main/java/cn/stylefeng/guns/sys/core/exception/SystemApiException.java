package cn.stylefeng.guns.sys.core.exception;

import cn.stylefeng.roses.kernel.model.exception.AbstractBaseExceptionEnum;
import lombok.Data;

/**
 * 没有访问权限
 *
 * @author fengshuonan
 * @Date 2019/7/18 22:18
 */
@Data
public class SystemApiException extends RuntimeException {

    private Integer code;
    private String errorMessage;

    public SystemApiException(Integer code, String errorMessage) {
        super(errorMessage);
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public SystemApiException(AbstractBaseExceptionEnum exception) {
        super(exception.getMessage());
        this.code = exception.getCode();
        this.errorMessage = exception.getMessage();
    }

}
