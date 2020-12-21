package cn.stylefeng.guns.sys.core.util;

/**
 * <p>数字转中文</p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2020/12/21
 * @since JDK 1.8
 */
public class NumToChUtil {
    public static String to(int d) {
        String[] str = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        String[] ss = new String[]{"", "十", "百", "千", "万", "十", "百", "千", "亿"};
        String s = String.valueOf(d);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            String index = String.valueOf(s.charAt(i));
            sb.append(str[Integer.parseInt(index)]);
        }
        String sss = String.valueOf(sb);
        int i = 0;
        for (int j = sss.length(); j > 0; j--) {
            sb.insert(j, ss[i++]);
        }
        return sb.toString();
    }
}
