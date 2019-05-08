package chs.wechat.spy.mybatis.model;

import java.util.Date;

public class Collections {
    private String id;

    private String user_id;

    private String col_id;

    private String seq;

    private Date times;

    private Integer type;

    private byte[] obj;

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

    public String getCol_id() {
        return col_id;
    }

    public void setCol_id(String col_id) {
        this.col_id = col_id == null ? null : col_id.trim();
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq == null ? null : seq.trim();
    }

    public Date getTimes() {
        return times;
    }

    public void setTimes(Date times) {
        this.times = times;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public byte[] getObj() {
        return obj;
    }

    public void setObj(byte[] obj) {
        this.obj = obj;
    }
}