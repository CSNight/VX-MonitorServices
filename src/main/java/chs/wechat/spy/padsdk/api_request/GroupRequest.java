package chs.wechat.spy.padsdk.api_request;

import chs.wechat.spy.utils.ConfigProperties;
import chs.wechat.spy.utils.CustomHttpRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupRequest {
    private String baseHost = ConfigProperties.GetProperties("api_host");
    private final String CREATE_GROUP = baseHost + "/group/creat";//POST /api/group/creat 加群
    private final String QUIT_GROUP = baseHost + "/group/quit";//POST /api/group/quick 退群
    private final String GET_INFO = baseHost + "/group/getinfo";//POST /api/group/getinfo 获取群信息
    private final String GET_MEMBER = baseHost + "/group/getmember";//POST /api/group/getmember 获取群成员
    private final String ADD_MEMBER = baseHost + "/group/addmember";//POST /api/group/addmember 添加群成员
    private final String INVITE_MEMBER = baseHost + "/group/invitemember";//POST /api/group/invitemember 邀请群成员
    private final String DEL_MEMBER = baseHost + "/group/delmember";//POST /api/group/delmember 删除群成员
    private final String UPDATE_GROUP_NAME = baseHost + "/group/updatename";//POST /api/group/updatename 修改群名称
    private final String UPDATE_NICK = baseHost + "/group/updatenickname";//POST /api/group/updatenickname 修改群昵称
    private final String UPDATE_ANNOUNCE = baseHost + "/group/updateannouncement";//POST /api/group/updateannouncement 修改群公告
    private final String GET_QRCODE = baseHost + "/group/getqrcode";//POST /api/group/getqrcode 获取群二维码
    private final String SCAN_IN = baseHost + "/group/scanintogroup";//POST /api/group/scanintogroup 扫码加群

    /**
     * @param users:
     * @param uuid:
     * @return java.lang.String
     * @since 2019/5/13 16:01
     */
    public String CreateGroup(List<String> users, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("users", users);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(CREATE_GROUP, params);
    }

    /**
     * @param chatroomid:
     * @param uuid:
     * @return java.lang.String
     * @since 2019/5/13 16:07
    */
    public String QUIT(String chatroomid, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("chatroomid", chatroomid);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(QUIT_GROUP, params);
    }

    /**
    * @param chatroomid:
      * @param uuid:
     * @return java.lang.String
     * @since 2019/5/13 16:07
    */
    public String GetInfo(String chatroomid, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("chatroomid", chatroomid);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(GET_INFO, params);
    }

    /**
    * @param chatroomid:
      * @param uuid:
     * @return java.lang.String
     * @since 2019/5/13 16:08
    */
    public String GetMembers(String chatroomid, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("chatroomid", chatroomid);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(GET_MEMBER, params);
    }

    /**
    * @param user:
      * @param chatroomid:
      * @param uuid:
     * @return java.lang.String
     * @since 2019/5/13 16:08
    */
    public String AddMember(String user, String chatroomid, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("user", user);
        params.put("chatroomid", chatroomid);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(ADD_MEMBER, params);
    }

    /**
    * @param user:
      * @param chatroomid:
      * @param uuid:
     * @return java.lang.String
     * @since 2019/5/13 16:08
    */
    public String InviteMember(String user, String chatroomid, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("user", user);
        params.put("chatroomid", chatroomid);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(INVITE_MEMBER, params);
    }

    /**
    * @param user:
      * @param chatroomid:
      * @param uuid:
     * @return java.lang.String
     * @since 2019/5/13 16:08
    */
    public String DeleteMember(String user, String chatroomid, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("user", user);
        params.put("chatroomid", chatroomid);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(DEL_MEMBER, params);
    }

    /**
    * @param name:
      * @param chatroomid:
      * @param uuid:
     * @return java.lang.String
     * @since 2019/5/13 16:08
    */
    public String UpdateName(String name, String chatroomid, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("chatroomid", chatroomid);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(UPDATE_GROUP_NAME, params);
    }

    /**
    * @param name:
      * @param chatroomid:
      * @param uuid:
     * @return java.lang.String
     * @since 2019/5/13 16:08
    */
    public String UpdateNickName(String name, String chatroomid, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("chatroomid", chatroomid);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(UPDATE_NICK, params);
    }

    /**
    * @param context:
      * @param chatroomid:
      * @param uuid:
     * @return java.lang.String
     * @since 2019/5/13 16:08
    */
    public String UpdateAnnounce(String context, String chatroomid, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("context", context);
        params.put("chatroomid", chatroomid);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(UPDATE_ANNOUNCE, params);
    }

    /**
    * @param chatroomid:
      * @param uuid:
     * @return java.lang.String
     * @since 2019/5/13 16:08
    */
    public String GetQRCode(String chatroomid, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("chatroomid", chatroomid);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(GET_QRCODE, params);
    }

    /**
     * @param url:
     * @param uuid:
     * @return java.lang.String
     * @since 2019/5/13 16:08
    */
    public String ScanIn(String url, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("url", url);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(SCAN_IN, params);
    }
}
