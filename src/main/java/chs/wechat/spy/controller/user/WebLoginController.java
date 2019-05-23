package chs.wechat.spy.controller.user;

import chs.wechat.spy.db.mybatis.mapper.WechatUserMapper;
import chs.wechat.spy.db.redis.RedisClientOperation;
import chs.wechat.spy.websdk.WebApiBus;
import chs.wechat.spy.websdk.api.constant.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
public class WebLoginController {
    private final String device_name = "web";
    private final Logger logger = LoggerFactory.getLogger(WebLoginController.class);
    private RedisClientOperation rco = new RedisClientOperation();
    @Resource
    private WechatUserMapper userMapper;

    @RequestMapping(value = "/web/login/login_start", method = RequestMethod.GET, params = {"user_id"})
    public String login_start(String user_id) {
        return StartInstance(user_id);
    }

    private String StartInstance(String user_id) {
        WebApiBus s = new WebApiBus(Config.me().autoLogin(true));
        s.start();
        return "";
    }
}
