package cn.stylefeng.guns.webApi.huanying.model.result;

import lombok.Data;
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
public class HyAppResult implements Serializable {

    private static final long serialVersionUID = 1L;


    private String appinfoid;

    private String utDid;

    private Integer appuserid;

    private String name;

    private String username;

    private String packAge;
    private String packName;

    private Integer fakedata;

    private String applicationName;

}
