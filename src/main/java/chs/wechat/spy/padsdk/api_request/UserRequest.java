package chs.wechat.spy.padsdk.api_request;

import chs.wechat.spy.utils.ConfigProperties;
import chs.wechat.spy.utils.CustomHttpRequest;

import java.util.HashMap;
import java.util.Map;

public class UserRequest {
    private String baseHost = ConfigProperties.GetProperties("api_host");
    private final String SET_IMAGE_HEAD = baseHost + "/user/setheadimg";//POST /api/user/setheadimg
    private final String SET_WXID = baseHost + "/user/setwxid";//POST /api/user/setwxid
    private final String SET_USERINFO = baseHost + "/user/setuserinfo";//POST /api/user/setuserinfo
    private final String BIND_EMAIL = baseHost + "/user/bindemail";//POST /api/user/bindemail
    private final String BIND_QQ = baseHost + "/user/bindqq";//POST /api/user/bindqq
    private final String GET_USERINFO = baseHost + "/user/get";//POST /api/user/get


    public String SetImageHead(String base64, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("base64", base64);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(SET_IMAGE_HEAD, params);
    }

    public String SetWxid(String wxid, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("wxid", wxid);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(SET_WXID, params);
    }

    public String SetUserInfo(String nickname, String sign, int sex, String country, String provincia, String city, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("nickname", nickname);
        params.put("sign", sign);
        params.put("sex", sex);
        params.put("country", country);
        params.put("provincia", provincia);
        params.put("city", city);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(SET_USERINFO, params);
    }

    public String BindEmail(String email, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(BIND_EMAIL, params);
    }

    public String BindQQ(String qq, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("qq", qq);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(BIND_QQ, params);
    }

    public String GetUserInfo(String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(GET_USERINFO, params);
    }


}
