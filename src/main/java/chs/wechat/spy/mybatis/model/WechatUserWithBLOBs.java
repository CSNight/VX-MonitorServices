package chs.wechat.spy.mybatis.model;

public class WechatUserWithBLOBs extends WechatUser {
    private byte[] head_image;

    private byte[] qr_code;

    public byte[] getHead_image() {
        return head_image;
    }

    public void setHead_image(byte[] head_image) {
        this.head_image = head_image;
    }

    public byte[] getQr_code() {
        return qr_code;
    }

    public void setQr_code(byte[] qr_code) {
        this.qr_code = qr_code;
    }
}