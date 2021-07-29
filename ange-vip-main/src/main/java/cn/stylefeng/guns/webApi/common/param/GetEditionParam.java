package cn.stylefeng.guns.webApi.common.param;

import lombok.Data;

/**
 * <p>获取版本信息返回</p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2021/5/26
 * @since JDK 1.8
 */
@Data
public class GetEditionParam {
    private String edition;
    private String timestamp;
    private String sign;


    public GetEditionParam(String edition, String timestamp, String sign) {
        this.edition = edition;
        this.timestamp = timestamp;
        this.sign = sign;
    }
}
