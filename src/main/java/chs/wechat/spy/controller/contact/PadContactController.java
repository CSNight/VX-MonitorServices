package chs.wechat.spy.controller.contact;

import chs.wechat.spy.db.mybatis.mapper.ContactMapper;
import chs.wechat.spy.db.redis.RedisClientOperation;
import chs.wechat.spy.padsdk.api_request.ContactRequest;
import chs.wechat.spy.utils.ConfigProperties;
import chs.wechat.spy.utils.JSONUtil;
import chs.wechat.spy.utils.ServerResponse;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class PadContactController {
    private RedisClientOperation rco = new RedisClientOperation();
    @Resource
    private ContactMapper contactMapper;
    private ContactRequest cr = new ContactRequest();

    @RequestMapping(value = "/pad/contact/getDetail", params = {"wxid"})
    public String getContactDetail(String wxid) {
        String uuid = ConfigProperties.GetProperties("app_uid");
        JSONObject jo_res = JSONObject.parseObject(cr.GetContact(wxid, uuid));
        if (jo_res.getString("Success").equals("true")) {
            return JSONUtil.pojo2json(new ServerResponse(1, jo_res.getString("Context"), "GET62"));
        }
        return JSONUtil.pojo2json(new ServerResponse(0, "error", "GET62"));
    }

}
