package chs.wechat.spy.padsdk.msg_bus;

import chs.wechat.spy.controller.impl.ContactImpl;
import chs.wechat.spy.db.mybatis.model.ContactWithBLOBs;
import chs.wechat.spy.utils.CustomHttpRequest;
import chs.wechat.spy.utils.GUID;
import chs.wechat.spy.controller.ReflectUtils;
import com.alibaba.fastjson.JSONObject;

public class ContactToDB {
    public void ContactCallBack(String uuid, JSONObject jo_contact) {
        ContactWithBLOBs contact = new ContactWithBLOBs();
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
        contact.setSmallHead(CustomHttpRequest.download(contact.getSmallHeadUrl()));
        contact.setBigHead(CustomHttpRequest.download(contact.getBigHeadUrl()));
        ReflectUtils.getBean(ContactImpl.class).insertSelective(contact);
    }
}
