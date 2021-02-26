package cn.stylefeng.guns.sys.core.exception;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("JSON返回格式")
public class ApiResult<T> {

    @ApiModelProperty("返回值")
    private Integer code;
    @ApiModelProperty("返回说明")
    private String msg;
    @ApiModelProperty("返回数据")
    private T data;
    @ApiModelProperty("是否成功")
    private Boolean success;

    public ApiResult() {
    }
    public ApiResult(int c, String m, T r,boolean s) {
        code = c;
        msg = m;
        data = r;
        success = s;
    }

    public ApiResult(int c, String m, T r) {
        code = c;
        msg = m;
        data = r;
    }

    public ApiResult(int c, String m) {
        code = c;
        msg = m;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public static ApiResult success() {
        return new ApiResult(200, "操作成功");
    }

    public static ApiResult success(Object object) {
        return new ApiResult(200, "操作成功", object);
    }

    public static ApiResult error() {
        return new ApiResult(500, "操作失败");
    }

    public static ApiResult error(Object object) {
        return new ApiResult(500, "操作失败", object);
    }

    public static ApiResult errorMsg(String object) {
        return new ApiResult(500, object,"");
    }
    public static ApiResult resultError(Integer code,String msg,Object data,Boolean success) {
        return new ApiResult(code,msg,data,success);
    }

}
