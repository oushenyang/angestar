package cn.stylefeng.guns.sys.core.util;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.DES;
import cn.hutool.crypto.symmetric.DESede;
import cn.stylefeng.guns.sys.core.exception.*;
import com.alibaba.fastjson.JSONObject;

/**
 * <p>返回处理工具</p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2021/6/1
 * @since JDK 1.8
 */
public class ApiResultUtil {
    //对返回参数加密
    public static Object setAlgorithm(Object object, AppInfoApi appInfoApi){
        if (appInfoApi.getWebAlgorithmType()!=0&&appInfoApi.getWebAlgorithmRange()!=1){
            //des解密
            if (appInfoApi.getWebAlgorithmType()==1){
                DES des = DESContext.getInstance(appInfoApi);
                try {
                    return des.encryptBase64(JSONObject.toJSONString(object), CharsetUtil.CHARSET_UTF_8);
                }catch (Exception ignored){
                    throw new SystemApiException(4, "系统错误","",false);
                }
                //aes解密
            }else if (appInfoApi.getWebAlgorithmType()==2){
                AES aes = AESContext.getInstance(appInfoApi);
                try {
                    return aes.encryptBase64(JSONObject.toJSONString(object), CharsetUtil.CHARSET_UTF_8);
                }catch (Exception ignored){
                    throw new SystemApiException(4, "系统错误","",false);
                }
                //DESede解密
            }else if (appInfoApi.getWebAlgorithmType()==3){
                DESede deSede = DESedeContext.getInstance(appInfoApi);
                try {
                    return deSede.encryptBase64(JSONObject.toJSONString(object), CharsetUtil.CHARSET_UTF_8);
                }catch (Exception ignored){
                    throw new SystemApiException(4, "系统错误","",false);
                }
            }
        }
        return object;
    }
}
