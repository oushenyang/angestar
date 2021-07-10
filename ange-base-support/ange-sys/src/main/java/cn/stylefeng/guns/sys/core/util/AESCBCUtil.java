package cn.stylefeng.guns.sys.core.util;


import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.symmetric.*;
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

        SM4 aes = new SM4("OFB", "PKCS7Padding",
                // 密钥，可以自定义
                "DYgjCEIMDYgjCEIM".getBytes(),
                // iv加盐，按照实际需求添加
                "DYgjCEIMDYgjCEIM".getBytes());


        String privateKeyBase64 = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIliGHFUa/PTm4PDjLB1Gjl0lo3Jt3busWRvbyS7upXQvE3wxK8QovyxYtY3TUSIpD6mx2JHGd2XT/gG897x07/TNmPLCs4xVTerqS1eIn2QwU5/23bKmWA83ssk+ZsffvxSdqryAjHMi0t1pDENhF58/9HOnp+LBI4ccqoBE+p1AgMBAAECgYAzXWjUHoNKI4jWh+t3IiFO8AdGNfARWIccjQlaC5JkZymwXl+7MJYvskbI3t5VzFzUS9jqJrlF/0fX7QmLBpFrb9QgV8y2qf26zwfLGLJuvjMjy6YmvySKiD4C0Bd1VCrIopcDE+D0lRKx/yENPrtVkx+wFqdX64PwcC1skFvC1QJBAL7W9ALSEHekanu+VFwn3+uarMYP4uL81DqY89B8da5vMAS5pC9IkNj4u87Cm/coPcX2RSASf2vFx8iXDjCu2wcCQQC4SpTMkTpxULdSmPJj+Q3zh+EFJ7Dk3wduCQDr14O0aAqZcjQVeyLeSaKt0WUdH7n3r1eFNjVCXIUR7IFaL6OjAkEAj+/kfzQdQ3/46Hg3fIJ+u18gLQrSX8297KxsSLV2tSgbmZTDJv6ecWe5j0rtA8+QN/11Salp/clg1ARKqaFYhQJAWtEmEsleq4jDTojgqjOJlIFZeljc62ydFLSLJ63E0ZqT3ppQ4GUWAcT3zgBqe7euxUg7MQJNrK47RWHvPKpNUQJAHv7dGXo/4i7bUG+nqpxBO6DBUj/OD7YwCBe5cuFoQJiElge4HYPCsShAIFlzmoZsuEfXFvmWUCuZQ5mBbhURjA==";
        String publicKeyBase64 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCJYhhxVGvz05uDw4ywdRo5dJaNybd27rFkb28ku7qV0LxN8MSvEKL8sWLWN01EiKQ+psdiRxndl0/4BvPe8dO/0zZjywrOMVU3q6ktXiJ9kMFOf9t2yplgPN7LJPmbH378Unaq8gIxzItLdaQxDYRefP/Rzp6fiwSOHHKqARPqdQIDAQAB";
        RSA rsa = new RSA(privateKeyBase64,publicKeyBase64);
        //公钥加密，私钥解密
        String encrypt = rsa.encryptBase64("我是一段测试aaaa", KeyType.PublicKey);
        System.out.println(encrypt);
        String decrypt = rsa.decryptStr(encrypt, KeyType.PrivateKey);
        System.out.println(decrypt);
        SymmetricCrypto des = new SymmetricCrypto(SymmetricAlgorithm.DESede, "0123456789ABHAEQ01234567".getBytes());
        String encryptHex = aes.encryptHex("测试");
        System.out.println(encryptHex);
        String decryptStr = aes.decryptStr(encryptHex);
        System.out.println(decryptStr);
    }

}
