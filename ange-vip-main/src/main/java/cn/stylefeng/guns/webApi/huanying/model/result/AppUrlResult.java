package cn.stylefeng.guns.webApi.huanying.model.result;

public class AppUrlResult {
    private  String pkgid;

    private String url;

    private Integer id;

    public String getPkgid() {
        return pkgid;
    }

    public void setPkgid(String pkgid) {
        this.pkgid = pkgid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AppUrlResult(String pkgid, String url, Integer id) {
        this.pkgid = pkgid;
        this.url = url;
        this.id = id;
    }
}
