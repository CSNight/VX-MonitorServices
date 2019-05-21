package chs.wechat.spy.padsdk.api_request;

import chs.wechat.spy.utils.ConfigProperties;
import chs.wechat.spy.utils.CustomHttpRequest;

import java.util.HashMap;
import java.util.Map;

public class UtilsRequest {
    private String baseHost = ConfigProperties.GetProperties("api_host");
    private final String AMR_TO_MP3 = baseHost + "/utils/amr2mp3";//POST /api/utils/amr2mp3

    public String AmrToMp3(String arm) {
        Map<String, Object> params = new HashMap<>();
        params.put("arm", arm);
        return CustomHttpRequest.jsonPost(AMR_TO_MP3, params);
    }
}
