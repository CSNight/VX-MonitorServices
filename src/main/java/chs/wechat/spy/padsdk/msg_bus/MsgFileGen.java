package chs.wechat.spy.padsdk.msg_bus;

import chs.wechat.spy.controller.ReflectUtils;
import chs.wechat.spy.controller.impl.MsgFileImpl;
import chs.wechat.spy.controller.impl.MsgLogImpl;
import chs.wechat.spy.db.mybatis.model.MsgFile;
import chs.wechat.spy.db.mybatis.model.MsgLog;
import chs.wechat.spy.padsdk.api_request.MsgRequest;
import chs.wechat.spy.padsdk.api_request.UtilsRequest;
import chs.wechat.spy.utils.ConfigProperties;
import chs.wechat.spy.utils.CustomHttpRequest;
import chs.wechat.spy.utils.GUID;
import chs.wechat.spy.utils.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

public class MsgFileGen {
    private MsgRequest mr = new MsgRequest();

    private MsgFile BuildComm(MsgLog msgLog, Map<String, Object> meta) {
        MsgFile msgFile = new MsgFile();
        String uid = GUID.getUUID();
        msgFile.setId(uid);
        msgFile.setUserId(msgLog.getUserId());
        msgFile.setMeta(JSONUtil.map2json(meta));
        msgFile.setFileTime(msgLog.getMsgTime());
        msgFile.setFromId(msgLog.getFromUser());
        return msgFile;
    }

    public void imgFile(MsgLog msgLog, Map<String, Object> meta, String msg) {
        String uuid = ConfigProperties.GetProperties("app_uid");
        MsgFile msgFile = BuildComm(msgLog, meta);
        msgFile.setFileName("image_" + System.currentTimeMillis());
        try {
            JSONObject res = JSONObject.parseObject(mr.GetImg((Map<String, Object>) JSONObject.parseObject(msg, Map.class), uuid));
            if (res.getString("Success").equals("true")) {
                msgFile.setFileBlob(Base64ToByte(res.getJSONObject("Context"), "image"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        msgFile.setExt(".png");
        MsgFileInsert(msgFile, msgLog);
    }

    public void voiceFile(MsgLog msgLog, Map<String, Object> meta, String msg) {
        String uuid = ConfigProperties.GetProperties("app_uid");
        MsgFile msgFile = BuildComm(msgLog, meta);
        msgFile.setFileName("voice_" + System.currentTimeMillis());
        try {
            JSONObject res = JSONObject.parseObject(mr.GetVoice((Map<String, Object>) JSONObject.parseObject(msg, Map.class), uuid));
            if (res.getString("Success").equals("true")) {
                UtilsRequest ur = new UtilsRequest();
                BASE64Encoder base64Encoder = new BASE64Encoder();
                BASE64Decoder base64Decoder = new BASE64Decoder();
                String mp3_str = ur.AmrToMp3(base64Encoder.encode(Base64ToByte(res.getJSONObject("Context"), "voice")));
                msgFile.setFileBlob(base64Decoder.decodeBuffer(mp3_str));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        msgFile.setExt(".mp3");
        MsgFileInsert(msgFile, msgLog);
    }

    public void videoFile(MsgLog msgLog, Map<String, Object> meta, String msg) {
        String uuid = ConfigProperties.GetProperties("app_uid");
        MsgFile msgFile = BuildComm(msgLog, meta);
        msgFile.setFileName("video_" + System.currentTimeMillis());
        try {
            JSONObject res = JSONObject.parseObject(mr.GetVideo((Map<String, Object>) JSONObject.parseObject(msg, Map.class), uuid));
            if (res.getString("Success").equals("true")) {
                msgFile.setFileBlob(Base64ToByte(res.getJSONObject("Context"), "video"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        msgFile.setExt(".mp4");
        MsgFileInsert(msgFile, msgLog);
    }

    public void redPack(MsgLog msgLog, Map<String, Object> meta, String msg) {
        String uuid = ConfigProperties.GetProperties("app_uid");
        MsgFile msgFile = BuildComm(msgLog, meta);
        msgFile.setFileName("red_pack_" + System.currentTimeMillis());
        try {
            String res = mr.GetRedPack((Map<String, Object>) JSONObject.parseObject(msg, Map.class), uuid);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        msgFile.setExt("red_pack");
        MsgFileInsert(msgFile, msgLog);
    }

    public void emojiFile(MsgLog msgLog, Map<String, Object> meta) {
        MsgFile msgFile = BuildComm(msgLog, meta);
        msgFile.setFileName("emoji_" + System.currentTimeMillis());
        msgFile.setFileBlob(CustomHttpRequest.download(meta.get("cdnurl").toString()));
        msgFile.setExt(".gif");
        MsgFileInsert(msgFile, msgLog);
    }

    public void callFile(MsgLog msgLog, Map<String, Object> meta) {
        MsgFile msgFile = BuildComm(msgLog, meta);
        msgFile.setFileName(meta.get("status").toString() + "_" + System.currentTimeMillis());
        msgFile.setExt(meta.get("invite_type").toString());
        msgFile.setFileBlob(meta.get("room_id").toString().getBytes(Charset.forName("utf-8")));
        MsgFileInsert(msgFile, msgLog);
    }

    public void sysFile(MsgLog msgLog, Map<String, Object> meta, String content) {
        String type = meta.get("type").toString();
        MsgFile msgFile = BuildComm(msgLog, meta);
        switch (type) {
            case "revokemsg":
                msgFile.setExt(type);
                msgFile.setFileName("revokemsg_" + System.currentTimeMillis());
                break;
            case "functionmsg":
                msgFile.setExt("red_dot");
                msgFile.setFileName("red_dot_" + System.currentTimeMillis());
                break;
            case "dynacfg":
                msgFile.setExt("dynacfg");
                msgFile.setFileName("dynacfg_" + System.currentTimeMillis());
                break;
            case "ClientCheckGetExtInfo":
                msgFile.setExt(type);
                msgFile.setFileName(type + "_" + System.currentTimeMillis());
                break;
        }
        msgFile.setFileBlob(content.getBytes(Charset.forName("utf-8")));
        msgLog.setMsgDescribe(null);
        MsgFileInsert(msgFile, msgLog);
    }

    public void MsgFileCommon(MsgLog msgLog, Map<String, Object> meta, String t) {
        MsgFile msgFile = BuildComm(msgLog, meta);
        msgFile.setFileName(t + "_" + System.currentTimeMillis());
        if (meta.get("type").equals("6")) {
            msgFile.setFileName(meta.get("title").toString());
            msgFile.setExt("." + meta.get("ext").toString());
        }
        msgFile.setFileBlob(msgLog.getMsgDescribe());
        MsgFileInsert(msgFile, msgLog);
    }

    private void MsgFileInsert(MsgFile msgFile, MsgLog msgLog) {
        MsgFileImpl msgFileImpl = ReflectUtils.getBean(MsgFileImpl.class);
        msgFileImpl.insertSelective(msgFile);
        MsgLogImpl msgLogImpl = ReflectUtils.getBean(MsgLogImpl.class);
        msgLog.setContentId(msgFile.getId());
        msgLogImpl.insertSelective(msgLog);
    }

    private byte[] Base64ToByte(JSONObject Context, String field) {
        if (Context == null) {
            return null;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            return decoder.decodeBuffer(Context.getString(field));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
