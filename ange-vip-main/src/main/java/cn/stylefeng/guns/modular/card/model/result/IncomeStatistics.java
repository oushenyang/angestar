package cn.stylefeng.guns.modular.card.model.result;

import lombok.Data;

import java.math.BigDecimal;
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
public class IncomeStatistics {
    private BigDecimal price;
    private Date activeTime;
}
