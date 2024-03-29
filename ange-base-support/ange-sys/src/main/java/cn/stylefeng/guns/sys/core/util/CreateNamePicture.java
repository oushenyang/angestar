package cn.stylefeng.guns.sys.core.util;


import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.http.HtmlUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import sun.misc.BASE64Encoder;
import sun.security.krb5.internal.crypto.Des;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resources;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.imageio.ImageIO;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import static org.apache.catalina.manager.Constants.CHARSET;
import static sun.security.x509.CertificateAlgorithmId.ALGORITHM;

/**
 * <p></p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2020/4/8
 * @since JDK 1.8
 */
public class CreateNamePicture {
//    /**
//     * @throws IOException
//     * @throws
//     **/
    public static void main(String[] args) throws Exception {
        String a = decrypt("15258908FE7EB30327F269CF6E8F6709142AC251D1CDE6C47777026B4164B9AE078F32E349FFB3FCB54D2518BFD5EF927D7127F368505FF819DD3777EA4803985CAECB0B81A05A8DA62B3BC797F738404F2B1B4C8637F8A9470DC9C07E633E93E269EA3CC4BEF74655368D2CA07DC5A45E55CDBE5709D82976B84021878F6D3DBB567CDB0E1C0ABB327A9A90BD9FFCAC3518454392FFCE42580D16A6F7C23C8790887468AE239791C7E7768A3877722BB402A6283217E6E65592EB67D8EF3BC5A8132E88D7906C863F43D705FD52EF44A27BF9E48F990D90E1E25863E77914D146AE88632A4A5F04859FEC262960D428F59C91E823ED9B942A62702910FE1F2226CC6543606E2DA034D8CCECD9D5B88EB82366BC7C58D39D2ED75BA075B84C175C5B3E13B307A3148E62047EDDE151F5EF2831BEF00D90EE299C56926FDF9E33C1BB75BF775D00E98F7D5CE8E9BE9FE12BCF0E7A6F343672E486EFA29A8521C486BB4BE3930AE7F3EFE564AF0A9D3AF041AC4B91420EF8ED6DBEEDECA03463D2BC66FB0E13283A49", Charset.forName("utf8"), "Z1drRB6A");
        System.out.println(a);

    }



    /**
     * 对给定的字符串以指定的编码方式和密钥进行加密
     * @param srcStr 待加密的字符串
     * @param charset 字符集，如utf8
     * @param sKey 密钥
     */
    public static String encrypt(String srcStr, Charset charset, String sKey) {
        byte[] src = srcStr.getBytes(charset);
        byte[] buf = encrypt(src, sKey);
        return parseByte2HexStr(buf);
    }

    /**
     * 对给定的密文以指定的编码方式和密钥进行解密
     * @param hexStr 需要解密的密文
     * @param charset 字符集
     * @param sKey 密钥
     * @return 解密后的原文
     * @throws Exception
     */
    public static String decrypt(String hexStr, Charset charset, String sKey) throws Exception {
        byte[] src = parseHexStr2Byte(hexStr);
        byte[] buf =decrypt(src, sKey);
        return new String(buf, charset);
    }

    public static byte[] encrypt(byte[] data, String sKey) {
        try {
            byte[] key = sKey.getBytes();

            IvParameterSpec iv = new IvParameterSpec(key);
            DESKeySpec desKey = new DESKeySpec(key);

            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);

            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

            cipher.init(Cipher.ENCRYPT_MODE, securekey, iv);

            return cipher.doFinal(data);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     * @param src
     * @param sKey
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] src, String sKey) throws Exception {
        byte[] key = sKey.getBytes();
        // 初始化向量
        IvParameterSpec iv = new IvParameterSpec(key);
        // 创建一个DESKeySpec对象
        DESKeySpec desKey = new DESKeySpec(key);
        // 创建一个密匙工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        // 将DESKeySpec对象转换成SecretKey对象
        SecretKey securekey = keyFactory.generateSecret(desKey);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, iv);
        // 真正开始解密操作
        return cipher.doFinal(src);
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    public static void aa(){
        String regular="function regular(args1,args2,args3){return args1}";
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
        try {
            engine.eval(regular);
            if (engine instanceof Invocable) {
                Invocable invoke = (Invocable) engine;
                String result = (String) invoke.invokeFunction(
                        "regular",
                        1,
                        1,
                        1);
                System.out.println(result);
            } else {
                System.out.println("error");
            }
        } catch (ScriptException | NoSuchMethodException e) {
            System.out.println(e.getMessage());
        }

}


    /**
     * 绘制字体头像
     * 如果是英文名，只显示首字母大写
     * 如果是中文名，只显示最后两个字
     * @param name
     * @throws IOException
     */
    public static String generateImg(String name)
            throws IOException {
        int width = 100;
        int height = 100;
        name = name.substring(0, 1);
        int nameLen = name.length();
        String nameWritten;
        // 如果用户输入的姓名少于等于2个字符，不用截取
        if (nameLen <= 2) {
            nameWritten = name;
        } else {
            // 如果用户输入的姓名大于等于3个字符，截取后面两位
            String first = name.substring(0, 1);
            if (isChinese(first)) {
                // 截取倒数两位汉字
                nameWritten = name.substring(nameLen - 2);
            } else {
                // 截取前面的两个英文字母
                nameWritten = name.substring(0, 2).toUpperCase();
            }
        }
        //Font font = new Font("微软雅黑", Font.PLAIN, 30);
        InputStream inputStream = ResourceUtil.getStreamSafe("weiruan.ttf");
//        InputStream inputStream = Resources.class.getResourceAsStream("/weiruan.ttf");
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            font = font.deriveFont(Font.PLAIN, 50);
            GraphicsEnvironment localGraphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            localGraphicsEnvironment.registerFont(font);
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = localGraphicsEnvironment.createGraphics(bi);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.setFont(font);

            g2.setBackground(getRandomColor());

            g2.clearRect(0, 0, width, height);

            g2.setPaint(Color.WHITE);
            // 一个字
            if(nameWritten.length() ==1) {
                // 中文
                if(isChinese(nameWritten)) {
                    g2.drawString(nameWritten, 25, 70);
                }// 英文
                else {
                    g2.drawString(nameWritten.toUpperCase(), 33, 67);
                }
            }
            BufferedImage rounded = makeRoundedCorner(bi, 99);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();//io流
            ImageIO.write(rounded, "png", baos);//写入流中
            byte[] bytes = baos.toByteArray();//转换成字节
            BASE64Encoder encoder = new BASE64Encoder();
            String png_base64 = encoder.encodeBuffer(bytes).trim();//转换成base64串
            png_base64 = png_base64.replaceAll("\n", "").replaceAll("\r", "");//删除 \r\n
            return "data:image/jpg;base64,"+png_base64;
        } catch (FontFormatException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 判断字符串是否为中文
     * @param str
     * @return
     */
    public static boolean isChinese(String str) {
        String regEx = "[\\u4e00-\\u9fa5]+";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        if (m.find())
            return true;
        else
            return false;
    }

    /**
     * 获得随机颜色
     * @return
     */
    private static Color getRandomColor() {
        String[] beautifulColors =
                new String[]{"232,221,203", "205,179,128", "3,101,100", "3,54,73", "3,22,52",
                        "237,222,139", "251,178,23", "96,143,159", "1,77,103", "254,67,101", "252,157,154",
                        "249,205,173", "200,200,169", "131,175,155", "229,187,129", "161,23,21", "34,8,7",
                        "118,77,57", "17,63,61", "60,79,57", "95,92,51", "179,214,110", "248,147,29",
                        "227,160,93", "178,190,126", "114,111,238", "56,13,49", "89,61,67", "250,218,141",
                        "3,38,58", "179,168,150", "222,125,44", "20,68,106", "130,57,53", "137,190,178",
                        "201,186,131", "222,211,140", "222,156,83", "23,44,60", "39,72,98", "153,80,84",
                        "217,104,49", "230,179,61", "174,221,129", "107,194,53", "6,128,67", "38,157,128",
                        "178,200,187", "69,137,148", "117,121,71", "114,83,52", "87,105,60", "82,75,46",
                        "171,92,37", "100,107,48", "98,65,24", "54,37,17", "137,157,192", "250,227,113",
                        "29,131,8", "220,87,18", "29,191,151", "35,235,185", "213,26,33", "160,191,124",
                        "101,147,74", "64,116,52", "255,150,128", "255,94,72", "38,188,213", "167,220,224",
                        "1,165,175", "179,214,110", "248,147,29", "230,155,3", "209,73,78", "62,188,202",
                        "224,160,158", "161,47,47", "0,90,171", "107,194,53", "174,221,129", "6,128,67",
                        "38,157,128", "201,138,131", "220,162,151", "137,157,192", "175,215,237", "92,167,186",
                        "255,66,93", "147,224,255", "247,68,97", "185,227,217"};
        int len = beautifulColors.length;
        Random random = new Random();
        String[] color = beautifulColors[random.nextInt(len)].split(",");
        return new Color(Integer.parseInt(color[0]), Integer.parseInt(color[1]),
                Integer.parseInt(color[2]));
    }


    /**
     * 图片做圆角处理
     * @param image
     * @param cornerRadius
     * @return
     */
    public static BufferedImage makeRoundedCorner(BufferedImage image, int cornerRadius){
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = output.createGraphics();
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius));
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        return output;
    }
}
