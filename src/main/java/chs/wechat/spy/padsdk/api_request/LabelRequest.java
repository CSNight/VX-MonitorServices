package chs.wechat.spy.padsdk.api_request;

import chs.wechat.spy.utils.ConfigProperties;
import chs.wechat.spy.utils.CustomHttpRequest;

import java.util.HashMap;
import java.util.Map;

public class LabelRequest {
    private String baseHost = ConfigProperties.GetProperties("api_host");
    private final String GET_LABEL = baseHost + "/label/get";//POST /api/label/get
    private final String SET_LABEL = baseHost + "/label/set";//POST /api/label/set
    private final String ADD_LABEL = baseHost + "/label/add";//POST /api/label/add
    private final String DELETE_LABEL = baseHost + "/label/delete";//POST /api/label/delete

    /**
     * @param uuid:
     * @return java.lang.String
     * @since 2019/5/13 16:40
     */
    public String GetLabel(String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(GET_LABEL, params);
    }

    /**
     * @param wxid:
     * @param labelid:
     * @param uuid:
     * @return java.lang.String
     * @since 2019/5/13 16:40
     */
    public String SetLabel(String wxid, String labelid, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("wxid", wxid);
        params.put("labelid", labelid);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(SET_LABEL, params);
    }

    /**
     * @param name:
     * @param uuid:
     * @return java.lang.String
     * @since 2019/5/13 16:40
     */
    public String AddLabel(String name, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(ADD_LABEL, params);
    }

    /**
     * @param labelid:
     * @param uuid:
     * @return java.lang.String
     * @since 2019/5/13 16:40
     */
    public String DeleteLabel(String labelid, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("labelid", labelid);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(DELETE_LABEL, params);
    }
}
