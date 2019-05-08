package chs.wechat.spy.mybatis.model;

public class ChatRooms {
    private String id;

    private String user_id;

    private String room_id;

    private String room_code;

    private String room_nick;

    private String room_owner;

    private Integer member_count;

    private Boolean is_owner;

    private String small_head_url;

    private byte[] small_head;

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

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id == null ? null : room_id.trim();
    }

    public String getRoom_code() {
        return room_code;
    }

    public void setRoom_code(String room_code) {
        this.room_code = room_code == null ? null : room_code.trim();
    }

    public String getRoom_nick() {
        return room_nick;
    }

    public void setRoom_nick(String room_nick) {
        this.room_nick = room_nick == null ? null : room_nick.trim();
    }

    public String getRoom_owner() {
        return room_owner;
    }

    public void setRoom_owner(String room_owner) {
        this.room_owner = room_owner == null ? null : room_owner.trim();
    }

    public Integer getMember_count() {
        return member_count;
    }

    public void setMember_count(Integer member_count) {
        this.member_count = member_count;
    }

    public Boolean getIs_owner() {
        return is_owner;
    }

    public void setIs_owner(Boolean is_owner) {
        this.is_owner = is_owner;
    }

    public String getSmall_head_url() {
        return small_head_url;
    }

    public void setSmall_head_url(String small_head_url) {
        this.small_head_url = small_head_url == null ? null : small_head_url.trim();
    }

    public byte[] getSmall_head() {
        return small_head;
    }

    public void setSmall_head(byte[] small_head) {
        this.small_head = small_head;
    }
}