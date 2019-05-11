package chs.wechat.spy.padsdk.msg_bus;

import chs.wechat.spy.db.mybatis.model.Contact;
import chs.wechat.spy.db.redis.RedisClientOperation;
import chs.wechat.spy.db.redis.RedisConnManager;
import chs.wechat.spy.utils.ConfigProperties;
import chs.wechat.spy.utils.GUID;
import chs.wechat.spy.utils.JSONUtil;
import chs.wechat.spy.utils.ServerResponse;
import chs.wechat.spy.websocket.WebSocketServerSingleton;
import com.alibaba.fastjson.JSONObject;

public class ApiMsgBus {
    private RedisClientOperation rco = new RedisClientOperation();

    public void ApiMsgDispatch(String json_msg) {
        rco.setJedisClient(RedisConnManager.getInstance().getJedis(rco.getJedis_id()));
        JSONObject evt = JSONObject.parseObject(json_msg);
        switch (evt.getString("action")) {
            case "log"://log信息
                ApiLogMsgProcess(evt.getString("context"), rco);
                break;
            case "qrcode"://返回二维码数据
                SendQR(evt.getString("context"));
                break;
            case "getcontact"://获取联系人信息。会多次传输
                ContactMsgProcess(evt.getString("context"));
                break;
            case "getgroup"://获取群组信息。会多次传输
                break;
            case "getgzh"://获取公众号信息。会多次传输
                break;
            case "msgcallback"://微信消息回调事件
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

    private void ContactMsgProcess(String context) {
        String uuid = ConfigProperties.GetProperties("app_uid");
        JSONObject jo_contact = JSONObject.parseObject(trimMsg(context));
        Contact contact = new Contact();
        contact.setId(GUID.getUUID());
        contact.setUserId(uuid);
        contact.setContactId(jo_contact.getString("user_name"));
        contact.setContactName(jo_contact.getString("nick_name"));
        contact.setRemark(jo_contact.getString("remark"));
        contact.setSex(jo_contact.getInteger("sex"));
        contact.setStranger(jo_contact.getString("stranger"));
        contact.setAddSource(jo_contact.getString("source"));
        contact.setLoc(jo_contact.getString("country") + " " + jo_contact.getString("provincia")
                + " " + jo_contact.getString("city"));
        contact.setSignature(jo_contact.getString("signature"));
        contact.setSmallHeadUrl(jo_contact.getString("small_head"));
        contact.setBigHeadUrl(jo_contact.getString("big_head"));
    }

    private String trimMsg(String source) {
        return source.replaceAll("\t*\n*\\s*", "");
    }

}
