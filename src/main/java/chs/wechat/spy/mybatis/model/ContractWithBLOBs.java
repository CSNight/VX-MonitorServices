package chs.wechat.spy.mybatis.model;

public class ContractWithBLOBs extends Contract {
    private byte[] small_head;

    private byte[] big_head;

    public byte[] getSmall_head() {
        return small_head;
    }

    public void setSmall_head(byte[] small_head) {
        this.small_head = small_head;
    }

    public byte[] getBig_head() {
        return big_head;
    }

    public void setBig_head(byte[] big_head) {
        this.big_head = big_head;
    }
}