package chs.wechat.spy.padsdk.msg_bus;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class XmlMsgParser {
    private Node getNode(String content, String path) {
        Node node = null;
        try {
            SAXReader sax = new SAXReader();// 创建一个SAXReader对象
            InputStream in = new ByteArrayInputStream(trimMsg(content).getBytes());
            Document doc = sax.read(in);
            node = doc.selectSingleNode(path);
            in.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        return node;
    }

    private String trimMsg(String source) {
        return source.replaceAll("\\\\t|\\\\r|\\\\n", "");
    }

    public Map<String, Object> ImageParser(String content) {
        Map<String, Object> properties = new HashMap<>();
        Node imgNode = getNode(content, "/msg/img");
        properties.put("size", imgNode.valueOf("@length"));
        properties.put("md5", imgNode.valueOf("@md5"));
        return properties;
    }

    public Map<String, Object> VideoParser(String content) {
        Map<String, Object> properties = new HashMap<>();
        Node videoNode = getNode(content, "/msg/videomsg");
        properties.put("size", videoNode.valueOf("@length"));
        properties.put("md5", videoNode.valueOf("@md5"));
        properties.put("playlength", videoNode.valueOf("@md5"));
        return properties;
    }

    public Map<String, Object> VoiceParser(String content) {
        Map<String, Object> properties = new HashMap<>();
        Node voiceNode = getNode(content, "/msg/voicemsg");
        properties.put("size", voiceNode.valueOf("@length"));
        properties.put("time", voiceNode.valueOf("@voicelength"));
        properties.put("format", voiceNode.valueOf("@voiceformat"));
        return properties;
    }

    public Map<String, Object> PosParser(String content) {
        Map<String, Object> properties = new HashMap<>();
        Node poiNode = getNode(content, "/msg/location");
        properties.put("x", poiNode.valueOf("@x"));
        properties.put("y", poiNode.valueOf("@y"));
        properties.put("label", poiNode.valueOf("@label"));
        properties.put("scale", poiNode.valueOf("@scale"));
        properties.put("poiname", poiNode.valueOf("@poiname"));
        return properties;
    }

    public Map<String, Object> emojiParser(String content) {
        Map<String, Object> properties = new HashMap<>();
        Node poiNode = getNode(content, "/msg/emoji");
        properties.put("cdnurl", poiNode.valueOf("@cdnurl"));
        properties.put("type", poiNode.valueOf("@type"));//1 保存 2下载包
        properties.put("md5", poiNode.valueOf("@md5"));
        properties.put("size", poiNode.valueOf("@len"));
        return properties;
    }

    public Map<String, Object> CardParser(String content) {
        Map<String, Object> properties = new HashMap<>();
        Node cardNode = getNode(content, "/msg");
        properties.put("username", cardNode.valueOf("@username"));
        properties.put("nickname", cardNode.valueOf("@nickname"));
        properties.put("alias", cardNode.valueOf("@alias"));
        return properties;
    }

    public Map<String, Object> AppDispatch(String content) {
        Node appNode = getNode(content, "/msg/appmsg");
        String typeT = appNode.selectSingleNode("type").getText();
        switch (typeT) {
            default:
            case "5":
            case "33":
                return appParser(content);
            case "6":
                return FileParser(content);
            case "2001":
                return redParser(content);
        }
    }

    public Map<String, Object> FileParser(String content) {
        Map<String, Object> properties = new HashMap<>();
        Node fileNode = getNode(content, "/msg/appmsg");
        properties.put("title", fileNode.selectSingleNode("title").getText());
        properties.put("size", fileNode.selectSingleNode("des").getText());
        properties.put("type", fileNode.selectSingleNode("type").getText());
        properties.put("len", fileNode.selectSingleNode("appattach/totallen").getText());
        properties.put("ext", fileNode.selectSingleNode("appattach/fileext").getText());
        return properties;
    }

    public Map<String, Object> appParser(String content) {
        Map<String, Object> properties = new HashMap<>();
        Node appNode = getNode(content, "/msg/appmsg");
        properties.put("title", appNode.selectSingleNode("title").getText());
        properties.put("size", appNode.selectSingleNode("des").getText());
        properties.put("url", appNode.selectSingleNode("url").getText());
        properties.put("type", appNode.selectSingleNode("type").getText());
        if (properties.get("type").equals("5")) {
            properties.put("linkType", "link");
        } else if (properties.get("type").equals("33")) {
            properties.put("linkType", "app");
        } else {
            properties.put("linkType", "unknown");
        }

        properties.put("thumburl", appNode.selectSingleNode("thumburl").getText());
        return properties;
    }

    public Map<String, Object> mailParser(String content) {
        Map<String, Object> properties = new HashMap<>();
        Node mailNode = getNode(content, "/msg/pushmail/content");
        properties.put("sender", mailNode.selectSingleNode("sender").getText());
        properties.put("date", mailNode.selectSingleNode("date").getText());
        properties.put("from", mailNode.selectSingleNode("fromlist/item/addr").getText());
        return properties;
    }

    public Map<String, Object> redParser(String content) {
        Map<String, Object> properties = new HashMap<>();
        Node redNode = getNode(content, "/msg/appmsg");
        properties.put("title", redNode.selectSingleNode("title").getText());
        properties.put("des", redNode.selectSingleNode("des").getText());
        properties.put("type", redNode.selectSingleNode("type").getText());
        properties.put("text", redNode.selectSingleNode("wcpayinfo/receivertitle").getText());
        properties.put("url", redNode.selectSingleNode("url").getText());
        properties.put("thumburl", redNode.selectSingleNode("thumburl").getText());
        return properties;
    }

    public Map<String, Object> CallParser(String content) {
        Map<String, Object> properties = new HashMap<>();
        Node voiceCall = getNode(content, "/voipinvitemsg");
        String invite_t = voiceCall.selectSingleNode("invitetype").getText();
        String status = voiceCall.selectSingleNode("status").getText();
        switch (invite_t) {
            case "0":
                properties.put("invite_type", "video_call");
                break;
            case "1":
                properties.put("invite_type", "voice_call");
                break;
        }
        switch (status) {
            case "1":
                properties.put("status", "call_in");
                break;
            case "2":
                properties.put("status", "cancel_in");
                break;
        }
        properties.put("room_id", voiceCall.selectSingleNode("roomid").getText());
        properties.put("key", voiceCall.selectSingleNode("key").getText());
        return properties;
    }

    public Map<String, Object> sysParser(String content) {
        Map<String, Object> properties = new HashMap<>();
        Node sys_msg = getNode(content, "/sysmsg");
        String msg_t = sys_msg.valueOf("@type");
        switch (msg_t) {
            case "revokemsg":
                properties.put("type", "revokemsg");
                properties.put("revoke_from", sys_msg.selectSingleNode("revokemsg/session").getText());
                properties.put("revoke_msgid", sys_msg.selectSingleNode("revokemsg/newmsgid").getText());
                properties.put("msg_scope", sys_msg.selectSingleNode("revokemsg/msgid").getText());
                properties.put("replace_by", sys_msg.selectSingleNode("revokemsg/replacemsg").getText());
                break;
            case "functionmsg":
                properties.put("type", "functionmsg");
                properties.put("cgi", sys_msg.selectSingleNode("functionmsg/cgi").getText());
                properties.put("cmdid", sys_msg.selectSingleNode("functionmsg/cmdid").getText());
                properties.put("functionmsgid", sys_msg.selectSingleNode("functionmsg/functionmsgid").getText());
                properties.put("custombuff", sys_msg.selectSingleNode("functionmsg/custombuff").getText());
                properties.put("businessid", sys_msg.selectSingleNode("functionmsg/businessid").getText());
                properties.put("actiontime", sys_msg.selectSingleNode("functionmsg/actiontime").getText());
                break;
            case "dynacfg":
                properties.put("type", "dynacfg");
                break;
            case "ClientCheckGetExtInfo":
                properties.put("type", "client_check");
                break;
        }
        return properties;
    }
}
