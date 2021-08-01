package cn.stylefeng.guns.sys.core.exception;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import cn.stylefeng.guns.sys.core.exception.apiResult.ApiManageApi;
import org.apache.commons.lang3.StringUtils;

/**
 * aes加解密（单例）
 */
public class AESContext {
    private static AES aes = null;
    private static Integer encryptionMode = 10;
    private static Integer fill = 10;
    private static String webKey = "0";
    private static String webSalt = "0";

    public static AES getInstance(ApiManageApi appInfoApi) {
        if (aes == null||!encryptionMode.equals(appInfoApi.getEncryptionMode())
                ||!fill.equals(appInfoApi.getFill())
                ||!webKey.equals(appInfoApi.getWebKey())
                ||!webSalt.equals(appInfoApi.getWebSalt())) {
            encryptionMode = appInfoApi.getEncryptionMode();
            fill = appInfoApi.getFill();
            webKey = appInfoApi.getWebKey();
            webSalt = appInfoApi.getWebSalt();
            Mode mode = Mode.ECB;
            Padding padding = Padding.PKCS5Padding;
            switch (appInfoApi.getEncryptionMode()){
                case 0:
                    mode = Mode.ECB;
                    break;
                case 1:
                    mode = Mode.CBC;
                    break;
                case 2:
                    mode = Mode.CTR;
                    break;
                case 3:
                    mode = Mode.CTS;
                    break;
                case 4:
                    mode = Mode.CFB;
                    break;
                case 5:
                    mode = Mode.OFB;
                    break;
            }

            switch (appInfoApi.getFill()){
                case 0:
                    padding = Padding.PKCS5Padding;
                    break;
                case 2:
                    padding = Padding.ISO10126Padding;
                    break;
                case 3:
                    padding = Padding.ZeroPadding;
                    break;
                case 4:
                    padding = Padding.NoPadding;
                    break;
            }
            if (StringUtils.isNotEmpty(appInfoApi.getWebSalt())&&appInfoApi.getEncryptionMode()!=0){
                if (appInfoApi.getFill()==1){
                    aes = new AES(mode.toString(), "PKCS7Padding", appInfoApi.getWebKey().getBytes(),appInfoApi.getWebSalt().getBytes());
                }else {
                    aes = new AES(mode, padding, appInfoApi.getWebKey().getBytes(),appInfoApi.getWebSalt().getBytes());
                }
            }else {
                if (appInfoApi.getFill()==1){
                    aes = new AES(mode.toString(), "PKCS7Padding", appInfoApi.getWebKey().getBytes());
                }else {
                    aes = new AES(mode, padding, appInfoApi.getWebKey().getBytes());
                }
            }
        }
        return aes;
    }
}
