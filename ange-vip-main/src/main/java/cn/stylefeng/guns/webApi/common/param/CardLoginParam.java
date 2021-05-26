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
public class CardLoginParam {
    private String singleCode;
    private String edition;
    private String mac;
    private String model;
    private String holdCheck;
    private String sign;


    public CardLoginParam(String singleCode, String edition, String mac, String model, String holdCheck, String sign) {
        this.singleCode = singleCode;
        this.edition = edition;
        this.mac = mac;
        this.model = model;
        this.holdCheck = holdCheck;
        this.sign = sign;
    }
}
