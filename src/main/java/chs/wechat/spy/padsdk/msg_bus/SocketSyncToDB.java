package chs.wechat.spy.padsdk.msg_bus;

import chs.wechat.spy.controller.ReflectUtils;
import chs.wechat.spy.controller.impl.ContactImpl;
import chs.wechat.spy.db.mybatis.model.ContactWithBLOBs;
import chs.wechat.spy.utils.CustomHttpRequest;
import chs.wechat.spy.utils.GUID;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SocketSyncToDB {
    public void ContactCallBack(String user_id, JSONObject jo_contact) {
        ContactWithBLOBs contact = new ContactWithBLOBs();
        contact.setUserId(user_id);
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
        contact.setSmallHead(CustomHttpRequest.download(contact.getSmallHeadUrl()));
        contact.setBigHead(CustomHttpRequest.download(contact.getBigHeadUrl()));
        ContactImpl contactImpl = ReflectUtils.getBean(ContactImpl.class);
        Map<String, String> identify = new HashMap<>();
        identify.put("user_id", user_id);
        identify.put("contact_id", jo_contact.getString("user_name"));
        String contact_unique = contactImpl.getContactById(identify);
        if (contact_unique != null) {
            contact.setId(contact_unique);
            contactImpl.updateByPrimaryKeySelective(contact);
        } else {
            contact.setId(GUID.getUUID());
            contactImpl.insertSelective(contact);
        }
    }
}
