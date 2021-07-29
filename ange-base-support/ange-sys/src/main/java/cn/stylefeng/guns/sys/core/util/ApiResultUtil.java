package cn.stylefeng.guns.sys.core.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.DES;
import cn.hutool.crypto.symmetric.DESede;
import cn.hutool.crypto.symmetric.SM4;
import cn.stylefeng.guns.sys.core.exception.*;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>返回处理工具</p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2021/6/1
 * @since JDK 1.8
 */
public class ApiResultUtil {
    public static String setCustomResultData(String customResultData,String timestamp){
        //TODO
        if (StringUtils.contains(customResultData, "%holdCheck%")){
            if (StringUtils.isNotEmpty(timestamp)){
                customResultData = customResultData.replaceAll("%holdCheck%",timestamp);
            }else {
                customResultData = customResultData.replaceAll("%holdCheck%","");
            }
        }
        if (StringUtils.contains(customResultData, "%timestamp10%")){
            customResultData = customResultData.replaceAll("%timestamp10%",String.valueOf(System.currentTimeMillis() / 1000));
        }
        if (StringUtils.contains(customResultData, "%timestamp13%")){
            customResultData = customResultData.replaceAll("%timestamp13%",String.valueOf(System.currentTimeMillis()));
        }
        if (StringUtils.contains(customResultData, "%currentTime%")){
            customResultData = customResultData.replaceAll("%currentTime%",DateUtil.now());
        }
        return customResultData;
    }
    //对返回参数加密
    public static Object setAlgorithm(Object object, ApiManageApi apiManageApi){
        if (apiManageApi.getWebAlgorithmType()!=0&&apiManageApi.getWebAlgorithmRange()!=1){
            //des解密
            if (apiManageApi.getWebAlgorithmType()==1){
                DES des = DESContext.getInstance(apiManageApi);
                try {
                    if (apiManageApi.getWebAlgorithmOutput()==0){
                        return des.encryptBase64(JSONObject.toJSONString(object), CharsetUtil.CHARSET_UTF_8);
                    }else {
                        return des.encryptHex(JSONObject.toJSONString(object), CharsetUtil.CHARSET_UTF_8);
                    }
                }catch (Exception ignored){
                    throw new SystemApiException(4, "系统错误","",false);
                }
                //aes解密
            }else if (apiManageApi.getWebAlgorithmType()==2){
                AES aes = AESContext.getInstance(apiManageApi);
                try {
                    if (apiManageApi.getWebAlgorithmOutput()==0){
                        return aes.encryptBase64(JSONObject.toJSONString(object), CharsetUtil.CHARSET_UTF_8);
                    }else {
                        return aes.encryptHex(JSONObject.toJSONString(object), CharsetUtil.CHARSET_UTF_8);
                    }
                }catch (Exception ignored){
                    throw new SystemApiException(4, "系统错误","",false);
                }
                //DESede解密
            }else if (apiManageApi.getWebAlgorithmType()==3){
                DESede deSede = DESedeContext.getInstance(apiManageApi);
                try {
                    if (apiManageApi.getWebAlgorithmOutput()==0){
                        return deSede.encryptBase64(JSONObject.toJSONString(object), CharsetUtil.CHARSET_UTF_8);
                    }else {
                        return deSede.encryptHex(JSONObject.toJSONString(object), CharsetUtil.CHARSET_UTF_8);
                    }
                }catch (Exception ignored){
                    throw new SystemApiException(4, "系统错误","",false);
                }
            }else if (apiManageApi.getWebAlgorithmType()==4){
                SM4 sm4 = SM4Context.getInstance(apiManageApi);
                try {
                    if (apiManageApi.getWebAlgorithmOutput()==0){
                        return sm4.encryptBase64(JSONObject.toJSONString(object), CharsetUtil.CHARSET_UTF_8);
                    }else {
                        return sm4.encryptHex(JSONObject.toJSONString(object), CharsetUtil.CHARSET_UTF_8);
                    }
                }catch (Exception ignored){
                    throw new SystemApiException(4, "系统错误","",false);
                }
            }
        }
        return object;
    }
}
