package chs.wechat.spy.padsdk.api_request;

import chs.wechat.spy.utils.ConfigProperties;
import chs.wechat.spy.utils.CustomHttpRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SnsRequest {
    private String baseHost = ConfigProperties.GetProperties("api_host");
    private final String SEND_TEXT = baseHost + "/sns/sendtext";//POST /api/sns/sendtext
    private final String SEND_IMG_TEXT = baseHost + "/sns/sendimgtext";//POST /api/sns/sendimgtext
    private final String SEND_LINK = baseHost + "/sns/sendlink";//POST /api/sns/sendlink
    private final String GET_TIMELINE = baseHost + "/sns/gettimeline";//POST /api/sns/gettimeline 获取朋友圈
    private final String GET_USERPAGE = baseHost + "/sns/getuserpage";//POST /api/sns/getuserpage 获取好友主页
    private final String GET_OBJECT_DETAIL = baseHost + "/sns/objectdetail";//POST /api/sns/objectdetail 获取朋友圈详情
    private final String GET_COMMENT = baseHost + "/sns/comment";//POST /api/sns/comment 朋友圈评论
    private final String OP_SNS = baseHost + "/sns/op";//POST /api/sns/op 点赞、删除
    private final String SYNC_SNS = baseHost + "/sns/sync";//POST /api/sns/sync 同步朋友圈评论、点赞

    /**
    * @param text:
      * @param uuid:
     * @return java.lang.String
     * @since 2019/5/13 17:39
    */
    public String SendText(String text, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("text", text);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(SEND_TEXT, params);
    }

    /**
    * @param base64list:
      * @param text:
      * @param uuid:
     * @return java.lang.String
     * @since 2019/5/13 17:39
    */
    public String SendImgText(List<String> base64list, String text, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("base64list", base64list);
        params.put("text", text);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(SEND_IMG_TEXT, params);
    }

    /**
    * @param title:
      * @param url:
      * @param text:
      * @param uuid:
     * @return java.lang.String
     * @since 2019/5/13 17:39
    */
    public String SendLink(String title, String url, String text, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("title", title);
        params.put("url", text);
        params.put("text", text);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(SEND_LINK, params);
    }

    /**
    * @param snsid:
      * @param uuid:
     * @return java.lang.String
     * @since 2019/5/13 17:39
    */
    public String GetTimeLine(String snsid, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("snsid", snsid);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(GET_TIMELINE, params);
    }

    /**
    * @param wxid:
      * @param snsid:
      * @param uuid:
     * @return java.lang.String
     * @since 2019/5/13 17:39
    */
    public String GetUserPage(String wxid, String snsid, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("wxid", wxid);
        params.put("snsid", snsid);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(GET_USERPAGE, params);
    }

    /**
    * @param snsid:
      * @param uuid:
     * @return java.lang.String
     * @since 2019/5/13 17:40
    */
    public String GetObjectDetail(String snsid, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("snsid", snsid);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(GET_OBJECT_DETAIL, params);
    }

    /**
    * @param context:
      * @param replyid:
      * @param snsid:
      * @param uuid:
     * @return java.lang.String
     * @since 2019/5/13 17:40
    */
    public String GetComment(String context, String replyid, String snsid, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("context", context);
        params.put("replyid", replyid);
        params.put("snsid", snsid);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(GET_COMMENT, params);
    }

    /**
    * @param id:
      * @param type:
      * @param comment:
      * @param comment_type:
      * @param uuid:
     * @return java.lang.String
     * @since 2019/5/13 17:40
    */
    public String SnsOp(String id, String type, String comment, String comment_type, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("type", type);
        params.put("comment", comment);
        params.put("comment_type", comment_type);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(OP_SNS, params);
    }

    /**
     * @param key:
     * @param uuid:
     * @return java.lang.String
     * @since 2019/5/13 17:40
    */
    public String SyncSns(String key, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("key", key);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(SYNC_SNS, params);
    }
}
