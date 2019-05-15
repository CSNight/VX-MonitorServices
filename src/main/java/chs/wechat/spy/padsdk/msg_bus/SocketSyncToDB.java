package chs.wechat.spy.padsdk.msg_bus;

import chs.wechat.spy.controller.ReflectUtils;
import chs.wechat.spy.controller.impl.*;
import chs.wechat.spy.db.mybatis.model.*;
import chs.wechat.spy.db.redis.RedisClientOperation;
import chs.wechat.spy.utils.DownloadItem;
import chs.wechat.spy.utils.GUID;
import chs.wechat.spy.utils.JSONUtil;
import com.alibaba.fastjson.JSONObject;

import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SocketSyncToDB {
    private XmlMsgParser xmlMsgParser = new XmlMsgParser();
    private MsgFileGen msgFileGen = new MsgFileGen();

    public void ContactCallBack(String user_id, JSONObject jo_contact, RedisClientOperation rco) {
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
        DownloadItem smallHead = new DownloadItem("", "contact", jo_contact.getString("small_head"), "small");
        DownloadItem bigHead = new DownloadItem("", "contact", jo_contact.getString("big_head"), "big");
        ContactImpl contactImpl = ReflectUtils.getBean(ContactImpl.class);
        Map<String, String> identify = new HashMap<>();
        identify.put("user_id", user_id);
        identify.put("contact_id", jo_contact.getString("user_name"));
        String contact_unique = contactImpl.getContactById(identify);
        if (contact_unique != null) {
            contact.setId(contact_unique);
            smallHead.setId(contact_unique);
            bigHead.setId(contact_unique);
            contactImpl.updateByPrimaryKeySelective(contact);
        } else {
            String uid = GUID.getUUID();
            contact.setId(uid);
            smallHead.setId(uid);
            bigHead.setId(uid);
            contactImpl.insertSelective(contact);
        }
        rco.getJedisClient().rpush("downloadQueue", JSONUtil.pojo2json(smallHead), JSONUtil.pojo2json(bigHead));
    }

    public void PublicCTCallBack(String user_id, JSONObject jo_public_ct, RedisClientOperation rco) {
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
        pc.setBigHeadUrl(jo_public_ct.getString("big_head"));
        DownloadItem smallHead = new DownloadItem("", "public", jo_public_ct.getString("small_head"), "small");
        DownloadItem bigHead = new DownloadItem("", "public", jo_public_ct.getString("big_head"), "big");
        PublicContactImpl publicContactImpl = ReflectUtils.getBean(PublicContactImpl.class);
        Map<String, String> identify = new HashMap<>();
        identify.put("user_id", user_id);
        identify.put("pct_id", jo_public_ct.getString("user_name"));
        String pct_unique = publicContactImpl.getPublicCtById(identify);
        if (pct_unique != null) {
            pc.setId(pct_unique);
            smallHead.setId(pct_unique);
            bigHead.setId(pct_unique);
            publicContactImpl.updateByPrimaryKeySelective(pc);
        } else {
            String uid = GUID.getUUID();
            pc.setId(uid);
            smallHead.setId(uid);
            bigHead.setId(uid);
            publicContactImpl.insertSelective(pc);
        }
        rco.getJedisClient().rpush("downloadQueue", JSONUtil.pojo2json(smallHead), JSONUtil.pojo2json(bigHead));
    }

    public void GroupCallBack(String user_id, JSONObject jo_group, RedisClientOperation rco) {
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
        DownloadItem smallHead = new DownloadItem("", "chat_room", jo_group.getString("small_head"), "small");
        Map<String, String> identify = new HashMap<>();
        identify.put("user_id", user_id);
        identify.put("room_id", jo_group.getString("user_name"));
        String group_unique = chatRoomsImpl.getRoomById(identify);
        if (group_unique != null) {
            chatRooms.setId(group_unique);
            smallHead.setId(group_unique);
            chatRoomsImpl.updateByPrimaryKeySelective(chatRooms);
        } else {
            String uid = GUID.getUUID();
            chatRooms.setId(uid);
            smallHead.setId(uid);
            chatRoomsImpl.insertSelective(chatRooms);
        }
        rco.getJedisClient().rpush("downloadQueue", JSONUtil.pojo2json(smallHead));
    }

    public MsgLog MsgTextProcess(String user_id, JSONObject jo_msg, RedisClientOperation rco) {
        MsgLog msgLog = new MsgLog();
        msgLog.setId(GUID.getUUID());
        msgLog.setUserId(user_id);
        msgLog.setMsgType(jo_msg.getInteger("msg_type"));
        msgLog.setMsgSubtype(jo_msg.getInteger("sub_type"));
        msgLog.setMsgTime(new Date(jo_msg.getLong("timestamp") * 1000));
        msgLog.setFromUser(jo_msg.getString("from_user"));
        msgLog.setFromType(getUserType(jo_msg.getString("from_user")));
        msgLog.setToUser(jo_msg.getString("to_user"));
        msgLog.setToType(getUserType(jo_msg.getString("from_user")));
        msgLog.setUin(jo_msg.getString("uin"));
        msgLog.setMsgId(jo_msg.getString("msg_id"));
        msgLog.setMsgStatus(jo_msg.getInteger("status"));
        msgLog.setContinues(jo_msg.getInteger("continue"));
        msgLog.setMsgDescribe(jo_msg.getString("content").getBytes(Charset.forName("utf-8")));
        msgLog.setDescriptions(jo_msg.getString("description"));
        msgLog.setMsgSource(jo_msg.getString("msg_source").replaceAll("\\\\t|\\\\n", ""));
        return msgLog;
    }

    public void LogToDB(MsgLog msgLog) {
        MsgLogImpl msgLogImpl = ReflectUtils.getBean(MsgLogImpl.class);
        msgLogImpl.insertSelective(msgLog);
    }

    public void StToDB(MsgLog msgLog, JSONObject jo_msg, int msg_subtype) {
        Map<String, Object> prop = null;
        String content = jo_msg.getString("content");
        switch (msg_subtype) {
            case 1://文字
            case 10000://系统消息 群聊拉人进群退群消息等
            case 51://好友focus op id =2 进入聊天、公号 op id =5 退出(公号）
                LogToDB(msgLog);
                break;
            case 3://图片
                prop = xmlMsgParser.ImageParser(content);
                msgFileGen.imgFile(msgLog, prop, jo_msg.toString());
                break;
            case 34://语音
                prop = xmlMsgParser.VoiceParser(content);
                msgFileGen.voiceFile(msgLog, prop, jo_msg.toString());
                break;
            case 35://邮件推送
                prop = xmlMsgParser.mailParser(content);
                msgFileGen.MsgFileCommon(msgLog, prop, "email");
                break;
            case 42://名片
                prop = xmlMsgParser.CardParser(content);
                msgFileGen.MsgFileCommon(msgLog, prop, "card");
                break;
            case 43://视频
                prop = xmlMsgParser.VideoParser(content);
                msgFileGen.videoFile(msgLog, prop, jo_msg.toString());
                break;
            case 47://大表情
                prop = xmlMsgParser.emojiParser(content);
                msgFileGen.emojiFile(msgLog, prop);
                break;
            case 48://位置
                prop = xmlMsgParser.PosParser(content);
                msgFileGen.MsgFileCommon(msgLog, prop, "pos");
                break;
            case 49://链接、文件、卡券、红包
                prop = xmlMsgParser.AppDispatch(content);
                if (prop.get("type").equals("2001")) {
                    msgFileGen.redPack(msgLog, prop, jo_msg.toString());
                } else if (prop.get("type").equals("5")) {
                    msgFileGen.MsgFileCommon(msgLog, prop, "link");
                } else if (prop.get("type").equals("33")) {
                    msgFileGen.MsgFileCommon(msgLog, prop, "app");
                } else if (prop.get("type").equals("6")) {
                    msgFileGen.MsgFileCommon(msgLog, prop, "file");
                } else {
                    msgFileGen.MsgFileCommon(msgLog, prop, "unknown");
                }
                break;
            case 50://语音、视频通话
                LogToDB(msgLog);
                //prop = xmlMsgParser.ImageParser(jo_msg.getString("content"));
                break;

        }
    }

    private String getUserType(String contact) {
        if (contact.contains("@charroom")) {
            return "group";
        } else if (contact.contains("gh_")) {
            return "public";
        } else {
            return "private";
        }
    }
}
