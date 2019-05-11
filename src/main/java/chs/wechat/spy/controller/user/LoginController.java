package chs.wechat.spy.controller.user;

import chs.wechat.spy.db.mybatis.mapper.WechatUserMapper;
import chs.wechat.spy.db.redis.RedisClientOperation;
import chs.wechat.spy.db.redis.RedisConnManager;
import chs.wechat.spy.padsdk.api_request.LoginRequest;
import chs.wechat.spy.utils.ConfigProperties;
import chs.wechat.spy.utils.GUID;
import chs.wechat.spy.utils.JSONUtil;
import chs.wechat.spy.utils.UserStatus;
import chs.wechat.spy.websocket.WebSocketClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class LoginController {
    private String uuid = "";
    private String device_name = "ipad";
    private RedisClientOperation rco = new RedisClientOperation();
    @Resource
    private WechatUserMapper userMapper;
    LoginRequest lr = new LoginRequest();

    @RequestMapping("/auth")
    public String auth() {
        return "true";
    }

    @RequestMapping("/user/login_start")
    public String login_start() {
        RedisConnManager rcm = RedisConnManager.getInstance();
        rco.setJedisClient(RedisConnManager.getInstance().getJedis(rco.getJedis_id()));
        uuid = GUID.getUUID();
        UserStatus new_user = new UserStatus();
        new_user.setId(uuid);
        new_user.setStart_time(String.valueOf(System.currentTimeMillis()));
        new_user.setCurrent_opt("LOGIN_START");
        rco.setHashTable("uuid", JSONUtil.pojo2json(new_user), true);
        rcm.close(rco.getJedis_id());
        ConfigProperties.SetProperties("app_uid", uuid);
        WebSocketClient ws = WebSocketClient.getInstance();
        ws.setUri(uuid);
        ws.close();
        ws.open();
        return uuid;
    }
}
