package chs.wechat.spy.mybatis.model;

public class Contact {
    private String id;

    private String user_id;

    private String contract_id;

    private String contract_name;

    private String remark;

    private String loc;

    private Integer sex;

    private String stranger;

    private String signature;

    private String add_source;

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

    public String getContract_id() {
        return contract_id;
    }

    public void setContract_id(String contract_id) {
        this.contract_id = contract_id == null ? null : contract_id.trim();
    }

    public String getContract_name() {
        return contract_name;
    }

    public void setContract_name(String contract_name) {
        this.contract_name = contract_name == null ? null : contract_name.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc == null ? null : loc.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getStranger() {
        return stranger;
    }

    public void setStranger(String stranger) {
        this.stranger = stranger == null ? null : stranger.trim();
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature == null ? null : signature.trim();
    }

    public String getAdd_source() {
        return add_source;
    }

    public void setAdd_source(String add_source) {
        this.add_source = add_source == null ? null : add_source.trim();
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