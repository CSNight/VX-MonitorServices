package chs.wechat.spy.db.mybatis.model;

public class ChatRooms {
    private String id;

    private String userId;

    private String roomId;

    private String roomCode;

    private String roomNick;

    private String roomOwner;

    private Integer memberCount;

    private Boolean isOwner;

    private String smallHeadUrl;

    private byte[] smallHead;

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

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId == null ? null : roomId.trim();
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode == null ? null : roomCode.trim();
    }

    public String getRoomNick() {
        return roomNick;
    }

    public void setRoomNick(String roomNick) {
        this.roomNick = roomNick == null ? null : roomNick.trim();
    }

    public String getRoomOwner() {
        return roomOwner;
    }

    public void setRoomOwner(String roomOwner) {
        this.roomOwner = roomOwner == null ? null : roomOwner.trim();
    }

    public Integer getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }

    public Boolean getIsOwner() {
        return isOwner;
    }

    public void setIsOwner(Boolean isOwner) {
        this.isOwner = isOwner;
    }

    public String getSmallHeadUrl() {
        return smallHeadUrl;
    }

    public void setSmallHeadUrl(String smallHeadUrl) {
        this.smallHeadUrl = smallHeadUrl == null ? null : smallHeadUrl.trim();
    }

    public byte[] getSmallHead() {
        return smallHead;
    }

    public void setSmallHead(byte[] smallHead) {
        this.smallHead = smallHead;
    }
}