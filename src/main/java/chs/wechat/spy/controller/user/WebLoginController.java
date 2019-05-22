package chs.wechat.spy.controller.user;

import chs.wechat.spy.db.mybatis.mapper.WechatUserMapper;
import chs.wechat.spy.db.redis.RedisClientOperation;
import chs.wechat.spy.db.redis.RedisConnManager;
import chs.wechat.spy.utils.ConfigProperties;
import chs.wechat.spy.utils.GUID;
import chs.wechat.spy.utils.JSONUtil;
import chs.wechat.spy.utils.UserStatus;
import chs.wechat.spy.websdk.core.LoginModel;
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
    private LoginModel lm = LoginModel.getInstance();

    @RequestMapping(value = "/web/login/login_start", method = RequestMethod.GET, params = {"user_id"})
    public String login_start(String user_id) {
        return StartInstance(user_id);
    }

    private String StartInstance(String user_id) {
        RedisConnManager rcm = RedisConnManager.getInstance();
        rco.setJedisClient(RedisConnManager.getInstance().getJedis(rco.getJedis_id()));
        if (ConfigProperties.GetProperties("web_uid") != null) {
            rco.deleteKey(ConfigProperties.GetProperties("web_uid"));
        }
        String uuid = GUID.getUUID();
        UserStatus new_user = new UserStatus();
        new_user.setId(user_id);
        new_user.setStart_time(String.valueOf(System.currentTimeMillis()));
        new_user.setCurrent_opt("LOGIN_START");
        rco.setHashTable(uuid, JSONUtil.pojo2json(new_user), true);
        rcm.close(rco.getJedis_id());
        lm.login(uuid);
        return uuid;
    }
}
