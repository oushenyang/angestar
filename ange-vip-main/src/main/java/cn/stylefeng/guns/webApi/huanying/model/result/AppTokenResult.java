package cn.stylefeng.guns.webApi.huanying.model.result;

public class AppTokenResult {
    private  String appkey;

    private Integer id;

    private String appid;

    private Long timestamp;

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public AppTokenResult(String appkey, Integer id, String appid, Long timestamp) {
        this.appkey = appkey;
        this.id = id;
        this.appid = appid;
        this.timestamp = timestamp;
    }
}
