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
public class GetAppInfoParam {
    private String timestamp;
    private String sign;


    public GetAppInfoParam(String timestamp, String sign) {
        this.timestamp = timestamp;
        this.sign = sign;
    }
}
