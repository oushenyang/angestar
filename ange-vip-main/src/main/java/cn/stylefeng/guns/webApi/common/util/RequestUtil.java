package cn.stylefeng.guns.webApi.common.util;

import cn.stylefeng.guns.sys.core.exception.apiResult.ApiManageApi;
import cn.stylefeng.guns.sys.core.exception.*;
import cn.stylefeng.guns.sys.core.exception.enums.ApiExceptionEnum;
import cn.stylefeng.guns.webApi.common.param.*;
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
    //获取应用信息
    public static GetAppInfoParam getAppInfoParameter(ApiManageApi apiManage, String body){
        CommonParam commonParam = CommonUtil.requestDec(apiManage,body);
        String timestamp = commonParam.getParameterOne();
        String sign = commonParam.getParameterTwo();
        //启用超时和验证接口是否开启
        CommonUtil.overtime(apiManage,timestamp);
        //校验签名
        CommonUtil.sign(apiManage,sign,timestamp,timestamp);
        return new GetAppInfoParam(timestamp,sign);
    }
    //获取版本信息
    public static GetEditionParam getEditionParameter(ApiManageApi apiManage, String body){
        CommonParam commonParam = CommonUtil.requestDec(apiManage,body);
        String edition = commonParam.getParameterOne();
        String timestamp = commonParam.getParameterTwo();
        String sign = commonParam.getParameterThree();
        if (StringUtils.isEmpty(edition)){
            //必传参数存在空值
            throw new SystemApiException(ApiExceptionEnum.PARAMETER_HAS_NULL.getCode(), ApiExceptionEnum.PARAMETER_HAS_NULL.getMessage(),"",false);
        }
        //启用超时和验证接口是否开启
        CommonUtil.overtime(apiManage,timestamp);
        //校验签名
        CommonUtil.sign(apiManage,sign,timestamp,edition+timestamp);
        return new GetEditionParam(edition,timestamp,sign);
    }
    //取在线人数
    public static GetOnlineNumParam getOnlineNumParameter(ApiManageApi apiManage, String body){
        CommonParam commonParam = CommonUtil.requestDec(apiManage,body);
        String edition = commonParam.getParameterOne();
        String limit = commonParam.getParameterTwo();
        String timestamp = commonParam.getParameterThree();
        String sign = commonParam.getParameterFour();
        if (StringUtils.isNotEmpty(limit)&&!StringUtils.isNumeric(limit)){
            //传入数据类型或格式错误
            throw new CommonException(ApiExceptionEnum.DATA_TYPE_ERROR.getCode(),"",apiManage,false);
        }
        if (StringUtils.isNotEmpty(limit)){
            if (Integer.parseInt(limit)<0||Integer.parseInt(limit)>1440){
                limit = "60";
            }
        }else {
            limit = "60";
        }
        //启用超时和验证接口是否开启
        CommonUtil.overtime(apiManage,timestamp);
        //校验签名
        CommonUtil.sign(apiManage,sign,timestamp,StringUtils.trimToEmpty(edition)+StringUtils.trimToEmpty(limit)+timestamp);
        return new GetOnlineNumParam(edition,limit,timestamp,sign);
    }
    //单码登录
    public static CardLoginParam getCardLoginParameter(ApiManageApi apiManage, String body){
        CommonParam commonParam = CommonUtil.requestDec(apiManage,body);
        String card = commonParam.getParameterOne();
        String mac = commonParam.getParameterTwo();
        String edition = commonParam.getParameterThree();
        String model = commonParam.getParameterFour();
        String timestamp = commonParam.getParameterFive();
        String sign = commonParam.getParameterSix();
        if (StringUtils.isEmpty(card)||StringUtils.isEmpty(mac)){
            throw new SystemApiException(2, "必传参数存在空值","",false);
        }
        //校验签名
        CommonUtil.sign(apiManage,sign,timestamp,card+mac+StringUtils.trimToEmpty(edition)+StringUtils.trimToEmpty(model)+StringUtils.trimToEmpty(timestamp));
        return new CardLoginParam(card,edition,mac,model,timestamp,sign);
    }
    //检测单码用户状态
    public static CheckCardStatusParam getCheckCardStatusParameter(ApiManageApi apiManage, String body){
        CommonParam commonParam = CommonUtil.requestDec(apiManage,body);
        String statusCode = commonParam.getParameterOne();
        String singleCode = commonParam.getParameterTwo();
        String timestamp = commonParam.getParameterThree();
        String sign = commonParam.getParameterFour();
        if (StringUtils.isEmpty(singleCode)||StringUtils.isEmpty(statusCode)){
            throw new SystemApiException(2, "必传参数存在空值","",false);
        }
        //校验签名
        CommonUtil.sign(apiManage,sign,timestamp,StringUtils.trimToEmpty(statusCode)+StringUtils.trimToEmpty(singleCode)+StringUtils.trimToEmpty(timestamp));
        return new CheckCardStatusParam(statusCode,singleCode,timestamp,sign);
    }

    //获取卡密信息
    public static GetCardInfoParam getCardInfoParameter(ApiManageApi apiManage, String body){
        CommonParam commonParam = CommonUtil.requestDec(apiManage,body);
        String card = commonParam.getParameterOne();
        String timestamp = commonParam.getParameterTwo();
        String sign = commonParam.getParameterThree();
        if (StringUtils.isEmpty(card)){
            throw new SystemApiException(2, "必传参数存在空值","",false);
        }
        //校验签名
        CommonUtil.sign(apiManage,sign,timestamp,card+StringUtils.trimToEmpty(timestamp));
        return new GetCardInfoParam(card,timestamp,sign);
    }

    //设置单码数据
    public static SetCardDataParam setCardDataParameter(ApiManageApi apiManage, String body){
        CommonParam commonParam = CommonUtil.requestDec(apiManage,body);
        String token = commonParam.getParameterOne();
        String card = commonParam.getParameterTwo();
        String cardData = commonParam.getParameterThree();
        String timestamp = commonParam.getParameterFour();
        String sign = commonParam.getParameterFive();
        if (StringUtils.isEmpty(token)||StringUtils.isEmpty(card)||StringUtils.isEmpty(cardData)){
            throw new SystemApiException(2, "必传参数存在空值","",false);
        }
        //校验签名
        CommonUtil.sign(apiManage,sign,timestamp,StringUtils.trimToEmpty(token)+StringUtils.trimToEmpty(card)+StringUtils.trimToEmpty(cardData)+StringUtils.trimToEmpty(timestamp));
        return new SetCardDataParam(token,card,cardData,timestamp,sign);
    }

    //获取单码数据
    public static GetCardDataParam getCardDataParameter(ApiManageApi apiManage, String body){
        CommonParam commonParam = CommonUtil.requestDec(apiManage,body);
        String token = commonParam.getParameterOne();
        String card = commonParam.getParameterTwo();
        String timestamp = commonParam.getParameterThree();
        String sign = commonParam.getParameterFour();
        if (StringUtils.isEmpty(token)||StringUtils.isEmpty(card)){
            throw new SystemApiException(2, "必传参数存在空值","",false);
        }
        //校验签名
        CommonUtil.sign(apiManage,sign,timestamp,StringUtils.trimToEmpty(token)+StringUtils.trimToEmpty(card)+StringUtils.trimToEmpty(timestamp));
        return new GetCardDataParam(token,card,timestamp,sign);
    }

    //卡密解绑
    public static CardUnbindParam cardUnbindParameter(ApiManageApi apiManage, String body){
        CommonParam commonParam = CommonUtil.requestDec(apiManage,body);
        String card = commonParam.getParameterOne();
        String mac = commonParam.getParameterTwo();
        String timestamp = commonParam.getParameterThree();
        String sign = commonParam.getParameterFour();
        if (StringUtils.isEmpty(card)){
            throw new SystemApiException(2, "必传参数存在空值","",false);
        }
        //校验签名
        CommonUtil.sign(apiManage,sign,timestamp,StringUtils.trimToEmpty(card)+StringUtils.trimToEmpty(mac)+StringUtils.trimToEmpty(timestamp));
        return new CardUnbindParam(card,mac,timestamp,sign);
    }

    //卡密封禁
    public static CardDisableParam cardDisableParameter(ApiManageApi apiManage, String body){
        CommonParam commonParam = CommonUtil.requestDec(apiManage,body);
        String token = commonParam.getParameterOne();
        String card = commonParam.getParameterTwo();
        String remark = commonParam.getParameterThree();
        String timestamp = commonParam.getParameterFour();
        String sign = commonParam.getParameterFive();
        if (StringUtils.isEmpty(token)||StringUtils.isEmpty(card)){
            throw new SystemApiException(2, "必传参数存在空值","",false);
        }
        //校验签名
        CommonUtil.sign(apiManage,sign,timestamp,StringUtils.trimToEmpty(token)+StringUtils.trimToEmpty(card)+StringUtils.trimToEmpty(remark)+StringUtils.trimToEmpty(timestamp));
        return new CardDisableParam(token,card,remark,timestamp,sign);
    }

    //卡密退出登录
    public static CardLogOutParam cardLogOutParameter(ApiManageApi apiManage, String body){
        CommonParam commonParam = CommonUtil.requestDec(apiManage,body);
        String token = commonParam.getParameterOne();
        String card = commonParam.getParameterTwo();
        String timestamp = commonParam.getParameterThree();
        String sign = commonParam.getParameterFour();
        if (StringUtils.isEmpty(token)||StringUtils.isEmpty(card)){
            throw new SystemApiException(2, "必传参数存在空值","",false);
        }
        //校验签名
        CommonUtil.sign(apiManage,sign,timestamp,StringUtils.trimToEmpty(token)+StringUtils.trimToEmpty(card)+StringUtils.trimToEmpty(timestamp));
        return new CardLogOutParam(token,card,timestamp,sign);
    }
}
