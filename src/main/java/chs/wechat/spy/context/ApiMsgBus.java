package chs.wechat.spy.context;

import chs.wechat.spy.redis.RedisClientOperation;
import chs.wechat.spy.redis.RedisConnManager;
import chs.wechat.spy.utils.ConfigProperties;
import chs.wechat.spy.utils.ServerResponse;
import chs.wechat.spy.websocket.WebSocketServerSingleton;
import com.alibaba.fastjson.JSONObject;

public class ApiMsgBus {
    RedisClientOperation rco = new RedisClientOperation();

    public void ApiMsgDispatch(String json_msg) {
        rco.setJedisClient(RedisConnManager.getInstance().getJedis(rco.getJedis_id()));
        JSONObject evt = JSONObject.parseObject(json_msg);
        switch (evt.getString("action")) {
            case "log"://log信息
                ApiLogMsgProcess(evt.getString("context"), rco);
                break;
            case "qrcode"://返回二维码数据
                WebSocketServerSingleton wss = WebSocketServerSingleton.getInstance();
                wss.sendAll("data:image/jpeg;base64," + evt.getString("context"));
                break;
            case "getcontact"://获取联系人信息。会多次传输
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

    public void ApiLogMsgProcess(String context, RedisClientOperation rco) {
        String uuid = ConfigProperties.GetProperties("app_uid");
        if (context.equals("初始化成功:1")) {
            rco.setHashField(uuid, "init_status", "true");
        } else if (context.contains("初始化失败")) {
            rco.setHashField(uuid, "init_status", "false");
        } else if (context.equals("请扫描二维码")) {

        } else if (context.equals("已过期")) {
            WebSocketServerSingleton wss= WebSocketServerSingleton.getInstance();
            ServerResponse sr=new ServerResponse();
            sr.setSucess(1);
            sr.setResponse("OR_CODE_TIME_OUT");
            wss.sendAll("");
        }
    }
}
