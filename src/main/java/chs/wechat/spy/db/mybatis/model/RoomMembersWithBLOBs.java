package chs.wechat.spy.db.mybatis.model;

public class RoomMembersWithBLOBs extends RoomMembers {
    private byte[] smallHead;

    private byte[] bigHead;

    public byte[] getSmallHead() {
        return smallHead;
    }

    public void setSmallHead(byte[] smallHead) {
        this.smallHead = smallHead;
    }

    public byte[] getBigHead() {
        return bigHead;
    }

    public void setBigHead(byte[] bigHead) {
        this.bigHead = bigHead;
    }
}