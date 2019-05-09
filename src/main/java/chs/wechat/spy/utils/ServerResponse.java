package chs.wechat.spy.utils;

public class ServerResponse {
    private int sucess = 0;
    private String response = "";
    private String type = "";

    public int getSucess() {
        return sucess;
    }

    public void setSucess(int sucess) {
        this.sucess = sucess;
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
