package chs.wechat.spy.controller.user;

import chs.wechat.spy.api_request.LoginRequest;
import chs.wechat.spy.mybatis.mapper.WechatUserMapper;
import chs.wechat.spy.utils.GUID;
import chs.wechat.spy.websocket.WebSocketClient;
import com.alibaba.fastjson.JSONObject;
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
    public String get_qr() {
        uuid = GUID.getUUID();
        JSONObject res = JSONObject.parseObject(lr.GetQRCode(uuid, device_name));
        WebSocketClient ws = new WebSocketClient("ws://127.0.0.1:25015?action=scan&uuid=" + uuid + "&devicename=xzy-ipad&isreset=true");
        ws.open();
        return "<img src=data:image/jpeg;base64," + res.getString("Context") + " />";
    }
}
