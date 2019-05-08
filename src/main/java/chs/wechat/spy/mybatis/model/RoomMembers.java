package chs.wechat.spy.mybatis.model;

public class RoomMembers {
    private String id;

    private String user_id;

    private String room_id;

    private String member_id;

    private String member_name;

    private String member_nick;

    private String invited_by;

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

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id == null ? null : room_id.trim();
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id == null ? null : member_id.trim();
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name == null ? null : member_name.trim();
    }

    public String getMember_nick() {
        return member_nick;
    }

    public void setMember_nick(String member_nick) {
        this.member_nick = member_nick == null ? null : member_nick.trim();
    }

    public String getInvited_by() {
        return invited_by;
    }

    public void setInvited_by(String invited_by) {
        this.invited_by = invited_by == null ? null : invited_by.trim();
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