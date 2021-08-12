package cn.stylefeng.guns.webApi.common.param;

import lombok.Data;

/**
 * <p>卡密封禁</p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2021/5/26
 * @since JDK 1.8
 */
@Data
public class CardDisableParam {
    private String token;
    private String card;
    private String remark;
    private String timestamp;
    private String sign;


    public CardDisableParam(String token,String card, String remark, String timestamp, String sign) {
        this.token = token;
        this.card = card;
        this.remark = remark;
        this.timestamp = timestamp;
        this.sign = sign;
    }
}
