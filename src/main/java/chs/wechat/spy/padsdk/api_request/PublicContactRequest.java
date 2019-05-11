package chs.wechat.spy.padsdk.api_request;

import chs.wechat.spy.utils.ConfigProperties;
import chs.wechat.spy.utils.CustomHttpRequest;

import java.util.HashMap;
import java.util.Map;

public class PublicContactRequest {
    private String baseHost = ConfigProperties.GetProperties("api_host");
    private final String FOLLOW = baseHost + "/gh/follow";//POST /api/gh/follow
    private final String SEARCH = baseHost + "/gh/search";//POST /api/gh/search
    private final String REQUEST_URL = baseHost + "/gh/requesturl";//POST /api/gh/requesturl
    private final String GET_TOKEN = baseHost + "/gh/getrquesttoken";//POST /api/gh/getrquesttoken

    /**
     * @param ghid:
     * @param uuid:
     * @return java.lang.String
     */
    public String FollowPublicCT(String ghid, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("ghid", ghid);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(FOLLOW, params);
    }

    /**
     * @param name:
     * @param uuid:
     * @return java.lang.String
     */
    public String SearchPublicCT(String name, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(SEARCH, params);
    }

    /**
     * @param url:
     * @param uin:
     * @param key:
     * @param ghid:
     * @param uuid:
     * @return java.lang.String
     */
    public String WatchPublicLink(String url, String uin, String key, String ghid, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("url", url);
        params.put("uin", uin);
        params.put("key", key);
        params.put("ghid", ghid);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(REQUEST_URL, params);
    }

    /**
     * @param url:
     * @param ghid:
     * @param uuid:
     * @return java.lang.String
     */
    public String GetPublicToken(String url, String ghid, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("url", url);
        params.put("ghid", ghid);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(GET_TOKEN, params);
    }
}
