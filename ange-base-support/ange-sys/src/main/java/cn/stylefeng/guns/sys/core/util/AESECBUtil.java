package cn.stylefeng.guns.sys.core.util;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * <p></p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2021/1/6
 * @since JDK 1.8
 */
public class AESECBUtil {
    // 加密
    public static String Encrypt(String sSrc, String sKey){
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }
        byte[] raw = new byte[0];
        try {
            raw = sKey.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = null;//"算法/模式/补码方式"
        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
        try {
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        byte[] encrypted = new byte[0];
        try {
            encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
        } catch (IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new BASE64Encoder().encode(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }

    // 解密
    public static String Decrypt(String sSrc, String sKey) throws Exception {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original,"utf-8");
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        /*
         * 此处使用AES-128-ECB加密模式，key需要为16位。
         */
        String cKey = "0b31c497990cc6ee";
        // 需要加密的字串
        String cSrc = "{\"status\": 0, \"appurl\": \"www.lanzous.com/clone\", \"leftmoney\": \"0\", \"inchina\": 1, \"versioncode\": \"10\", \"tag\": 0, \"oldinviteid\": \"\", \"stonetime\": 1546272000000, \"validnum\": 0, \"googleversioncode\": \"6\", \"proxyratio\": \"0.1\", \"totalnum\": 0, \"totalmoney\": \"0\", \"points\": 0, \"closed\": 0, \"viptype\": 6, \"username\": \"15156041422\", \"realname\": \"\", \"inviteid\": \"5D8CLY\", \"zfb\": \"\", \"imsis\": \"\", \"virtual_id\": \"8b3b3057811a9f29f2726fb769af7fbb\", \"imeis\": \"Xiaomi_RedmiK20ProPremiumEdition|XhAxKdCn3TwDAFwETYuwio1m\", \"invitenum\": 0, \"proxyid\": \"5D8CLY\", \"fatherid\": \"0\", \"userid\": \"Xiaomi_RedmiK20ProPremiumEdition|WvMZuYLQ0W4DAArXTsQddFXj\", \"vipphone\": \"None\", \"channel\": \"china\", \"starttime\": 1558276363000, \"deadline\": 3818997416000}";
//        // 加密
//        String enString = AESECBUtil.Encrypt(cSrc, cKey);
//        System.out.println("加密后的字串是：" + enString);

        // 解密
//        String DeString = AESECBUtil.Decrypt(cSrc, cKey);
//        System.out.println("解密后的字串是：" + DeString);

        // 加密
        String enString = AESECBUtil.Encrypt(cSrc, cKey);
        System.out.println("加密后的字串是：" + enString);
    }


}
