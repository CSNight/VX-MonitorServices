package chs.wechat.spy.controller.user;

import chs.wechat.spy.db.mybatis.mapper.WechatUserMapper;
import chs.wechat.spy.db.mybatis.model.WechatUserWithBLOBs;
import chs.wechat.spy.db.redis.RedisClientOperation;
import chs.wechat.spy.db.redis.RedisConnManager;
import chs.wechat.spy.padsdk.api_request.LoginRequest;
import chs.wechat.spy.padsdk.api_request.UserRequest;
import chs.wechat.spy.utils.*;
import chs.wechat.spy.websocket.WebSocketClient;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;

@RestController
public class PadLoginController {
    private final String device_name = "ipad";
    private RedisClientOperation rco = new RedisClientOperation();
    @Resource
    private WechatUserMapper userMapper;
    private LoginRequest lr = new LoginRequest();

    @RequestMapping("/auth")
    public String auth() {
        return "true";
    }

    @RequestMapping(value = "/init", method = RequestMethod.POST, params = {"info"})
    public String userInit(String info) {
        JSONObject user_info = JSONObject.parseObject(info);
        WechatUserWithBLOBs wechatUser = new WechatUserWithBLOBs();
        String user_identify = GUID.getUUID();
        wechatUser.setId(user_identify);
        wechatUser.setUserId(user_info.getString("wxid"));
        wechatUser.setUserName(user_info.getString("user_name"));
        wechatUser.setPwd(user_info.getString("pwd"));
        userMapper.insertSelective(wechatUser);
        return user_identify;
    }

    @RequestMapping(value = "/pad/login/login_start", method = RequestMethod.GET, params = {"user_id"})
    public String login_start(String user_id) {
        return StartInstance(user_id);
    }

    @RequestMapping(value = "/pad/login/login_62", method = RequestMethod.GET, params = {"login_obj"})
    public String login_62(String login_obj) {
        JSONObject loginParams = JSONObject.parseObject(login_obj);
        String user_id = loginParams.getString("user_id");
        String user_name = loginParams.getString("user_name");
        String pwd = loginParams.getString("password");
        String uuid = StartInstance(user_id);
        String str62 = userMapper.selectByPrimaryKey(user_id).getStr62();
        return lr.Login_62(user_name, pwd, str62, uuid, device_name);
    }

    @RequestMapping(value = "/pad/login/get62Hex")
    public String get62Hex(@PathParam(value = "user_id") String user_id) {
        String res = lr.Get62Hex(ConfigProperties.GetProperties("app_uid"));
        JSONObject res_json = JSONObject.parseObject(res);
        if (res_json.getString("Success").equals("true")) {
            WechatUserWithBLOBs userWithBLOBs = new WechatUserWithBLOBs();
            userWithBLOBs.setId(user_id);
            userWithBLOBs.setStr62(res_json.getString("Context"));
            userMapper.updateByPrimaryKeySelective(userWithBLOBs);
            return JSONUtil.pojo2json(new ServerResponse(1, "success", "GET62"));
        }
        return JSONUtil.pojo2json(new ServerResponse(0, "error", "GET62"));
    }

    @RequestMapping(value = "/pad/login/reconnect", method = RequestMethod.GET, params = {"user_id"})
    public String reconnect(String user_id) {
        String uuid = ConfigProperties.GetProperties("app_uid");
        JSONObject token_res = JSONObject.parseObject(lr.GetToken(uuid));
        String str62 = userMapper.selectByPrimaryKey(user_id).getStr62();
        if (token_res.getString("Success").equals("true") && uuid != null && str62 != null) {
            String rec_res = JSONObject.parseObject(lr.Reconnect(str62, token_res.getString("Context"), uuid)).getString("Success");
            if (rec_res.equals("true")) {
                return JSONUtil.pojo2json(new ServerResponse(1, "success", "RECONNECT"));
            }
        }
        return JSONUtil.pojo2json(new ServerResponse(0, "success", "RECONNECT"));
    }

    @RequestMapping(value = "/pad/login/logout")
    public String logout() {
        String uuid = ConfigProperties.GetProperties("app_uid");
        UserRequest userRequest = new UserRequest();
        String rec_res = JSONObject.parseObject(userRequest.UserLogout(uuid)).getString("Success");
        if (rec_res.equals("true")) {
            WebSocketClient ws = WebSocketClient.getInstance();
            ws.close();
            RedisConnManager rcm = RedisConnManager.getInstance();
            rco.setJedisClient(RedisConnManager.getInstance().getJedis(rco.getJedis_id()));
            rco.deleteKey(uuid);
            rcm.close(rco.getJedis_id());
            return JSONUtil.pojo2json(new ServerResponse(1, "success", "LOGOUT"));
        }
        return JSONUtil.pojo2json(new ServerResponse(0, "success", "LOGOUT"));
    }

    private String StartInstance(String user_id) {
        RedisConnManager rcm = RedisConnManager.getInstance();
        rco.setJedisClient(RedisConnManager.getInstance().getJedis(rco.getJedis_id()));
        if (ConfigProperties.GetProperties("app_uid") != null) {
            rco.deleteKey(ConfigProperties.GetProperties("app_uid"));
        }
        String uuid = GUID.getUUID();
        UserStatus new_user = new UserStatus();
        new_user.setId(user_id);
        new_user.setStart_time(String.valueOf(System.currentTimeMillis()));
        new_user.setCurrent_opt("LOGIN_START");
        rco.setHashTable(uuid, JSONUtil.pojo2json(new_user), true);
        rcm.close(rco.getJedis_id());
        ConfigProperties.SetProperties("app_uid", uuid);
        WebSocketClient ws = WebSocketClient.getInstance();
        ws.setUri(uuid);
        ws.close();
        ws.open();
        return uuid;
    }
}
