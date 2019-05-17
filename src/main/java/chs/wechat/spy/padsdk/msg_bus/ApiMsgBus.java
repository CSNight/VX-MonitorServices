package chs.wechat.spy.padsdk.msg_bus;

import chs.wechat.spy.controller.ReflectUtils;
import chs.wechat.spy.controller.impl.ChatRoomsImpl;
import chs.wechat.spy.controller.impl.ContactImpl;
import chs.wechat.spy.controller.impl.PublicContactImpl;
import chs.wechat.spy.controller.impl.RoomMembersImpl;
import chs.wechat.spy.db.mybatis.model.MsgLog;
import chs.wechat.spy.db.redis.RedisClientOperation;
import chs.wechat.spy.db.redis.RedisConnManager;
import chs.wechat.spy.utils.ConfigProperties;
import chs.wechat.spy.utils.JSONUtil;
import chs.wechat.spy.utils.ServerResponse;
import chs.wechat.spy.websocket.WebSocketServerSingleton;
import com.alibaba.fastjson.JSONObject;

public class ApiMsgBus {
    private RedisClientOperation rco = new RedisClientOperation();
    private SocketSyncToDB syncCallbackToDB = new SocketSyncToDB();

    public void ApiMsgDispatch(String json_msg) {
        rco.setJedisClient(RedisConnManager.getInstance().getJedis(rco.getJedis_id()));
        String uuid = ConfigProperties.GetProperties("app_uid");
        String user_id = rco.getHashField(uuid, "id");
        JSONObject evt = JSONObject.parseObject(json_msg);
        switch (evt.getString("action")) {
            case "log"://log信息
                ApiLogMsgProcess(evt.getString("context"), rco);
                break;
            case "qrcode"://返回二维码数据
                SendQR(evt.getString("context"));
                break;
            case "getcontact"://获取联系人信息。会多次传输
                ContactMsgProcess(evt.getString("context"), user_id, rco);
                break;
            case "getgroup"://获取群组信息。会多次传输
                GroupMsgProcess(evt.getString("context"), user_id, rco);
                break;
            case "getgzh"://获取公众号信息。会多次传输
                PublicCTMsgProcess(evt.getString("context"), user_id, rco);
                break;
            case "msgcallback"://微信消息回调事件
                MsgCallBackProcess(evt.getString("context"), user_id, rco);
                break;
        }
        RedisConnManager.getInstance().close(rco.getJedis_id());
    }

    private void ApiLogMsgProcess(String context, RedisClientOperation rco) {
        String uuid = ConfigProperties.GetProperties("app_uid");
        if (context.equals("初始化成功:1")) {
            rco.setHashField(uuid, "init_status", "true");
            rco.setHashField(uuid, "current_opt", "INIT_SUCCESS");
        } else if (context.contains("初始化失败")) {
            rco.setHashField(uuid, "init_status", "false");
            rco.setHashField(uuid, "current_opt", "INIT_FAILED");
        } else if (context.equals("请扫描二维码") || context.equals("请点在手机上点确认")) {
            rco.setHashField(uuid, "current_opt", "WAITING_LOGIN");
        } else if (context.equals("已过期")) {
            rco.setHashField(uuid, "current_opt", "TIME_OUT");
            WebSocketServerSingleton wss = WebSocketServerSingleton.getInstance();
            ServerResponse sr = new ServerResponse(1, "TIME_OUT", "OR_CODE_TIME_OUT");
            wss.sendAll(JSONUtil.pojo2json(sr));
        } else if (context.equals("正在登录中")) {
            rco.setHashField(uuid, "current_opt", "LOGIN_ING");
        } else if (context.equals("登录成功")) {
            rco.setHashField(uuid, "current_opt", "LOGIN_SUCCESS");
            rco.setHashField(uuid, "login_status", "login");
            rco.setHashField(uuid, "login_time", String.valueOf(System.currentTimeMillis()));
            truncateContact();
        } else if (context.contains("已下线")) {
            rco.setHashField(uuid, "current_opt", "LOGOUT");
            rco.setHashField(uuid, "login_status", "logout");
            rco.setHashField(uuid, "logout_time", String.valueOf(System.currentTimeMillis()));
            WebSocketServerSingleton wss = WebSocketServerSingleton.getInstance();
            ServerResponse sr = new ServerResponse(1, "LOGOUT", "LOGOUT");
            wss.sendAll(JSONUtil.pojo2json(sr));
        }
    }

    private void SendQR(String context) {
        WebSocketServerSingleton wss = WebSocketServerSingleton.getInstance();
        ServerResponse sr = new ServerResponse();
        sr.setSuccess(1);
        if (!context.equals("")) {
            sr.setSuccess(0);
        }
        sr.setType("QR_CODE");
        sr.setResponse("data:image/jpeg;base64," + context);
        wss.sendAll(JSONUtil.pojo2json(sr));
    }

    private void ContactMsgProcess(String context, String user_id, RedisClientOperation rco) {
        JSONObject jo_contact = JSONObject.parseObject(trimMsg(context));
        syncCallbackToDB.ContactCallBack(user_id, jo_contact, rco);
    }

    private void GroupMsgProcess(String context, String user_id, RedisClientOperation rco) {
        JSONObject jo_group = JSONObject.parseObject(trimMsg(context));
        syncCallbackToDB.GroupCallBack(user_id, jo_group, rco);
    }

    private void PublicCTMsgProcess(String context, String user_id, RedisClientOperation rco) {
        JSONObject jo_public = JSONObject.parseObject(trimMsg(context));
        syncCallbackToDB.PublicCTCallBack(user_id, jo_public, rco);
    }

    public void MsgCallBackProcess(String context, String user_id, RedisClientOperation rco) {
        JSONObject jo_msg = JSONObject.parseObject(trimMsg(context));
        int msg_type = jo_msg.getInteger("msg_type");
        int msg_subtype = jo_msg.getInteger("sub_type");
        MsgLog msgComm = syncCallbackToDB.MsgTextProcess(user_id, jo_msg, rco);
        if (msg_type == 5) {
            syncCallbackToDB.StToDB(msgComm, jo_msg, msg_subtype);
        }
    }

    private String trimMsg(String source) {
        return source.replaceAll("\\\\t|\\\\r|\\\\n", "");
    }

    private void truncateContact() {
        ContactImpl contactImpl = ReflectUtils.getBean(ContactImpl.class);
        contactImpl.truncate();
        ChatRoomsImpl chatRoomsImpl = ReflectUtils.getBean(ChatRoomsImpl.class);
        chatRoomsImpl.truncate();
        RoomMembersImpl roomMembersImpl = ReflectUtils.getBean(RoomMembersImpl.class);
        roomMembersImpl.truncate();
        PublicContactImpl publicContactImpl = ReflectUtils.getBean(PublicContactImpl.class);
        publicContactImpl.truncate();
    }

}
