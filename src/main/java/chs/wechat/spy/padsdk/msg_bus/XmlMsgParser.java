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
            InputStream in = new ByteArrayInputStream(content.getBytes());
            Document doc = sax.read(in);
            node = doc.selectSingleNode(path);
            in.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        return node;
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
}
