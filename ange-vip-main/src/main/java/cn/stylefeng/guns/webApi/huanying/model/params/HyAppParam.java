package cn.stylefeng.guns.webApi.huanying.model.params;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 幻影破解
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-07-16
 */
@Data
public class HyAppParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    private String appinfoid;

    private String utDid;

    private Integer appuserid;

    private String name;

    private String packAge;

    private Integer fakedata;

    @Override
    public String checkParam() {
        return null;
    }

}
