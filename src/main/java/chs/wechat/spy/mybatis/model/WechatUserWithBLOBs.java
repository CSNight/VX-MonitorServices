package chs.wechat.spy.mybatis.model;

public class WechatUserWithBLOBs extends WechatUser {
    private byte[] headImage;

    private byte[] qrCode;

    public byte[] getHeadImage() {
        return headImage;
    }

    public void setHeadImage(byte[] headImage) {
        this.headImage = headImage;
    }

    public byte[] getQrCode() {
        return qrCode;
    }

    public void setQrCode(byte[] qrCode) {
        this.qrCode = qrCode;
    }
}