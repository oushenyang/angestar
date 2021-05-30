package cn.stylefeng.guns.sys.core.exception;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.DES;
import cn.stylefeng.guns.sys.core.exception.AppInfoApi;
import org.apache.commons.lang3.StringUtils;

/**
 * des加解密（单例）
 */
public class DESContext {
    private static DES des = null;
    private static Integer encryptionMode = 10;
    private static Integer fill = 10;
    private static String webKey = "0";
    private static String webSalt = "0";

    public static DES getInstance(AppInfoApi appInfoApi) {

        if (des == null||!encryptionMode.equals(appInfoApi.getEncryptionMode())
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
                case 6:
                    mode = Mode.PCBC;
                    break;
            }

            switch (appInfoApi.getFill()){
                case 0:
                    padding = Padding.PKCS5Padding;
                    break;
                case 2:
                    padding = Padding.PKCS1Padding;
                    break;
                case 3:
                    padding = Padding.ISO10126Padding;
                    break;
                case 4:
                    padding = Padding.SSL3Padding;
                    break;
                case 5:
                    padding = Padding.ZeroPadding;
                    break;
                case 6:
                    padding = Padding.OAEPPadding;
                    break;
                case 7:
                    padding = Padding.NoPadding;
                    break;
            }
            if (StringUtils.isNotEmpty(appInfoApi.getWebSalt())&&appInfoApi.getEncryptionMode()!=0){
                if (appInfoApi.getFill()==1){
                    des = new DES(mode.toString(), "PKCS7Padding", appInfoApi.getWebKey().getBytes(),appInfoApi.getWebSalt().getBytes());
                }else {
                    des = new DES(mode, padding, appInfoApi.getWebKey().getBytes(),appInfoApi.getWebSalt().getBytes());
                }
            }else {
                if (appInfoApi.getFill()==1){
                    des = new DES(mode.toString(), "PKCS7Padding", appInfoApi.getWebKey().getBytes());
                }else {
                    des = new DES(mode, padding, appInfoApi.getWebKey().getBytes());
                }
            }
        }
        return des;
    }
}
