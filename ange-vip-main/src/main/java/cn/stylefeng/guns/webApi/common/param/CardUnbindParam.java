package cn.stylefeng.guns.webApi.common.param;

import lombok.Data;

/**
 * <p>卡密解绑</p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2021/5/26
 * @since JDK 1.8
 */
@Data
public class CardUnbindParam {
    private String card;
    private String mac;
    private String timestamp;
    private String sign;


    public CardUnbindParam(String card,String mac,String timestamp, String sign) {
        this.card = card;
        this.mac = mac;
        this.timestamp = timestamp;
        this.sign = sign;
    }
}
