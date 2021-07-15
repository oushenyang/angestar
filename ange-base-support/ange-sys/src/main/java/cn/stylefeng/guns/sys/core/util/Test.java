package cn.stylefeng.guns.sys.core.util;


import cn.hutool.core.io.resource.ResourceUtil;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.imageio.ImageIO;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p></p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2020/4/8
 * @since JDK 1.8
 */
public class Test {
//    /**
//     * @throws IOException
//     * @throws
//     **/
    public static void main(String[] args) throws Exception {
        String a = String.valueOf(a("15258908FE7EB30327F269CF6E8F6709142AC251D1CDE6C47777026B4164B9AE078F32E349FFB3FC2C7FB7DEED11EC38FE7E8C7CB532382FE7B8F7EFE444B216DAB59821C8BFE525", Charset.forName("utf8"), "Z1drRB6A03GcZKWF"));
        System.out.println(a);

    }
        public static String a(String str, String str2) throws NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidKeyException, InvalidKeySpecException, InvalidAlgorithmParameterException {
            byte[] a = str.getBytes();
            Cipher instance = null;
            try {
                instance = Cipher.getInstance("DES/CBC/PKCS5Padding");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            }
            instance.init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(str2.getBytes("UTF-8"))), new IvParameterSpec(str2.getBytes("UTF-8")));
            return new String(instance.doFinal(a));
        }

        public static String a(byte[] bArr) {
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : bArr) {
                String hexString = Integer.toHexString(b & 255);
                if (hexString.length() < 2) {
                    hexString = "0" + hexString;
                }
                stringBuffer.append(hexString);
            }
            return stringBuffer.toString();
        }

        public static byte[] a(String s, Charset utf8, String str) {
            byte[] bArr = new byte[(str.length() / 2)];
            for (int i = 0; i < bArr.length; i++) {
                int i2 = i * 2;
                bArr[i] = (byte) Integer.parseInt(str.substring(i2, i2 + 2), 16);
            }
            return bArr;
        }

        public static byte[] b(String str, String str2) throws NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException, InvalidKeySpecException, InvalidAlgorithmParameterException {
            Cipher instance = Cipher.getInstance("DES/CBC/PKCS5Padding");
            instance.init(1, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(str2.getBytes("UTF-8"))), new IvParameterSpec(str2.getBytes("UTF-8")));
            return instance.doFinal(str.getBytes("UTF-8"));
        }

}
