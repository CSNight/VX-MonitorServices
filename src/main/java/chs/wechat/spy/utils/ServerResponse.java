package chs.wechat.spy.utils;

public class ServerResponse {
    private int success = 0;
    private String response = "";
    private String type = "";
    private long timestamp = System.currentTimeMillis();

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int sucess) {
        this.success = sucess;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
