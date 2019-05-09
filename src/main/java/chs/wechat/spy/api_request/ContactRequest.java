package chs.wechat.spy.api_request;

import chs.wechat.spy.utils.ConfigProperties;

public class ContactRequest {
    private String baseHost = ConfigProperties.GetProperties("api_host");
    private final String GET_QRCODE = baseHost + "/login/loginscan";
    private final String LOGIN_62 = baseHost + "/login/login62";
    private final String GET_62 = baseHost + "/login/get62";
    private final String GET_62_HEX = baseHost + "/login/get62hex";
    private final String GET_TOKEN = baseHost + "/login/gettopken";
    private final String AGAIN_LOGIN = baseHost + "/login/againlogin";
    private final String RECONNECT = baseHost + "/login/reconnect";
}
