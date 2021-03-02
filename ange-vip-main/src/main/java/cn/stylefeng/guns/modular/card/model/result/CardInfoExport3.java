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
public class CardInfoExport3 implements Serializable {
    /**
     * 卡密
     */
    @Excel(name = "卡密",width = 40, orderNum = "1")
    private String cardCode;

    /**
     * 卡类名称
     */
    @Excel(name = "卡类名称",width = 20, orderNum = "2")
    private String cardTypeName;

    /**
     * 激活时间
     */
    @Excel(name = "激活时间",width = 20, orderNum = "3",format = "yyyy-MM-dd HH:mm:ss")
    private Date activeTime;

    /**
     * 到期时间
     */
    @Excel(name = "到期时间",width = 20, orderNum = "4",format = "yyyy-MM-dd HH:mm:ss")
    private Date expireTime;
}
