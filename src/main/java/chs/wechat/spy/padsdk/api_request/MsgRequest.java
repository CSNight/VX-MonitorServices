package chs.wechat.spy.padsdk.api_request;

import chs.wechat.spy.utils.ConfigProperties;
import chs.wechat.spy.utils.CustomHttpRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MsgRequest {
    private String baseHost = ConfigProperties.GetProperties("api_host");
    private final String SEND_TEXT = baseHost + "/msg/sendtext";//POST /api/msg/sendtext
    private final String SEND_VOICE = baseHost + "/msg/sendvoice";//POST /api/msg/sendvoice
    private final String SEND_VICOE_SILK = baseHost + "/msg/sendvoicesilk";//POST /api/msg/sendvoicesilk
    private final String SEND_IMG = baseHost + "/msg/sendimg";//POST /api/msg/sendimg
    private final String SEND_APP = baseHost + "/msg/sendapp";//POST /api/msg/sendapp
    private final String SEND_CARD = baseHost + "/msg/sendshardcard";//POST /api/msg/sendshardcard
    private final String SEND_MASS = baseHost + "/msg/sendmass";//POST /api/msg/sendmass
    private final String GET_IMG = baseHost + "/msg/getimg";//POST /api/msg/getimg
    private final String GET_VOICE = baseHost + "/msg/getvoice";//POST /api/msg/getvoice
    private final String GET_VIDEO = baseHost + "/msg/getvideo";//POST /api/msg/getvideo
    private final String GET_REDPACK = baseHost + "/msg/getreadpack";//POST /api/msg/getreadpack
    private final String GET_TRANSFER = baseHost + "/msg/gettransfer";//POST /api/msg/gettransfer
    private final String INTO_GROUP = baseHost + "/msg/intogroup";//POST /api/msg/intogroup

    /**
     * @param wxid:
     * @param text:
     * @param atlist:
     * @param uuid:
     * @return java.lang.String
     * @since 2019/5/13 17:59
     */
    public String SendText(String wxid, String text, List<String> atlist, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("wxid", wxid);
        params.put("text", text);
        params.put("atlist", atlist);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(SEND_TEXT, params);
    }

    /**
     * @param wxid:
     * @param base64:
     * @param uuid:
     * @return java.lang.String
     * @since 2019/5/13 17:59
     */
    public String SendVoice(String wxid, String base64, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("wxid", wxid);
        params.put("base64", base64);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(SEND_VOICE, params);
    }

    /**
     * @param wxid:
     * @param base64:
     * @param uuid:
     * @return java.lang.String
     * @since 2019/5/13 17:59
     */
    public String SendSilk(String wxid, String base64, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("wxid", wxid);
        params.put("base64", base64);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(SEND_VICOE_SILK, params);
    }

    /**
     * @param wxid:
     * @param base64:
     * @param uuid:
     * @return java.lang.String
     * @since 2019/5/13 17:59
     */
    public String SendImg(String wxid, String base64, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("wxid", wxid);
        params.put("base64", base64);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(SEND_IMG, params);
    }

    /**
     * @param wxid:
     * @param appid:
     * @param sdkver:
     * @param title:
     * @param des:
     * @param url:
     * @param thumburl:
     * @param uuid:
     * @return java.lang.String
     * @since 2019/5/13 17:59
     */
    public String SendApp(String wxid, String appid, String sdkver, String title, String des, String url, String thumburl, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("wxid", wxid);
        params.put("appid", appid);
        params.put("sdkver", sdkver);
        params.put("title", title);
        params.put("des", des);
        params.put("url", url);
        params.put("thumburl", thumburl);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(SEND_APP, params);
    }

    /**
     * @param user:
     * @param title:
     * @param wxid:
     * @param uuid:
     * @return java.lang.String
     * @since 2019/5/13 17:59
     */
    public String SendCard(String user, String title, String wxid, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("user", user);
        params.put("wxid", wxid);
        params.put("title", title);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(SEND_CARD, params);
    }

    /**
     * @param wxids:
     * @param text:
     * @param uuid:
     * @return java.lang.String
     * @since 2019/5/13 17:59
     */
    public String SendMass(List<String> wxids, String text, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("wxids", wxids);
        params.put("text", text);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(SEND_MASS, params);
    }

    /**
     * @param msg:
     * @param uuid:
     * @return java.lang.String
     * @since 2019/5/13 17:59
     */
    public String GetImg(Map<String, Object> msg, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("msg", msg);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(GET_IMG, params);
    }

    /**
     * @param msg:
     * @param uuid:
     * @return java.lang.String
     * @since 2019/5/13 17:59
     */
    public String GetVoice(Map<String, Object> msg, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("msg", msg);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(GET_VOICE, params);
    }

    /**
     * @param msg:
     * @param uuid:
     * @return java.lang.String
     * @since 2019/5/13 17:59
     */
    public String GetVideo(Map<String, Object> msg, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("msg", msg);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(GET_VIDEO, params);
    }

    /**
     * @param msg:
     * @param uuid:
     * @return java.lang.String
     * @since 2019/5/13 17:59
     */
    public String GetRedPack(Map<String, Object> msg, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("msg", msg);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(GET_REDPACK, params);
    }

    /**
     * @param msg:
     * @param uuid:
     * @return java.lang.String
     * @since 2019/5/13 18:00
     */
    public String GetTransfer(Map<String, Object> msg, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("msg", msg);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(GET_TRANSFER, params);
    }

    /**
     * @param content:
     * @param uuid:
     * @return java.lang.String
     * @since 2019/5/13 18:00
     */
    public String IntoGroup(String content, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("content", content);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(INTO_GROUP, params);
    }
}
