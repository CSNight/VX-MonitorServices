package chs.wechat.spy.api_request;

import chs.wechat.spy.utils.ConfigProperties;
import chs.wechat.spy.utils.CustomHttpRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest {
    private String baseHost = ConfigProperties.GetProperties("api_host");
    private final String GET_QRCODE = baseHost + "/login/loginscan";
    private final String LOGIN_62 = baseHost + "/login/login62";
    private final String GET_62 = baseHost + "/login/get62";
    private final String GET_62_HEX = baseHost + "/login/get62hex";
    private final String GET_TOKEN = baseHost + "/login/gettopken";
    private final String AGAIN_LOGIN = baseHost + "/login/againlogin";
    private final String RECONNECT = baseHost + "/login/reconnect";

    public String GetQRCode(String uuid, String device_name) {
        Map<String, Object> params = new HashMap<>();
        params.put("devicename", device_name);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(GET_QRCODE, params);
    }

    public String Login_62(String username, String pwd, String str62, String uuid, String device_name) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        params.put("password", pwd);
        params.put("str62", str62);
        params.put("devicename", device_name);
        params.put("isreset", true);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(LOGIN_62, params);
    }

    public String Get62(String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(GET_62, params);
    }

    public String Get62Hex(String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(GET_62_HEX, params);
    }

    public String GetToken(String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(GET_TOKEN, params);
    }

    public String AgainLogin(String str62, String token, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("str62", str62);
        params.put("token", token);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(AGAIN_LOGIN, params);
    }

    public String GetToken(String str62, String token, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("str62", str62);
        params.put("token", token);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(RECONNECT, params);
    }
}
