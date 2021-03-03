package cn.stylefeng.guns.sys.core.util;

import java.math.BigDecimal;

public class GPSNewUtils {
    private static double a = 3.141592653589793d;
    private static double b = 6378245.0d;
    private static double c = 0.006693421622965943d;
    private static double d = ((a * 3000.0d) / 180.0d);

    public static boolean i(double d2, double d3) {
        return d3 < 72.004d || d3 > 137.8347d || d2 < 0.8293d || d2 > 55.8271d;
    }

    static {
    }

    public static double[] a(double d2, double d3) {
        double sqrt = Math.sqrt((d3 * d3) + (d2 * d2)) + (Math.sin(d * d2) * 2.0E-5d);
        double atan2 = Math.atan2(d2, d3) + (Math.cos(d3 * d) * 3.0E-6d);
        return new double[]{(Math.sin(atan2) * sqrt) + 0.006d, (sqrt * Math.cos(atan2)) + 0.0065d};
    }

    public static double[] b(double d2, double d3) {
        double d4 = d3 - 0.0065d;
        double d5 = d2 - 0.006d;
        double sqrt = Math.sqrt((d4 * d4) + (d5 * d5)) - (Math.sin(d * d5) * 2.0E-5d);
        double atan2 = Math.atan2(d5, d4) - (Math.cos(d4 * d) * 3.0E-6d);
        return new double[]{Math.sin(atan2) * sqrt, sqrt * Math.cos(atan2)};
    }

    public static double[] c(double d2, double d3) {
        double[] b2 = b(d2, d3);
        return f(b2[0], b2[1]);
    }

    public static double[] d(double d2, double d3) {
        double[] e = e(d2, d3);
         return  a(e[0], e[1]);
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

    public static double[] f(double d2, double d3) {
        double[] dArr = new double[2];
        if (i(d2, d3)) {
            dArr[0] = d2;
            dArr[1] = d3;
            return dArr;
        }
        double[] h = h(d2, d3);
        dArr[0] = d2 - h[0];
        dArr[1] = d3 - h[1];
        return dArr;
    }

    public static double[] g(double d2, double d3) {
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
        return new double[]{d4, d5};
    }

    public static double a(double d2, double d3, double d4, double d5) {
        double d6 = (d2 * 3.141592653589793d) / 180.0d;
        double d7 = (d4 * 3.141592653589793d) / 180.0d;
        double cos = (Math.cos(d6) * Math.cos(d7) * Math.cos(((d3 - d5) * 3.141592653589793d) / 180.0d)) + (Math.sin(d6) * Math.sin(d7));
        double d8 = 1.0d;
        if (cos <= 1.0d) {
            d8 = cos;
        }
        if (d8 < -1.0d) {
            d8 = -1.0d;
        }
        double acos = Math.acos(d8);
        double d9 = (double) 6371000;
        Double.isNaN(d9);
        return acos * d9;
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

    public static double a(double d2, double d3, double d4) {
        if (d2 < 0.0d) {
            return -(Math.abs(d2) + ((Math.abs(d3) + (Math.abs(d4) / 60.0d)) / 60.0d));
        }
        return Math.abs(d2) + ((Math.abs(d3) + (Math.abs(d4) / 60.0d)) / 60.0d);
    }

    public static double[] a(String str) {
        double parseDouble = Double.parseDouble(str.substring(0, str.indexOf("°")));
        double parseDouble2 = Double.parseDouble(str.substring(str.indexOf("°") + 1, str.indexOf("′")));
        double parseDouble3 = Double.parseDouble(str.substring(str.indexOf("′") + 1, str.indexOf("″")));
        if (parseDouble < 0.0d) {
            return new double[]{-Math.abs(parseDouble), parseDouble2, parseDouble3};
        }
        return new double[]{parseDouble, parseDouble2, parseDouble3};
    }

    public static double[] a(double d2) {
        int floor = (int) Math.floor(Math.abs(d2));
        double b2 = b(Math.abs(d2)) * 60.0d;
        int floor2 = (int) Math.floor(b2);
        double b3 = b(b2) * 60.0d;
        if (floor < 0) {
            return new double[]{(double) (-Math.abs(floor)), (double) floor2, b3};
        }
        return new double[]{(double) floor, (double) floor2, b3};
    }

    private static double b(double d2) {
        return (double) new BigDecimal(Double.toString(d2)).subtract(new BigDecimal(Integer.toString((int) d2))).floatValue();
    }
    public static void main(String[] args) {
        double[] a = a(39.909187,116.397451);
        double[] b = b(39.909187,116.397451);
        double[] c = c(39.909187,116.397451);
        double[] d = d(39.909187,116.397451);
        double[] e = e(39.909187,116.397451);
        double[] f = f(39.909187,116.397451);
        double[] g = g(39.909187,116.397451);
        double[] h = h(39.909187,116.397451);
        System.out.println(a[0]+","+a[1]);
        System.out.println(b[0]+","+b[1]);
//        System.out.println(c[0]+","+c[1]);
//        System.out.println("f:"+f.getLatitude()+","+f.getLongitude());
//        System.out.println(d[0]+","+d[1]);
        System.out.println(e[0]+","+e[1]);
//        System.out.println("f"+f[0]+","+f[1]);
        System.out.println(g[0]+","+g[1]);
        System.out.println(g[0]+","+g[1]);

    }

}
