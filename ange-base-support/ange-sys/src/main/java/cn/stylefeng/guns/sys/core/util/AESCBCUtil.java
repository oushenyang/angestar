package cn.stylefeng.guns.sys.core.util;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * <p></p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2021/1/6
 * @since JDK 1.8
 */
public class AESCBCUtil {
    private static final String ENCODING = "GBK";

    private static final String KEY_ALGORITHM = "AES";
    /**
     * 加解密算法/工作模式/填充方式
     */
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
    /**
     * 填充向量
     */
    private static final String FILL_VECTOR = "1234560405060708";

    /**
     * 加密字符串
     *
     * @param content  字符串
     * @param password 密钥KEY
     * @return
     * @throws Exception
     */
    public static String encrypt(String content, String password) {
        if (StringUtils.isAnyEmpty(content, password)) {
//            LOGGER.error("AES encryption params is null");
            return null;
        }

        byte[] raw = hex2byte(password);
        SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(FILL_VECTOR.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] anslBytes = content.getBytes(ENCODING);
            byte[] encrypted = cipher.doFinal(anslBytes);
            return byte2hex(encrypted).toUpperCase();
        } catch (Exception e) {
//            LOGGER.error("AES encryption operation has exception,content:{},password:{}", content, password, e);
        }
        return null;
    }

    /**
     * 解密
     *
     * @param content  解密前的字符串
     * @param password 解密KEY
     * @return
     * @throws Exception
     * @author cdduqiang
     * @date 2014年4月3日
     */
    public static String decrypt(String content, String password) {
        if (StringUtils.isAnyEmpty(content, password)) {
//            LOGGER.error("AES decryption params is null");
            return null;
        }

        try {
            byte[] raw = hex2byte(password);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(FILL_VECTOR.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = hex2byte(content);
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original, ENCODING);
        } catch (Exception e) {
//            LOGGER.error("AES decryption operation has exception,content:{},password:{}", content, password, e);
        }
        return null;
    }

    public static byte[] hex2byte(String strhex) {
        if (strhex == null) {
            return null;
        }
        int l = strhex.length();
        if (l % 2 == 1) {
            return null;
        }
        byte[] b = new byte[l / 2];
        for (int i = 0; i != l / 2; i++) {
            b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2), 16);
        }
        return b;
    }

    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }

    public static void main(String[] args) throws Exception {
        String str = "xUvUKsseUguS/EIHVaqRv9WQkGaNmBxyKNoWPSofgR84xC7JDb2aIv98T1em0KST419fWB9ms+Fg059cuN7++E9w0FcxYFkeyQSns0ssjKmTzuhlqXHSdpCGv8Teqnl5Pq+Uu8q3vT+6cmW+GnS7+tXQh8lSS99tYJjJu/DyHy8pJNTri8DdIjovvTOY5yY5/mUlZt1qym4MFor51cRpgejOn7YguaMW3H+j6rLCqwjyd9ImAuNlmB5iI4Pqc3i4GBrU1EFatkREvpMLH/BreZ/BjiWL6qctASOyI4Ad9Tr8EQxASuDp+HuwPtEYV9PO9nln8ZTBBh5wCCArz0hwlcWCzQYTqArVDvYuqgh+uOZFBP23h5natRo0mnx7113dAbKNjKi1Iau5go4RJK1izTiQpjaTJNNqmFmUGLZK+qKk7YAjKEvH+wcTJFaTLPrioNn90BNOYUtf8RxAU9qkbBno62DtPEcCXdzF406AgHzZnAmzCD7syElh5ufErVRaKuTdyjoCC/mC5fc9K2kIgnkxDjcaR9o6lVEy0Xgyo9pb7MN1kCfEdetoPE3QzgSKV/O2Mez2eCNJ5SK8VfH7FiYrP4wb68UbwtZzY8jmr9sZb911McGk8VxURIusKH8B64dnZpxZILrpmwya9uzBBtXmNuRJ+uhVInMFKLIzdXW5BrMu55BWfywuAtkUEb1I";
        //必须为16位
        String key = "3738400000000000";
        //生成加密密钥
        String key2 = byte2hex(key.getBytes());
        System.out.println(key2);
        String encryptStr = decrypt(str, key2);
        System.out.println(encryptStr);
//        System.out.println(decrypt(encryptStr, key2));
    }

}
