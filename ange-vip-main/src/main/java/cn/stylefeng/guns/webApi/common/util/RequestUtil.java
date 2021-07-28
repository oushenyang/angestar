package cn.stylefeng.guns.webApi.common.util;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.*;
import cn.stylefeng.guns.sys.core.exception.ApiManageApi;
import cn.stylefeng.guns.sys.core.exception.*;
import cn.stylefeng.guns.webApi.common.param.CardLoginParam;
import cn.stylefeng.guns.webApi.common.param.CheckCardStatusParam;
import cn.stylefeng.guns.webApi.common.param.CommonParam;
import cn.stylefeng.roses.core.util.HttpContext;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

/**
 * <p></p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2021/5/26
 * @since JDK 1.8
 */
public class RequestUtil {
    //
    public static CardLoginParam getCardLoginParameter(ApiManageApi apiManage, String body){
        CommonParam commonParam = CommonUtil.requestDec(apiManage,body);
        String card = commonParam.getParameterOne();
        String mac = commonParam.getParameterTwo();
        String edition = commonParam.getParameterThree();
        String model = commonParam.getParameterFour();
        String holdCheck = commonParam.getParameterFive();
        String sign = commonParam.getParameterSix();
        if (StringUtils.isEmpty(card)||StringUtils.isEmpty(mac)){
            throw new SystemApiException(2, "必传参数存在空值","",false);
        }
        if (apiManage.getSignFlag()&&StringUtils.isEmpty(sign)){
            throw new SystemApiException(4, "签名不正确","",false);
        }else if (apiManage.getSignFlag()&&StringUtils.isNotEmpty(sign)&&sign.length()!=32){
            throw new SystemApiException(4, "签名不正确","",false);
        }else if(apiManage.getSignFlag()&&StringUtils.isNotEmpty(sign)&&sign.length()==32){
            String md5 = SecureUtil.md5(card+mac+StringUtils.trimToEmpty(edition)+StringUtils.trimToEmpty(model)+StringUtils.trimToEmpty(holdCheck));
            if (!md5.equals(sign)){
                throw new SystemApiException(4, "签名不正确","",false);
            }
        }
        return new CardLoginParam(card,edition,mac,model,holdCheck,sign);
    }

    public static CheckCardStatusParam getCheckCardStatusParameter(ApiManageApi apiManage, String body){
        CommonParam commonParam = CommonUtil.requestDec(apiManage,body);
        String statusCode = commonParam.getParameterOne();
        String singleCode = commonParam.getParameterTwo();
        String holdCheck = commonParam.getParameterThree();
        String sign = commonParam.getParameterFour();
        if (StringUtils.isEmpty(singleCode)||StringUtils.isEmpty(statusCode)){
            throw new SystemApiException(2, "必传参数存在空值","",false);
        }
        if (apiManage.getSignFlag()&&StringUtils.isEmpty(sign)){
            throw new SystemApiException(4, "签名不正确","",false);
        }else if (apiManage.getSignFlag()&&StringUtils.isNotEmpty(sign)&&sign.length()!=32){
            throw new SystemApiException(4, "签名不正确","",false);
        }else if(StringUtils.isNotEmpty(sign)&&sign.length()==32){
            String md5 = SecureUtil.md5(StringUtils.trimToEmpty(statusCode)+StringUtils.trimToEmpty(singleCode)+StringUtils.trimToEmpty(holdCheck));
            if (!md5.equals(sign)){
                throw new SystemApiException(4, "签名不正确","",false);
            }
        }
        return new CheckCardStatusParam(statusCode,singleCode,holdCheck,sign);
    }
}
