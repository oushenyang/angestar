package cn.stylefeng.guns.sys.core.util;

import java.util.Random;

/**
 * <p>生成卡密</p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2020/4/21
 * @since JDK 1.8
 */
public class CardStringRandom {

    /**
     * 生成卡密
     * @param cardTypePrefix 卡密前缀
     * @param cardTypeRule 卡密规则 0-大写字母+数字；1-小写字母+数字；2-全大写字母；3-全小写字母；4-全数字；
     * @param cardTypeLength 卡密长度 0-32位；1-16位；2-8位；
     * @return 卡密
     */
    public static String create(String cardTypePrefix,Integer cardTypeRule,Integer cardTypeLength){
        String card = "";
        switch (cardTypeRule){
            case 0:
                card = capitalWordAndNum(cardTypePrefix,cardTypeLength);
                break;
            case 1:
                card = wordAndNum(cardTypePrefix,cardTypeLength);
                break;
            case 2:
                card = capitalWord(cardTypePrefix,cardTypeLength);
                break;
            case 3:
                card = word(cardTypePrefix,cardTypeLength);
                break;
            case 4:
                card = num(cardTypePrefix,cardTypeLength);
                break;
        }
        return card;
    }
    //生成大写字母+数字,
    public static String capitalWordAndNum(String cardTypePrefix,Integer cardTypeLength) {
        StringBuilder val = new StringBuilder(cardTypePrefix);
        Random random = new Random();
        //参数length，表示生成几位随机数
        for(int i = 0; i < cardTypeLength; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if( "char".equalsIgnoreCase(charOrNum) ) {
                //输出是大写字母还是小写字母
//                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                int temp = 65;
                val.append((char) (random.nextInt(26) + temp));
            } else {
                val.append(random.nextInt(10));
            }
        }
        return val.toString();
    }

    //生成小写字母+数字,
    public static String wordAndNum(String cardTypePrefix,Integer cardTypeLength) {
        StringBuilder val = new StringBuilder(cardTypePrefix);
        Random random = new Random();
        //参数length，表示生成几位随机数
        for(int i = 0; i < cardTypeLength; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if( "char".equalsIgnoreCase(charOrNum) ) {
                int temp = 97;
                val.append((char) (random.nextInt(26) + temp));
            } else {
                val.append(random.nextInt(10));
            }
        }
        return val.toString();
    }

    /**
     * 生成大写字母
     */
    private static String capitalWord(String cardTypePrefix,Integer cardTypeLength){
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(cardTypePrefix);
        for ( int i = 0; i < cardTypeLength; i++ )
        {
            int number = random.nextInt( base.length() );
            sb.append( base.charAt( number ) );
        }
        return sb.toString();
    }

    /**
     * 生成小写字母
     */
    private static String word(String cardTypePrefix,Integer cardTypeLength){
        String base = "abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(cardTypePrefix);
        for ( int i = 0; i < cardTypeLength; i++ )
        {
            int number = random.nextInt( base.length() );
            sb.append( base.charAt( number ) );
        }
        return sb.toString();
    }

    /**
     * 生成数字
     */
    private static String num(String cardTypePrefix,Integer cardTypeLength){
        String base = "0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(cardTypePrefix);
        for ( int i = 0; i < cardTypeLength; i++ )
        {
            int number = random.nextInt( base.length() );
            sb.append( base.charAt( number ) );
        }
        return sb.toString();
    }
}
