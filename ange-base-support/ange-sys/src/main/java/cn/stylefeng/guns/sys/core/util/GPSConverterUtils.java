package cn.stylefeng.guns.sys.core.util;

import java.nio.charset.Charset;

public class GPSConverterUtils {
    public static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;

    public static void main(String[] args) throws Exception {
        GPS Bd09ToGcj02 = Bd09ToGcj02(39.919762,116.405689);
        System.out.println(Bd09ToGcj02);
    }

    public static GPS Bd09ToGcj02(double lat, double lon)
    {
        double x = lon - 0.0065, y = lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        double tempLon = z * Math.cos(theta);
        double tempLat = z * Math.sin(theta);
        double[] gps = { tempLat, tempLon };
        return new GPS(tempLat, tempLon);
    }

}
