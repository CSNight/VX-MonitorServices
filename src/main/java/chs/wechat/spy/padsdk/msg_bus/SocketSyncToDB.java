package chs.wechat.spy.padsdk.msg_bus;

import chs.wechat.spy.controller.ReflectUtils;
import chs.wechat.spy.controller.impl.ChatRoomsImpl;
import chs.wechat.spy.controller.impl.ContactImpl;
import chs.wechat.spy.controller.impl.PublicContactImpl;
import chs.wechat.spy.controller.impl.WechatUserImpl;
import chs.wechat.spy.db.mybatis.model.ChatRooms;
import chs.wechat.spy.db.mybatis.model.ContactWithBLOBs;
import chs.wechat.spy.db.mybatis.model.PublicContactWithBLOBs;
import chs.wechat.spy.db.mybatis.model.WechatUserWithBLOBs;
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

    public void PublicCTCallBack(String user_id, JSONObject jo_public_ct) {
        PublicContactWithBLOBs pc = new PublicContactWithBLOBs();
        pc.setUserId(user_id);
        pc.setPctId(jo_public_ct.getString("user_name"));
        pc.setPctName(jo_public_ct.getString("nick_name"));
        pc.setSign(jo_public_ct.getString("signature"));
        pc.setStranger(jo_public_ct.getString("stranger"));
        pc.setLoc(jo_public_ct.getString("country") + " " + jo_public_ct.getString("provincia")
                + " " + jo_public_ct.getString("city"));
        pc.setIntro(jo_public_ct.getString("intro"));
        pc.setAddSource(jo_public_ct.getInteger("source"));
        pc.setSmallHeadUrl(jo_public_ct.getString("small_head"));
        pc.setSmallHead(CustomHttpRequest.download(jo_public_ct.getString("small_head")));
        pc.setBigHeadUrl(jo_public_ct.getString("big_head"));
        pc.setBigHead(CustomHttpRequest.download(jo_public_ct.getString("big_head")));
        PublicContactImpl publicContactImpl = ReflectUtils.getBean(PublicContactImpl.class);
        Map<String, String> identify = new HashMap<>();
        identify.put("user_id", user_id);
        identify.put("pct_id", jo_public_ct.getString("user_name"));
        String contact_unique = publicContactImpl.getPublicCtById(identify);
        if (contact_unique != null) {
            pc.setId(contact_unique);
            publicContactImpl.updateByPrimaryKeySelective(pc);
        } else {
            pc.setId(GUID.getUUID());
            publicContactImpl.insertSelective(pc);
        }
    }

    public void GroupCallBack(String user_id, JSONObject jo_group) {
        ChatRoomsImpl chatRoomsImpl = ReflectUtils.getBean(ChatRoomsImpl.class);
        WechatUserImpl wechatUserImpl = ReflectUtils.getBean(WechatUserImpl.class);
        WechatUserWithBLOBs user = wechatUserImpl.selectByPrimaryKey(user_id);
        ChatRooms chatRooms = new ChatRooms();
        chatRooms.setUserId(user_id);
        chatRooms.setRoomId(jo_group.getString("user_name"));
        chatRooms.setRoomOwner(jo_group.getString("chatroom_owner"));
        chatRooms.setIsOwner(jo_group.getString("chatroom_owner").equals(user.getUserId()));
        chatRooms.setMemberCount(jo_group.getInteger("member_count"));
        chatRooms.setRoomNick(jo_group.getString("nick_name").equals("") ? "群聊" : jo_group.getString("nick_name"));
        chatRooms.setRoomCode(jo_group.getString("chatroom_id"));
        chatRooms.setSmallHeadUrl(jo_group.getString("small_head"));
        chatRooms.setSmallHead(CustomHttpRequest.download(jo_group.getString("small_head")));

        Map<String, String> identify = new HashMap<>();
        identify.put("user_id", user_id);
        identify.put("room_id", jo_group.getString("user_name"));
        String group_unique = chatRoomsImpl.getRoomById(identify);
        if (group_unique != null) {
            chatRooms.setId(group_unique);
            chatRoomsImpl.updateByPrimaryKeySelective(chatRooms);
        } else {
            chatRooms.setId(GUID.getUUID());
            chatRoomsImpl.insertSelective(chatRooms);
        }
    }
}
