package chs.wechat.spy.websdk.api.enums;


/**
 * 心跳检查状态码
 *
 * @author biezhi
 * @date 2018/1/21
 */
public enum RetCode {

    NORMAL(0, "正常"),
    LOGIN_OTHERWISE(1101, "其它地方登陆"),
    MOBILE_LOGIN_OUT(1102, "移动端退出"),
    UNKNOWN(9999, "未知");

    private int    code;
    private String type;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    RetCode(int code, String type) {
        this.code = code;
        this.type = type;
    }

    public static RetCode parse(int code) {
        switch (code) {
            case 0:
                return NORMAL;
            case 1102:
                return MOBILE_LOGIN_OUT;
            case 1101:
                return LOGIN_OTHERWISE;
            default:
                return UNKNOWN;
        }
    }

}
