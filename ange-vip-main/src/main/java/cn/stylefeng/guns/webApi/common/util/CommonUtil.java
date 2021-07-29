package cn.stylefeng.guns.webApi.common.util;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.DES;
import cn.hutool.crypto.symmetric.DESede;
import cn.hutool.crypto.symmetric.SM4;
import cn.stylefeng.guns.sys.core.exception.*;
import cn.stylefeng.guns.sys.core.exception.enums.ApiExceptionEnum;
import cn.stylefeng.guns.webApi.common.param.CardLoginParam;
import cn.stylefeng.guns.webApi.common.param.CommonParam;
import cn.stylefeng.roses.core.util.HttpContext;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

public class CommonUtil {
    //传入数据处理
    public static CommonParam requestDec(ApiManageApi apiManage, String body){
        String decryptStr = null;
        String parameterOne = null;
        String parameterTwo = null;
        String parameterThree = null;
        String parameterFour = null;
        String parameterFive = null;
        String parameterSix = null;
        String parameterSeven = null;
        if ((apiManage.getWebAlgorithmRange()==0||apiManage.getWebAlgorithmRange()==1)&&apiManage.getPostType()==0&&apiManage.getWebAlgorithmType()!=0){
            //des解密
            if (apiManage.getWebAlgorithmType()==1){
                DES des = DESContext.getInstance(apiManage);
                try {
                    decryptStr = des.decryptStr(body, CharsetUtil.CHARSET_UTF_8);
                }catch (Exception ignored){
                    //传入数据包错误
                    throw new SystemApiException(ApiExceptionEnum.DATA_ERROR.getCode(), ApiExceptionEnum.DATA_ERROR.getMessage(),"",false);
                }
                //aes解密
            }else if (apiManage.getWebAlgorithmType()==2){
                AES aes = AESContext.getInstance(apiManage);
                try {
                    decryptStr = aes.decryptStr(body, CharsetUtil.CHARSET_UTF_8);
                }catch (Exception ignored){
                    //传入数据包错误
                    throw new SystemApiException(ApiExceptionEnum.DATA_ERROR.getCode(), ApiExceptionEnum.DATA_ERROR.getMessage(),"",false);
                }
                //DESede解密
            }else if (apiManage.getWebAlgorithmType()==3){
                DESede deSede = DESedeContext.getInstance(apiManage);
                try {
                    decryptStr = deSede.decryptStr(body, CharsetUtil.CHARSET_UTF_8);
                }catch (Exception ignored){
                    //传入数据包错误
                    throw new SystemApiException(ApiExceptionEnum.DATA_ERROR.getCode(), ApiExceptionEnum.DATA_ERROR.getMessage(),"",false);
                }
                //SM4解密
            }else if (apiManage.getWebAlgorithmType()==4){
                DESede deSede = DESedeContext.getInstance(apiManage);
                try {
                    decryptStr = deSede.decryptStr(body, CharsetUtil.CHARSET_UTF_8);
                }catch (Exception ignored){
                    //传入数据包错误
                    throw new SystemApiException(ApiExceptionEnum.DATA_ERROR.getCode(), ApiExceptionEnum.DATA_ERROR.getMessage(),"",false);
                }
            }
        }

        if ((apiManage.getWebAlgorithmRange()==0||apiManage.getWebAlgorithmRange()==1)&&apiManage.getPostType()==1&&apiManage.getWebAlgorithmType()!=0){
            parameterOne = HttpContext.getRequest().getParameter(apiManage.getParameterOne());
            parameterTwo = HttpContext.getRequest().getParameter(apiManage.getParameterTwo());
            parameterThree = HttpContext.getRequest().getParameter(apiManage.getParameterThree());
            parameterFour = HttpContext.getRequest().getParameter(apiManage.getParameterFour());
            parameterFive = HttpContext.getRequest().getParameter(apiManage.getParameterFive());
            parameterSix = HttpContext.getRequest().getParameter(apiManage.getParameterSix());
            parameterSeven = HttpContext.getRequest().getParameter(apiManage.getParameterSeven());
            //des解密
            if (apiManage.getWebAlgorithmType()==1){
                DES des = DESContext.getInstance(apiManage);
                try {
                    if (StringUtils.isNotEmpty(parameterOne)){
                        parameterOne = des.decryptStr(parameterTwo, CharsetUtil.CHARSET_UTF_8);
                    }
                    if (StringUtils.isNotEmpty(parameterTwo)){
                        parameterTwo = des.decryptStr(parameterTwo, CharsetUtil.CHARSET_UTF_8);
                    }
                    if (StringUtils.isNotEmpty(parameterThree)){
                        parameterThree = des.decryptStr(parameterThree, CharsetUtil.CHARSET_UTF_8);
                    }
                    if (StringUtils.isNotEmpty(parameterFour)){
                        parameterFour = des.decryptStr(parameterFour, CharsetUtil.CHARSET_UTF_8);
                    }
                    if (StringUtils.isNotEmpty(parameterFive)){
                        parameterFive = des.decryptStr(parameterFive, CharsetUtil.CHARSET_UTF_8);
                    }
                    if (StringUtils.isNotEmpty(parameterSix)){
                        parameterSix = des.decryptStr(parameterSix, CharsetUtil.CHARSET_UTF_8);
                    }
                    if (StringUtils.isNotEmpty(parameterSeven)){
                        parameterSeven = des.decryptStr(parameterSeven, CharsetUtil.CHARSET_UTF_8);
                    }
                }catch (Exception ignored){
                    //传入数据包错误
                    throw new SystemApiException(ApiExceptionEnum.DATA_ERROR.getCode(), ApiExceptionEnum.DATA_ERROR.getMessage(),"",false);
                }
                //aes解密
            }else if (apiManage.getWebAlgorithmType()==2){
                AES aes = AESContext.getInstance(apiManage);
                try {
                    if (StringUtils.isNotEmpty(parameterOne)){
                        parameterOne = aes.decryptStr(parameterTwo, CharsetUtil.CHARSET_UTF_8);
                    }
                    if (StringUtils.isNotEmpty(parameterTwo)){
                        parameterTwo = aes.decryptStr(parameterTwo, CharsetUtil.CHARSET_UTF_8);
                    }
                    if (StringUtils.isNotEmpty(parameterThree)){
                        parameterThree = aes.decryptStr(parameterThree, CharsetUtil.CHARSET_UTF_8);
                    }
                    if (StringUtils.isNotEmpty(parameterFour)){
                        parameterFour = aes.decryptStr(parameterFour, CharsetUtil.CHARSET_UTF_8);
                    }
                    if (StringUtils.isNotEmpty(parameterFive)){
                        parameterFive = aes.decryptStr(parameterFive, CharsetUtil.CHARSET_UTF_8);
                    }
                    if (StringUtils.isNotEmpty(parameterSix)){
                        parameterSix = aes.decryptStr(parameterSix, CharsetUtil.CHARSET_UTF_8);
                    }
                    if (StringUtils.isNotEmpty(parameterSeven)){
                        parameterSeven = aes.decryptStr(parameterSeven, CharsetUtil.CHARSET_UTF_8);
                    }
                }catch (Exception ignored){
                    //传入数据包错误
                    throw new SystemApiException(ApiExceptionEnum.DATA_ERROR.getCode(), ApiExceptionEnum.DATA_ERROR.getMessage(),"",false);
                }
                //DESede解密
            }else if (apiManage.getWebAlgorithmType()==3){
                DESede deSede = DESedeContext.getInstance(apiManage);
                try {
                    if (StringUtils.isNotEmpty(parameterOne)){
                        parameterOne = deSede.decryptStr(parameterTwo, CharsetUtil.CHARSET_UTF_8);
                    }
                    if (StringUtils.isNotEmpty(parameterTwo)){
                        parameterTwo = deSede.decryptStr(parameterTwo, CharsetUtil.CHARSET_UTF_8);
                    }
                    if (StringUtils.isNotEmpty(parameterThree)){
                        parameterThree = deSede.decryptStr(parameterThree, CharsetUtil.CHARSET_UTF_8);
                    }
                    if (StringUtils.isNotEmpty(parameterFour)){
                        parameterFour = deSede.decryptStr(parameterFour, CharsetUtil.CHARSET_UTF_8);
                    }
                    if (StringUtils.isNotEmpty(parameterFive)){
                        parameterFive = deSede.decryptStr(parameterFive, CharsetUtil.CHARSET_UTF_8);
                    }
                    if (StringUtils.isNotEmpty(parameterSix)){
                        parameterSix = deSede.decryptStr(parameterSix, CharsetUtil.CHARSET_UTF_8);
                    }
                    if (StringUtils.isNotEmpty(parameterSeven)){
                        parameterSeven = deSede.decryptStr(parameterSeven, CharsetUtil.CHARSET_UTF_8);
                    }
                }catch (Exception ignored){
                    //传入数据包错误
                    throw new SystemApiException(ApiExceptionEnum.DATA_ERROR.getCode(), ApiExceptionEnum.DATA_ERROR.getMessage(),"",false);
                }
                //SM4解密
            }else if (apiManage.getWebAlgorithmType()==4){
                SM4 sm4 = SM4Context.getInstance(apiManage);
                try {
                    if (StringUtils.isNotEmpty(parameterOne)){
                        parameterOne = sm4.decryptStr(parameterTwo, CharsetUtil.CHARSET_UTF_8);
                    }
                    if (StringUtils.isNotEmpty(parameterTwo)){
                        parameterTwo = sm4.decryptStr(parameterTwo, CharsetUtil.CHARSET_UTF_8);
                    }
                    if (StringUtils.isNotEmpty(parameterThree)){
                        parameterThree = sm4.decryptStr(parameterThree, CharsetUtil.CHARSET_UTF_8);
                    }
                    if (StringUtils.isNotEmpty(parameterFour)){
                        parameterFour = sm4.decryptStr(parameterFour, CharsetUtil.CHARSET_UTF_8);
                    }
                    if (StringUtils.isNotEmpty(parameterFive)){
                        parameterFive = sm4.decryptStr(parameterFive, CharsetUtil.CHARSET_UTF_8);
                    }
                    if (StringUtils.isNotEmpty(parameterSix)){
                        parameterSix = sm4.decryptStr(parameterSix, CharsetUtil.CHARSET_UTF_8);
                    }
                    if (StringUtils.isNotEmpty(parameterSeven)){
                        parameterSeven = sm4.decryptStr(parameterSeven, CharsetUtil.CHARSET_UTF_8);
                    }
                }catch (Exception ignored){
                    //传入数据包错误
                    throw new SystemApiException(ApiExceptionEnum.DATA_ERROR.getCode(), ApiExceptionEnum.DATA_ERROR.getMessage(),"",false);
                }
            }
        }

        if (apiManage.getWebAlgorithmRange()==2||apiManage.getWebAlgorithmType()==0){
            parameterOne = HttpContext.getRequest().getParameter(apiManage.getParameterOne());
            parameterTwo = HttpContext.getRequest().getParameter(apiManage.getParameterTwo());
            parameterThree = HttpContext.getRequest().getParameter(apiManage.getParameterThree());
            parameterFour = HttpContext.getRequest().getParameter(apiManage.getParameterFour());
            parameterFive = HttpContext.getRequest().getParameter(apiManage.getParameterFive());
            parameterSix = HttpContext.getRequest().getParameter(apiManage.getParameterSix());
            parameterSeven = HttpContext.getRequest().getParameter(apiManage.getParameterSeven());
        }
        if (apiManage.getWebAlgorithmType()!=0&& StringUtils.isNotEmpty(decryptStr)){
            try {
                JSONObject jsonObject = JSON.parseObject(decryptStr);
                parameterOne = jsonObject.getString(apiManage.getParameterOne());
                parameterTwo = jsonObject.getString(apiManage.getParameterTwo());
                parameterThree = jsonObject.getString(apiManage.getParameterThree());
                parameterFour = jsonObject.getString(apiManage.getParameterFour());
                parameterFive = jsonObject.getString(apiManage.getParameterFive());
                parameterSix = jsonObject.getString(apiManage.getParameterSix());
                parameterSeven = jsonObject.getString(apiManage.getParameterSeven());
            }catch (Exception ignored){
                //传入数据包错误
                throw new SystemApiException(ApiExceptionEnum.DATA_ERROR.getCode(), ApiExceptionEnum.DATA_ERROR.getMessage(),"",false);
            }
        }
        return new CommonParam(parameterOne,parameterTwo,parameterThree,parameterFour,parameterFive,parameterSix,parameterSeven);
    }

    public static void overtime(ApiManageApi apiManage, String timestamp){
        if (StringUtils.isNotEmpty(timestamp)){
            if (!StringUtils.isNumeric(timestamp)||timestamp.length()<10||timestamp.length()>13){
                //传入时间戳格式错误
                throw new CommonException(ApiExceptionEnum.TIMESTAMP_TYPE_ERROR.getCode(), ApiExceptionEnum.TIMESTAMP_TYPE_ERROR.getMessage(),"",apiManage,false);
            }
        }
        if (apiManage.getDataOvertime()>0){
            if (StringUtils.isEmpty(timestamp)){
                //已开启超时验证，时间戳必传
                throw new CommonException(ApiExceptionEnum.TIMESTAMP_NULL.getCode(), ApiExceptionEnum.TIMESTAMP_NULL.getMessage(),timestamp,apiManage,false);
            }
            long timestamp1 = Long.parseLong(timestamp.substring(0,10));
            long now = Long.parseLong(String.format("%010d", System.currentTimeMillis()/1000));
            if ((now-timestamp1)>apiManage.getDataOvertime()){
                //数据包超时
                throw new CommonException(ApiExceptionEnum.DATA_OVERTIME.getCode(), ApiExceptionEnum.DATA_OVERTIME.getMessage(),timestamp,apiManage,false);
            }
        }
        if (apiManage.getApiStatus()==1){
            //接口未开启
            throw new CommonException(ApiExceptionEnum.API_CLOSE.getCode(), ApiExceptionEnum.API_CLOSE.getMessage(), timestamp,apiManage, false);
        }
    }
}
