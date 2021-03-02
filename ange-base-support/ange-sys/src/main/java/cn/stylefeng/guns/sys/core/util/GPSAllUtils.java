package cn.stylefeng.guns.sys.core.util;

public class GPSAllUtils {
    private static double a = 3.141592653589793d;
    private static double b = 6378245.0d;
    private static double c = 0.006693421622965943d;
    private static double d = ((a * 3000.0d) / 180.0d);


    public static boolean i(double d2, double d3) {
        return d3 < 72.004d || d3 > 137.8347d || d2 < 0.8293d || d2 > 55.8271d;
    }

    public static double[] e(double d2, double d3) {
        double[] dArr = new double[2];
        if (i(d2, d3)) {
            dArr[0] = d2;
            dArr[1] = d3;
            return dArr;
        }
        double[] h = h(d2, d3);
        dArr[0] = d2 + h[0];
        dArr[1] = d3 + h[1];
        return dArr;
    }

    public static double[] h(double d2, double d3) {
        double d4 = d3 - 105.0d;
        double d5 = d2 - 35.0d;
        double j = j(d4, d5);
        double k = k(d4, d5);
        double d6 = (d2 / 180.0d) * a;
        double sin = Math.sin(d6);
        double d7 = 1.0d - ((c * sin) * sin);
        double sqrt = Math.sqrt(d7);
        return new double[]{(j * 180.0d) / (((b * (1.0d - c)) / (d7 * sqrt)) * a), (k * 180.0d) / (((b / sqrt) * Math.cos(d6)) * a)};
    }

    public static double j(double d2, double d3) {
        double d4 = d2 * 2.0d;
        return -100.0d + d4 + (d3 * 3.0d) + (d3 * 0.2d * d3) + (0.1d * d2 * d3) + (Math.sqrt(Math.abs(d2)) * 0.2d) + ((((Math.sin((d2 * 6.0d) * a) * 20.0d) + (Math.sin(d4 * a) * 20.0d)) * 2.0d) / 3.0d) + ((((Math.sin(a * d3) * 20.0d) + (Math.sin((d3 / 3.0d) * a) * 40.0d)) * 2.0d) / 3.0d) + ((((Math.sin((d3 / 12.0d) * a) * 160.0d) + (Math.sin((d3 * a) / 30.0d) * 320.0d)) * 2.0d) / 3.0d);
    }

    public static double k(double d2, double d3) {
        double d4 = d2 * 0.1d;
        return d2 + 300.0d + (d3 * 2.0d) + (d4 * d2) + (d4 * d3) + (Math.sqrt(Math.abs(d2)) * 0.1d) + ((((Math.sin((6.0d * d2) * a) * 20.0d) + (Math.sin((d2 * 2.0d) * a) * 20.0d)) * 2.0d) / 3.0d) + ((((Math.sin(a * d2) * 20.0d) + (Math.sin((d2 / 3.0d) * a) * 40.0d)) * 2.0d) / 3.0d) + ((((Math.sin((d2 / 12.0d) * a) * 150.0d) + (Math.sin((d2 / 30.0d) * a) * 300.0d)) * 2.0d) / 3.0d);
    }
    public static GPS g(double d2, double d3) {
        double d4;
        double d5;
        double d6 = d2 - 0.01d;
        double d7 = d3 - 0.01d;
        double d8 = d2 + 0.01d;
        double d9 = d3 + 0.01d;
        double d10 = 0.0d;
        while (true) {
            d4 = (d6 + d8) / 2.0d;
            double d11 = d9;
            d5 = (d7 + d9) / 2.0d;
            double[] e = e(d4, d5);
            double d12 = e[0] - d2;
            double d13 = e[1] - d3;
            if (Math.abs(d12) < 1.0E-9d && Math.abs(d13) < 1.0E-9d) {
                break;
            }
            if (d12 > 0.0d) {
                d8 = d4;
            } else {
                d6 = d4;
            }
            if (d13 > 0.0d) {
                d11 = d5;
            } else {
                d7 = d5;
            }
            d10 += 1.0d;
            if (d10 > 10000.0d) {
                break;
            }
            d9 = d11;
        }
        return new GPS(d4, d5);
    }

    public static void main(String[] args) {
        GPS gps = g(39.909187,116.397451);
        System.out.println(gps);

    }

}
