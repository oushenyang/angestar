package cn.stylefeng.guns.webApi.common.param;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.*;
import cn.stylefeng.guns.modular.apiManage.model.result.ApiManageApi;
import cn.stylefeng.guns.sys.core.exception.*;
import cn.stylefeng.guns.webApi.common.param.CardLoginParam;
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
    public static CardLoginParam getCardLoginParameter(ApiManageApi apiManage, AppInfoApi appInfoApi, String body){
        String decryptStr = null;
        CardLoginParam cardLoginParam = null;
        String singleCode = null;
        String edition = null;
        String mac = null;
        String model = null;
        String holdCheck = null;
        String sign = null;

        if (appInfoApi.getWebAlgorithmRange()==0||appInfoApi.getWebAlgorithmRange()==1){
            //des解密
            if (appInfoApi.getWebAlgorithmType()==1){
                DES des = DESContext.getInstance(appInfoApi);
                try {
                    decryptStr = des.decryptStr(body, CharsetUtil.CHARSET_UTF_8);
                }catch (Exception ignored){
                    throw new SystemApiException(3, "传入数据包错误","",false);
                }
                //aes解密
            }else if (appInfoApi.getWebAlgorithmType()==2){
                AES aes = AESContext.getInstance(appInfoApi);
                try {
                    decryptStr = aes.decryptStr(body, CharsetUtil.CHARSET_UTF_8);
                }catch (Exception ignored){
                    throw new SystemApiException(3, "传入数据包错误","",false);
                }
                //DESede解密
            }else if (appInfoApi.getWebAlgorithmType()==3){
                DESede deSede = DESedeContext.getInstance(appInfoApi);
                try {
                    decryptStr = deSede.decryptStr(body, CharsetUtil.CHARSET_UTF_8);
                }catch (Exception ignored){
                    throw new SystemApiException(3, "传入数据包错误","",false);
                }
            }
        }
       if (appInfoApi.getWebAlgorithmRange()==2||appInfoApi.getWebAlgorithmType()==0){
            singleCode = HttpContext.getRequest().getParameter(apiManage.getParameterOne());
            mac = HttpContext.getRequest().getParameter(apiManage.getParameterTwo());
            edition = HttpContext.getRequest().getParameter(apiManage.getParameterThree());
            model = HttpContext.getRequest().getParameter(apiManage.getParameterFour());
            holdCheck = HttpContext.getRequest().getParameter(apiManage.getParameterFive());
            sign = HttpContext.getRequest().getParameter(apiManage.getParameterSix());
            cardLoginParam = new CardLoginParam(singleCode,edition,mac,model,holdCheck,sign);
        }
        if (appInfoApi.getWebAlgorithmType()!=0&&StringUtils.isNotEmpty(decryptStr)){
            try {
                JSONObject jsonObject = JSON.parseObject(decryptStr);
                singleCode = jsonObject.getString(apiManage.getParameterOne());
                edition = jsonObject.getString(apiManage.getParameterTwo());
                mac = jsonObject.getString(apiManage.getParameterThree());
                model = jsonObject.getString(apiManage.getParameterFour());
                holdCheck = jsonObject.getString(apiManage.getParameterFive());
                sign = jsonObject.getString(apiManage.getParameterSix());
                cardLoginParam = new CardLoginParam(singleCode,edition,mac,model,holdCheck,sign);
            }catch (Exception ignored){
                throw new SystemApiException(3, "传入数据包错误","",false);
            }
        }

        if (StringUtils.isEmpty(singleCode)||StringUtils.isEmpty(edition)||StringUtils.isEmpty(mac)){
            throw new SystemApiException(2, "必传参数存在空值","",false);
        }
        if (appInfoApi.getSignFlag()&&StringUtils.isEmpty(sign)){
            throw new SystemApiException(4, "签名不正确","",false);
        }else if (appInfoApi.getSignFlag()&&StringUtils.isNotEmpty(sign)&&sign.length()!=32){
            throw new SystemApiException(4, "签名不正确","",false);
        }else if(appInfoApi.getSignFlag()&&StringUtils.isNotEmpty(sign)&&sign.length()==32){
            String md5 = SecureUtil.md5(singleCode+mac+StringUtils.trimToEmpty(edition)+StringUtils.trimToEmpty(model)+StringUtils.trimToEmpty(holdCheck));
            if (!md5.equals(sign)){
                throw new SystemApiException(4, "签名不正确","",false);
            }
        }
        String s = JSON.toJSONString(cardLoginParam);
        return cardLoginParam;
    }

    public static CheckCardStatusParam getCheckCardStatusParameter(ApiManageApi apiManage, AppInfoApi appInfoApi, String body){
        String decryptStr = null;
        CheckCardStatusParam checkCardStatusParam = null;
        String statusCode = null;
        String singleCode = null;
        String holdCheck = null;
        String sign = null;

        if (appInfoApi.getWebAlgorithmRange()==0||appInfoApi.getWebAlgorithmRange()==1){
            //des解密
            if (appInfoApi.getWebAlgorithmType()==1){
                DES des = DESContext.getInstance(appInfoApi);
                try {
                    decryptStr = des.decryptStr(body, CharsetUtil.CHARSET_UTF_8);
                }catch (Exception ignored){
                    throw new SystemApiException(3, "传入数据包错误","",false);
                }
                //aes解密
            }else if (appInfoApi.getWebAlgorithmType()==2){
                AES aes = AESContext.getInstance(appInfoApi);
                try {
                    decryptStr = aes.decryptStr(body, CharsetUtil.CHARSET_UTF_8);
                }catch (Exception ignored){
                    throw new SystemApiException(3, "传入数据包错误","",false);
                }
                //DESede解密
            }else if (appInfoApi.getWebAlgorithmType()==3){
                DESede deSede = DESedeContext.getInstance(appInfoApi);
                try {
                    decryptStr = deSede.decryptStr(body, CharsetUtil.CHARSET_UTF_8);
                }catch (Exception ignored){
                    throw new SystemApiException(3, "传入数据包错误","",false);
                }
            }
        }
        if (appInfoApi.getWebAlgorithmRange()==2||appInfoApi.getWebAlgorithmType()==0){
            statusCode = HttpContext.getRequest().getParameter(apiManage.getParameterOne());
            singleCode = HttpContext.getRequest().getParameter(apiManage.getParameterTwo());
            holdCheck = HttpContext.getRequest().getParameter(apiManage.getParameterThree());
            sign = HttpContext.getRequest().getParameter(apiManage.getParameterFour());
            checkCardStatusParam = new CheckCardStatusParam(statusCode,singleCode,holdCheck,sign);
        }
        if (appInfoApi.getWebAlgorithmType()!=0&&StringUtils.isNotEmpty(decryptStr)){
            try {
                JSONObject jsonObject = JSON.parseObject(decryptStr);
                statusCode = jsonObject.getString(apiManage.getParameterOne());
                singleCode = jsonObject.getString(apiManage.getParameterTwo());
                holdCheck = jsonObject.getString(apiManage.getParameterThree());
                sign = jsonObject.getString(apiManage.getParameterFour());
                checkCardStatusParam = new CheckCardStatusParam(statusCode,singleCode,holdCheck,sign);
            }catch (Exception ignored){
                throw new SystemApiException(3, "传入数据包错误","",false);
            }
        }

        if (StringUtils.isEmpty(singleCode)||StringUtils.isEmpty(statusCode)){
            throw new SystemApiException(2, "必传参数存在空值","",false);
        }
        if (appInfoApi.getSignFlag()&&StringUtils.isEmpty(sign)){
            throw new SystemApiException(4, "签名不正确","",false);
        }else if (appInfoApi.getSignFlag()&&StringUtils.isNotEmpty(sign)&&sign.length()!=32){
            throw new SystemApiException(4, "签名不正确","",false);
        }else if(StringUtils.isNotEmpty(sign)&&sign.length()==32){
            String md5 = SecureUtil.md5(StringUtils.trimToEmpty(statusCode)+StringUtils.trimToEmpty(singleCode)+StringUtils.trimToEmpty(holdCheck));
            if (!md5.equals(sign)){
                throw new SystemApiException(4, "签名不正确","",false);
            }
        }
        return checkCardStatusParam;
    }
}
