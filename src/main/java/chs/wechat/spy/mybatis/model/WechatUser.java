package chs.wechat.spy.mybatis.model;

public class WechatUser {
    private String id;

    private String user_id;

    private String user_name;

    private String pwd;

    private String str62;

    private String user_sign;

    private String loc;

    private Integer sex;

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

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name == null ? null : user_name.trim();
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd == null ? null : pwd.trim();
    }

    public String getStr62() {
        return str62;
    }

    public void setStr62(String str62) {
        this.str62 = str62 == null ? null : str62.trim();
    }

    public String getUser_sign() {
        return user_sign;
    }

    public void setUser_sign(String user_sign) {
        this.user_sign = user_sign == null ? null : user_sign.trim();
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
}