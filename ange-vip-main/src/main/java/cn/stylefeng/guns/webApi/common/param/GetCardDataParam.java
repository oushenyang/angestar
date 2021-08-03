package cn.stylefeng.guns.webApi.common.param;

import lombok.Data;

/**
 * <p>获取单码数据</p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2021/5/26
 * @since JDK 1.8
 */
@Data
public class GetCardDataParam {
    private String token;
    private String card;
    private String timestamp;
    private String sign;


    public GetCardDataParam(String token, String card, String timestamp, String sign) {
        this.token = token;
        this.card = card;
        this.timestamp = timestamp;
        this.sign = sign;
    }
}
