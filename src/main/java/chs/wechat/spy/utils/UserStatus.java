package chs.wechat.spy.utils;

public class UserStatus {
    private String id = "";
    private String web_uid = "";
    private String start_time = "";
    private String current_opt = "";
    private String init_status = "false";
    private String login_status = "logout";
    private String login_time = "";
    private String logout_time = "";
    private String cookies = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getInit_status() {
        return init_status;
    }

    public void setInit_status(String init_status) {
        this.init_status = init_status;
    }

    public String getLogin_status() {
        return login_status;
    }

    public void setLogin_status(String login_status) {
        this.login_status = login_status;
    }

    public String getLogin_time() {
        return login_time;
    }

    public void setLogin_time(String login_time) {
        this.login_time = login_time;
    }

    public String getLogout_time() {
        return logout_time;
    }

    public void setLogout_time(String logout_time) {
        this.logout_time = logout_time;
    }

    public String getCurrent_opt() {
        return current_opt;
    }

    public void setCurrent_opt(String current_opt) {
        this.current_opt = current_opt;
    }

    public String getWeb_uid() {
        return web_uid;
    }

    public void setWeb_uid(String web_uid) {
        this.web_uid = web_uid;
    }

    public String getCookies() {
        return cookies;
    }

    public void setCookies(String cookies) {
        this.cookies = cookies;
    }
}
