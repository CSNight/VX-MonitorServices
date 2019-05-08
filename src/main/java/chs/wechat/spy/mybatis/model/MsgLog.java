package chs.wechat.spy.mybatis.model;

import java.util.Date;

public class MsgLog {
    private String id;

    private String user_id;

    private String msg_id;

    private Date msg_time;

    private Integer msg_type;

    private Integer msg_subtype;

    private String from_user;

    private String to_user;

    private String uin;

    private Integer msg_status;

    private String content_id;

    private byte[] msg_describe;

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

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id == null ? null : msg_id.trim();
    }

    public Date getMsg_time() {
        return msg_time;
    }

    public void setMsg_time(Date msg_time) {
        this.msg_time = msg_time;
    }

    public Integer getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(Integer msg_type) {
        this.msg_type = msg_type;
    }

    public Integer getMsg_subtype() {
        return msg_subtype;
    }

    public void setMsg_subtype(Integer msg_subtype) {
        this.msg_subtype = msg_subtype;
    }

    public String getFrom_user() {
        return from_user;
    }

    public void setFrom_user(String from_user) {
        this.from_user = from_user == null ? null : from_user.trim();
    }

    public String getTo_user() {
        return to_user;
    }

    public void setTo_user(String to_user) {
        this.to_user = to_user == null ? null : to_user.trim();
    }

    public String getUin() {
        return uin;
    }

    public void setUin(String uin) {
        this.uin = uin == null ? null : uin.trim();
    }

    public Integer getMsg_status() {
        return msg_status;
    }

    public void setMsg_status(Integer msg_status) {
        this.msg_status = msg_status;
    }

    public String getContent_id() {
        return content_id;
    }

    public void setContent_id(String content_id) {
        this.content_id = content_id == null ? null : content_id.trim();
    }

    public byte[] getMsg_describe() {
        return msg_describe;
    }

    public void setMsg_describe(byte[] msg_describe) {
        this.msg_describe = msg_describe;
    }
}