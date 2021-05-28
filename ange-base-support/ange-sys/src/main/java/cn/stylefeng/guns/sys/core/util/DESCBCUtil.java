package cn.stylefeng.guns.sys.core.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

/**
 * <p></p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2021/1/6
 * @since JDK 1.8
 */
public class DESCBCUtil {
    /**
     * DES/CBC/PKCS5Padding
     */
    public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";
    /**
     * DES算法，加密
     * @param data 待加密字符串
     * @return 加密后的字节数组，一般结合Base64编码使用
     */
    public static String encode(String data){
        return encode("0b31c497990cc6ee0b31c497", data.getBytes());
    }
    /**
     * DES算法，加密
     * @param data  待加密字符串
     * @param key  加密私钥，长度不能够小于8位
     * @return 加密后的字节数组，一般结合Base64编码使用
     *             异常
     */
    private static String encode(String key, byte[] data){
        try {
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec("87654321".getBytes());
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
            byte[] bytes = cipher.doFinal(data);
            return new BASE64Encoder().encode(bytes);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * DES算法，解密
     * @param data  待解密字符串
     * @param key 解密私钥，长度不能够小于8位
     * @return 解密后的字节数组
     * @throws Exception  异常
     */
    private static byte[] decode(String key, byte[] data) throws Exception {
        try {
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec("87654321".getBytes());
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * 解密.
     * @param data
     * @return
     * @throws Exception
     */
    public static String decode(String data) {
        String key = "0b31c497990cc6ee0b31c497";
        byte[] datas;
        String value = null;
        try {
            if (System.getProperty("os.name") != null
                    && (System.getProperty("os.name").equalsIgnoreCase("sunos") || System
                    .getProperty("os.name").equalsIgnoreCase("linux"))) {
                datas = decode(key, new BASE64Decoder().decodeBuffer(data));
            } else {
                datas = decode(key, new BASE64Decoder().decodeBuffer(data));
            }
            value = new String(datas);
        } catch (Exception e) {
            value = "";
        }
        return value;
    }
}
