package cn.stylefeng.guns.webApi.common.param;

import lombok.Data;

/**
 * <p></p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2021/5/26
 * @since JDK 1.8
 */
@Data
public class CheckCardStatusParam {
    private String statusCode;
    private String singleCode;
    private String holdCheck;
    private String sign;


    public CheckCardStatusParam(String statusCode, String singleCode,String holdCheck, String sign) {
        this.statusCode = statusCode;
        this.singleCode = singleCode;
        this.holdCheck = holdCheck;
        this.sign = sign;
    }
}
