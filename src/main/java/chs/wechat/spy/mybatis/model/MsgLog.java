package chs.wechat.spy.mybatis.model;

import java.util.Date;

public class MsgLog {
    private String id;

    private String userId;

    private String msgId;

    private Date msgTime;

    private Integer msgType;

    private Integer msgSubtype;

    private String fromUser;

    private String toUser;

    private String uin;

    private Integer msgStatus;

    private String contentId;

    private byte[] msgDescribe;

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

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId == null ? null : msgId.trim();
    }

    public Date getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(Date msgTime) {
        this.msgTime = msgTime;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public Integer getMsgSubtype() {
        return msgSubtype;
    }

    public void setMsgSubtype(Integer msgSubtype) {
        this.msgSubtype = msgSubtype;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser == null ? null : fromUser.trim();
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser == null ? null : toUser.trim();
    }

    public String getUin() {
        return uin;
    }

    public void setUin(String uin) {
        this.uin = uin == null ? null : uin.trim();
    }

    public Integer getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(Integer msgStatus) {
        this.msgStatus = msgStatus;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId == null ? null : contentId.trim();
    }

    public byte[] getMsgDescribe() {
        return msgDescribe;
    }

    public void setMsgDescribe(byte[] msgDescribe) {
        this.msgDescribe = msgDescribe;
    }
}