package cn.stylefeng.guns.modular.account.model.result;

import lombok.Data;

import java.util.Date;

/**
 * <p>月统计</p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2021/6/17
 * @since JDK 1.8
 */
@Data
public class AccountMonth {
    private Integer expireNum;
    private Integer activeNum;
    private Date registrationTime;
}
