package cn.stylefeng.guns.webApi.common.param;

import lombok.Data;

/**
 * <p>获取卡密信息</p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2021/5/26
 * @since JDK 1.8
 */
@Data
public class GetCardInfoParam {
    private String card;
    private String timestamp;
    private String sign;


    public GetCardInfoParam(String card, String timestamp, String sign) {
        this.card = card;
        this.timestamp = timestamp;
        this.sign = sign;
    }
}
