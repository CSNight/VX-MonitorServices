package chs.wechat.spy.api_request;

import chs.wechat.spy.utils.ConfigProperties;
import chs.wechat.spy.utils.CustomHttpRequest;

import java.util.HashMap;
import java.util.Map;

public class ContactRequest {
    private String baseHost = ConfigProperties.GetProperties("api_host");
    private final String GET_CONTACT = baseHost + "/contact/get";//POST /api/contact/get
    private final String SET_REMARK = baseHost + "/contact/setremark";//POST /api/contact/setremark
    private final String DELETE_CONTACT = baseHost + "/contact/delete";//POST /api/contact/delete
    private final String ACCEPT_CONTACT = baseHost + "/contact/accept";//POST /api/contact/accept
    private final String GET_FRIENDS = baseHost + "/contact/getcontact";//POST /api/contact/getcontact
    private final String GET_ROOMS = baseHost + "/contact/getgroup";//POST /api/contact/getgroup
    private final String GET_PUBLIC_CONTACT = baseHost + "/contact/getgzh";//POST /api/contact/getgzh
    private final String SYNC_CONTACTS = baseHost + "/contact/synccontacts";//POST /api/contact/synccontacts

    /**
     * @param wx_id: 
     * @param uuid: 
     * @return java.lang.String
     */
    public String GetContact(String wx_id, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("wxid", wx_id);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(GET_CONTACT, params);
    }

    /**
     * @param remark:
     * @param wx_id:
     * @param uuid:
     * @return java.lang.String
     */
    public String SetRemark(String remark, String wx_id, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("remark", remark);
        params.put("wxid", wx_id);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(SET_REMARK, params);
    }

    /**
     * @param wx_id:
     * @param uuid:
     * @return java.lang.String
     */
    public String DeleteContact(String wx_id, String uuid) {

        Map<String, Object> params = new HashMap<>();
        params.put("wxid", wx_id);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(DELETE_CONTACT, params);
    }
    /**
     * @param stranger: 
     * @param ticket: 
     * @param uuid: 
     * @return java.lang.String
    */
    public String AcceptContact(String stranger, String ticket, String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("stranger", stranger);
        params.put("ticket", ticket);
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(ACCEPT_CONTACT, params);
    }

    /**
    * @param uuid: 
     * @return java.lang.String
    */
    public String GetFriends(String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(GET_FRIENDS, params);
    }
    /**
    * @param uuid: 
     * @return java.lang.String
    */
    public String GetRooms(String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(GET_ROOMS, params);
    }
    /**
    * @param uuid: 
     * @return java.lang.String
    */
    public String GetPublicContact(String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(GET_PUBLIC_CONTACT, params);
    }
    /**
     * @param uuid: 
     * @return java.lang.String
    */
    public String SyncContacts(String uuid) {
        Map<String, Object> params = new HashMap<>();
        params.put("uuid", uuid);
        return CustomHttpRequest.jsonPost(SYNC_CONTACTS, params);
    }
}
