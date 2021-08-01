package cn.stylefeng.guns.webApi.common.param;

import lombok.Data;

/**
 * <p>设置单码数据</p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2021/5/26
 * @since JDK 1.8
 */
@Data
public class SetCardDataParam {
    private String token;
    private String card;
    private String cardData;
    private String timestamp;
    private String sign;


    public SetCardDataParam(String token, String card, String cardData, String timestamp, String sign) {
        this.token = token;
        this.card = card;
        this.cardData = cardData;
        this.timestamp = timestamp;
        this.sign = sign;
    }
}
