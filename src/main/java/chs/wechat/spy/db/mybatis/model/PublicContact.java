package chs.wechat.spy.db.mybatis.model;

public class PublicContact {
    private String id;

    private String userId;

    private String pctId;

    private String pctName;

    private String loc;

    private String sign;

    private String stranger;

    private Integer addSource;

    private String smallHeadUrl;

    private String bigHeadUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getPctId() {
        return pctId;
    }

    public void setPctId(String pctId) {
        this.pctId = pctId == null ? null : pctId.trim();
    }

    public String getPctName() {
        return pctName;
    }

    public void setPctName(String pctName) {
        this.pctName = pctName == null ? null : pctName.trim();
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

    public Integer getAddSource() {
        return addSource;
    }

    public void setAddSource(Integer addSource) {
        this.addSource = addSource;
    }

    public String getSmallHeadUrl() {
        return smallHeadUrl;
    }

    public void setSmallHeadUrl(String smallHeadUrl) {
        this.smallHeadUrl = smallHeadUrl == null ? null : smallHeadUrl.trim();
    }

    public String getBigHeadUrl() {
        return bigHeadUrl;
    }

    public void setBigHeadUrl(String bigHeadUrl) {
        this.bigHeadUrl = bigHeadUrl == null ? null : bigHeadUrl.trim();
    }
}