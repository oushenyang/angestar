package cn.stylefeng.guns.sys.core.exception;

import cn.stylefeng.roses.kernel.model.exception.AbstractBaseExceptionEnum;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

/**
 * 系统相关
 *
 * @author fengshuonan
 * @Date 2019/7/18 22:18
 */
@Data
public class SystemApiException extends RuntimeException {

    private Integer code;
    private String errorMessage;
    private Object data;
    private Boolean success;

    public SystemApiException(Integer code, String errorMessage, Object data, Boolean success) {
        super(errorMessage);
        this.code = code;
        this.errorMessage = errorMessage;
        this.data = data;
        this.success = success;
    }
}
