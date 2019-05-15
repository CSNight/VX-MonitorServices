package chs.wechat.spy.padsdk.msg_bus;

import chs.wechat.spy.db.mybatis.model.MsgFile;
import chs.wechat.spy.db.mybatis.model.MsgLog;
import chs.wechat.spy.padsdk.api_request.MsgRequest;
import chs.wechat.spy.utils.ConfigProperties;
import chs.wechat.spy.utils.GUID;
import chs.wechat.spy.utils.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import sun.misc.BASE64Decoder;

import java.io.FileOutputStream;
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

    public MsgFile imgFile(MsgLog msgLog, Map<String, Object> meta, String msg) {
        String uuid = ConfigProperties.GetProperties("app_uid");
        MsgFile msgFile = BuildComm(msgLog, meta);
        msgFile.setFileName("image" + System.currentTimeMillis());
        try {
            JSONObject res = JSONObject.parseObject(mr.GetImg((Map<String, Object>) JSONObject.parseObject(msg, Map.class), uuid));
            if (res.getString("Success").equals("true")) {
                msgFile.setFileBlob(res.getString("Context").getBytes(Charset.forName("utf-8")));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        msgFile.setExt(".png");
        return msgFile;
    }

    public MsgFile voiceFile(MsgLog msgLog, Map<String, Object> meta, String msg) {
        String uuid = ConfigProperties.GetProperties("app_uid");
        MsgFile msgFile = BuildComm(msgLog, meta);
        msgFile.setFileName("voice" + System.currentTimeMillis());
        try {
            JSONObject res = JSONObject.parseObject(mr.GetVoice((Map<String, Object>) JSONObject.parseObject(msg, Map.class), uuid));
            if (res.getString("Success").equals("true")) {
                msgFile.setFileBlob(res.getString("Context").getBytes(Charset.forName("utf-8")));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        msgFile.setExt(".mp3");
        return msgFile;
    }

    public MsgFile videoFile(MsgLog msgLog, Map<String, Object> meta, String msg) {
        String uuid = ConfigProperties.GetProperties("app_uid");
        MsgFile msgFile = BuildComm(msgLog, meta);
        msgFile.setFileName("video" + System.currentTimeMillis());
        try {
            JSONObject res = JSONObject.parseObject(mr.GetVideo((Map<String, Object>) JSONObject.parseObject(msg, Map.class), uuid));
            if (res.getString("Success").equals("true")) {
                msgFile.setFileBlob(res.getString("Context").getBytes(Charset.forName("utf-8")));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        msgFile.setExt(".mp4");
        return msgFile;
    }

    public MsgFile redPack(MsgLog msgLog, Map<String, Object> meta, String msg) {
        String uuid = ConfigProperties.GetProperties("app_uid");
        MsgFile msgFile = BuildComm(msgLog, meta);
        msgFile.setFileName("redPack" + System.currentTimeMillis());
        try {
            JSONObject res = JSONObject.parseObject(mr.GetRedPack((Map<String, Object>) JSONObject.parseObject(msg, Map.class), uuid));
            if (res.getString("Success").equals("true")) {
                msgFile.setFileBlob(res.getString("Context").getBytes(Charset.forName("utf-8")));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        msgFile.setExt("");
        return msgFile;
    }

    public static void main(String[] args) {
        MsgRequest mr = new MsgRequest();
        String s = "{\"content\":\"<?xml version=\\\"1.0\\\"?>\\n<msg>\\n\\t<img aeskey=\\\"d38328e6bbb8c6ddcb3e41bc8ee3fa1f\\\" encryver=\\\"1\\\" cdnthumbaeskey=\\\"d38328e6bbb8c6ddcb3e41bc8ee3fa1f\\\" cdnthumburl=\\\"3053020100044c304a0201000204dd1ba52202033d0af70204208e1e6f02045cdb841b0425617570696d675f323238336338333761333535373735645f313535373839303037343634300204010438010201000400\\\" cdnthumblength=\\\"3855\\\" cdnthumbheight=\\\"67\\\" cdnthumbwidth=\\\"144\\\" cdnmidheight=\\\"0\\\" cdnmidwidth=\\\"0\\\" cdnhdheight=\\\"0\\\" cdnhdwidth=\\\"0\\\" cdnmidimgurl=\\\"3053020100044c304a0201000204dd1ba52202033d0af70204208e1e6f02045cdb841b0425617570696d675f323238336338333761333535373735645f313535373839303037343634300204010438010201000400\\\" length=\\\"3855\\\" cdnbigimgurl=\\\"3053020100044c304a0201000204dd1ba52202033d0af70204208e1e6f02045cdb841b0425617570696d675f323238336338333761333535373735645f313535373839303037343634300204010438010201000400\\\" hdlength=\\\"577328\\\" md5=\\\"2d0b7be815522c4f5aaed003214a4619\\\" />\\n</msg>\\n\",\"continue\":0,\"description\":\"\",\"from_user\":\"chensi199100\",\"msg_id\":\"2682852943451080697\",\"msg_source\":\"<msgsource />\\n\",\"msg_type\":5,\"status\":1,\"sub_type\":3,\"timestamp\":1557913413,\"to_user\":\"filehelper\",\"uin\":1207650860,\"mywxid\":\"chensi199100\"}";
        String ss = mr.GetImg((Map<String, Object>) JSONObject.parseObject(s, Map.class), ConfigProperties.GetProperties("app_uid"));
        BASE64Decoder D=new BASE64Decoder();
        byte[] sss = new byte[0];
        try {
            sss = D.decodeBuffer(JSONObject.parseObject(ss).getJSONObject("Context").getString("image"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileOutputStream fs = new FileOutputStream("f:\\a.jpg");
            fs.write(sss);
            fs.flush();
            fs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
