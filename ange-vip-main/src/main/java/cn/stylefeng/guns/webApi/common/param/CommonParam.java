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
public class CommonParam {
    private String parameterOne;
    private String parameterTwo;
    private String parameterThree;
    private String parameterFour;
    private String parameterFive;
    private String parameterSix;
    private String parameterSeven;


    public CommonParam(String parameterOne, String parameterTwo, String parameterThree, String parameterFour, String parameterFive, String parameterSix, String parameterSeven) {
        this.parameterOne = parameterOne;
        this.parameterTwo = parameterTwo;
        this.parameterThree = parameterThree;
        this.parameterFour = parameterFour;
        this.parameterFive = parameterFive;
        this.parameterSix = parameterSix;
        this.parameterSeven = parameterSeven;
    }
}
