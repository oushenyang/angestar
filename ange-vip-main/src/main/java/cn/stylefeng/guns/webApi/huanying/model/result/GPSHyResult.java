package cn.stylefeng.guns.webApi.huanying.model.result;

public class GPSHyResult {
    private  double lat;

    private double lon;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public GPSHyResult(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "GPSHyResult{" +
                "lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}
