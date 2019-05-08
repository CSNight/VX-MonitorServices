package chs.wechat.spy.mybatis.model;

public class PublicContract {
    private String id;

    private String user_id;

    private String pct_id;

    private String pct_name;

    private String loc;

    private String sign;

    private String stranger;

    private Integer add_source;

    private String small_head_url;

    private String big_head_url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id == null ? null : user_id.trim();
    }

    public String getPct_id() {
        return pct_id;
    }

    public void setPct_id(String pct_id) {
        this.pct_id = pct_id == null ? null : pct_id.trim();
    }

    public String getPct_name() {
        return pct_name;
    }

    public void setPct_name(String pct_name) {
        this.pct_name = pct_name == null ? null : pct_name.trim();
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc == null ? null : loc.trim();
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign == null ? null : sign.trim();
    }

    public String getStranger() {
        return stranger;
    }

    public void setStranger(String stranger) {
        this.stranger = stranger == null ? null : stranger.trim();
    }

    public Integer getAdd_source() {
        return add_source;
    }

    public void setAdd_source(Integer add_source) {
        this.add_source = add_source;
    }

    public String getSmall_head_url() {
        return small_head_url;
    }

    public void setSmall_head_url(String small_head_url) {
        this.small_head_url = small_head_url == null ? null : small_head_url.trim();
    }

    public String getBig_head_url() {
        return big_head_url;
    }

    public void setBig_head_url(String big_head_url) {
        this.big_head_url = big_head_url == null ? null : big_head_url.trim();
    }
}