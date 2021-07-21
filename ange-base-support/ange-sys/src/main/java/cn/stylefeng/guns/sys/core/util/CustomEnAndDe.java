package cn.stylefeng.guns.sys.core.util;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

/**
 * <p>自定义加解密</p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2020/11/6
 * @since JDK 1.8
 */
public class CustomEnAndDe {
    public static void main(String[] args) throws Exception {
        String a = deCrypto("961800CA30B85562725DF7A4F73ED2588BBFA88E1C877C55");
        System.out.println(a);

    }
    private String key = "*()&^%$#";
    /**
     * 加密（使用DES算法）
     *
     * @param txt
     *            需要加密的文本
     *            密钥
     * @return 成功加密的文本
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static String enCrypto(String txt){
        StringBuffer sb = new StringBuffer();
        DESKeySpec desKeySpec = null;
        try {
            desKeySpec = new DESKeySpec("*()&^%$#".getBytes());
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        SecretKeyFactory skeyFactory = null;
        Cipher cipher = null;
        try {
            skeyFactory = SecretKeyFactory.getInstance("DES");
            try {
                cipher = Cipher.getInstance("DES");
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        SecretKey deskey = null;
        try {
            deskey = skeyFactory.generateSecret(desKeySpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        try {
            cipher.init(Cipher.ENCRYPT_MODE, deskey);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        byte[] cipherText = new byte[0];
        try {
            cipherText = cipher.doFinal(txt.getBytes());
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        for (int n = 0; n < cipherText.length; n++) {
            String stmp = (java.lang.Integer.toHexString(cipherText[n] & 0XFF));

            if (stmp.length() == 1) {
                sb.append("0" + stmp);
            } else {
                sb.append(stmp);
            }
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 解密（使用DES算法）
     *
     * @param txt
     *            需要解密的文本
     *            密钥
     * @return 成功解密的文本
     * @throws InvalidKeyException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static String deCrypto(String txt){
        DESKeySpec desKeySpec = null;
        String result = null;
        try {
            desKeySpec = new DESKeySpec("*()&^%$#".getBytes());
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        SecretKeyFactory skeyFactory = null;
        Cipher cipher = null;
        try {
            skeyFactory = SecretKeyFactory.getInstance("DES");
            cipher = Cipher.getInstance("DES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        SecretKey deskey = null;
        try {
            deskey = skeyFactory.generateSecret(desKeySpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        try {
            cipher.init(Cipher.DECRYPT_MODE, deskey);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        byte[] btxts = new byte[txt.length() / 2];
        for (int i = 0, count = txt.length(); i < count; i += 2) {
            btxts[i / 2] = (byte) Integer.parseInt(txt.substring(i, i + 2), 16);
        }
        try {
            result = new String(cipher.doFinal(btxts));
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
