package chs.wechat.spy.db.mybatis.model;

import java.sql.Timestamp;

public class Collections {
    private String id;

    private String userId;

    private String colId;

    private String seq;

    private Timestamp times;

    private Integer type;

    private byte[] obj;

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

    public String getColId() {
        return colId;
    }

    public void setColId(String colId) {
        this.colId = colId == null ? null : colId.trim();
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq == null ? null : seq.trim();
    }

    public Timestamp getTimes() {
        return times;
    }

    public void setTimes(Timestamp times) {
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