package cn.stylefeng.guns.webApi.common.param;

import lombok.Data;

/**
 * <p>取在线人数</p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2021/5/26
 * @since JDK 1.8
 */
@Data
public class GetOnlineNumParam {
    private String edition;
    private String limit;
    private String timestamp;
    private String sign;


    public GetOnlineNumParam(String edition, String limit, String timestamp, String sign) {
        this.edition = edition;
        this.limit = limit;
        this.timestamp = timestamp;
        this.sign = sign;
    }
}
