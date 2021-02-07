package cn.stylefeng.guns.modular.card.model.result;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 卡密表
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-04-20
 */
@Data
public class CardInfoExport1 implements Serializable {
    /**
     * 卡密
     */
    @Excel(name = "卡密",width = 40, orderNum = "1")
    private String cardCode;
}
