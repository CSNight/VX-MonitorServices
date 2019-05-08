package chs.wechat.spy.controller.user;

import chs.wechat.spy.api_request.LoginRequest;
import chs.wechat.spy.mybatis.mapper.WechatUserMapper;
import chs.wechat.spy.utils.ConfigProperties;
import chs.wechat.spy.utils.GUID;
import chs.wechat.spy.websocket.WebSocketClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class LoginController {
    private String uuid = "";
    private String device_name = "ipad";
    @Resource
    private WechatUserMapper userMapper;
    LoginRequest lr = new LoginRequest();

    @RequestMapping("/auth")
    public String auth() {
        return "true";
    }

    @RequestMapping("/user/login_start")
    public String login_start() {
        uuid = GUID.getUUID();
        ConfigProperties.SetProperties("app_uid", uuid);
        WebSocketClient ws = WebSocketClient.getInstance();
        ws.setUri(uuid);
        ws.open();
        return uuid;
    }
}
