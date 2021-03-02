package cn.stylefeng.guns.webApi.huanying;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * <p></p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2020/7/16
 * @since JDK 1.8
 */
@Data
public class AppDataBody implements Serializable {
    private Integer appuserid;
    @JSONField(name="package")
    private String packAge;
    private String name;
}
