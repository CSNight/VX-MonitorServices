package chs.wechat.spy.websdk.core;

import chs.wechat.spy.db.redis.RedisClientOperation;
import chs.wechat.spy.db.redis.RedisConnManager;
import chs.wechat.spy.utils.ConfigProperties;
import chs.wechat.spy.utils.JSONUtil;
import chs.wechat.spy.utils.ServerResponse;
import chs.wechat.spy.utils.UrlRegularUtils;
import chs.wechat.spy.websocket.WebSocketServerSingleton;
import com.alibaba.fastjson.JSONObject;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class LoginModel {
    private static LoginModel ourInstance;
    private String uuid;
    private Map<String, String> headers = new HashMap<>();
    private RedisClientOperation rco = new RedisClientOperation();
    private boolean isAlive = false;
    private boolean isLogin = false;
    private Map<String, Object> meta = new HashMap<>();
    private Map<String, String[]> url_list = new HashMap<>();

    private LoginModel() {
        headers.put("User-Agent", ConfigProperties.GetProperties("USER_AGENT"));
        url_list.put("wx2.qq.com", new String[]{"file.wx2.qq.com", "webpush.wx2.qq.com"});
        url_list.put("wx8.qq.com", new String[]{"file.wx8.qq.com", "webpush.wx8.qq.com"});
        url_list.put("qq.com", new String[]{"file.wx.qq.com", "webpush.wx.qq.com"});
        url_list.put("web2.wechat.com", new String[]{"file.web2.wechat.com", "webpush.web2.wechat.com"});
        url_list.put("wechat.com", new String[]{"file.web.wechat.com", "webpush.web.wechat.com"});
    }

    public static LoginModel getInstance() {
        if (ourInstance == null) {
            synchronized (LoginModel.class) {
                if (ourInstance == null) {
                    ourInstance = new LoginModel();
                }
            }
        }
        return ourInstance;
    }

    public String login(String app_uid) {
        if (isAlive || isLogin) {
            return "";
        }
        RedisConnManager rcm = RedisConnManager.getInstance();
        rco.setJedisClient(rcm.getJedis(rco.getJedis_id()));
        isLogin = true;
        while (isLogin) {
            String uuid = push_login(app_uid);
            if (!uuid.equals("false")) {
                rco.setHashField(app_uid, "login_status", "HAVE_HIS");
            } else {
                rco.setHashField(app_uid, "current_opt", "GET_QRCODE");
                while (get_QRuuid().equals("")) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                rco.setHashField(app_uid, "web_uid", uuid);
                ConfigProperties.SetProperties("web_uid", uuid);
                rco.setHashField(app_uid, "current_opt", "DOWNLOAD_QRCODE");
                rco.setHashField(app_uid, "login_status", "WAIT_SCAN");
                String base64QR = getQR();
                ServerResponse sr = new ServerResponse();
                sr.setSuccess(1);
                if (!base64QR.equals("")) {
                    sr.setSuccess(0);
                }
                sr.setType("QR_CODE");
                sr.setResponse("data:image/jpeg;base64," + base64QR);
                WebSocketServerSingleton wss = WebSocketServerSingleton.getInstance();
                wss.sendAll(JSONUtil.pojo2json(sr));
            }
            boolean isLoginComplete = false;
            while (!isLoginComplete) {
                String status = check_login();
                if (status.equals("200")) {
                    isLoginComplete = true;
                } else if (status.equals("201")) {
                    rco.setHashField(app_uid, "login_status", "WAIT_CONFIRM");
                    rco.setHashField(app_uid, "current_opt", "CONFIRM");
                } else if (!status.equals("408")) {
                    break;
                }
            }
            if (isLoginComplete) {
                rco.setHashField(app_uid, "login_status", "LOGIN_SUCCESS");
                rco.setHashField(app_uid, "cookies", JSONUtil.map2json(HttpRequestBus.getInstance().getCookieDict()));
                break;
            } else if (isLogin) {
                rco.setHashField(app_uid, "login_status", "TIME_OUT");
                break;
            }
        }
        isLogin = false;
        rcm.close(rco.getJedis_id());
        web_init();
        return "success";
    }

    public String push_login(String app_uid) {
        String jo_cookies = rco.getHashField(app_uid, "cookies");
        if (jo_cookies.equals("")) {
            return "false";
        }
        Map<String, String> cookiesDict = JSONUtil.json2Map(jo_cookies);
        if (cookiesDict.containsKey("wxuin")) {
            String url = String.format("%s/cgi-bin/mmwebwx-bin/webwxpushloginurl?uin=%s",
                    ConfigProperties.GetProperties("BASE_URL"), cookiesDict.get("wxuin"));
            String res = HttpRequestBus.getInstance().sendGet(url, new HashMap<>(), true, headers);
            JSONObject jo = JSONObject.parseObject(res);
            ConfigProperties.SetProperties("web_uid", jo.getString("uuid"));
            return jo.getString("uuid");
        }
        return "false";
    }

    public String check_login() {
        String url = String.format("%s/cgi-bin/mmwebwx-bin/login", ConfigProperties.GetProperties("BASE_URL"));
        long localTime = Long.parseLong(String.valueOf(System.currentTimeMillis()).substring(0, 10));
        Map<String, Object> params = new HashMap<>();
        params.put("loginicon", true);
        params.put("uuid", uuid);
        params.put("tip", 1);
        params.put("r", (-localTime / 1579));
        params.put("_", localTime);
        String res = HttpRequestBus.getInstance().sendGet(url, params, false, headers);
        String code = UrlRegularUtils.getCodeCheck(res);
        if (code.equals("200")) {
            if (process_login(res)) {
                return code;
            }
        } else if (code.equals("400")) {
            return code;
        } else if (!code.equals("")) {
            return code;
        }
        return "400";
    }

    public boolean process_login(String content) {
        String url = UrlRegularUtils.getRedictUrl(content);
        String res = HttpRequestBus.getInstance().sendGet(url, new HashMap<>(), true, headers);
        String parsed_url = url.substring(0, url.lastIndexOf("/"));
        meta.put("url", parsed_url);
        meta.put("fileUrl", url);
        meta.put("syncUrl", url);
        for (String indexUrl : url_list.keySet()) {
            String file_url = String.format("https://%s/cgi-bin/mmwebwx-bin", url_list.get(indexUrl)[0]);
            String syncUrl = String.format("https://%s/cgi-bin/mmwebwx-bin", url_list.get(indexUrl)[1]);
            if (parsed_url.contains(indexUrl)) {
                meta.put("fileUrl", file_url);
                meta.put("syncUrl", syncUrl);
                break;
            }
        }
        meta.put("deviceid", "e" + String.valueOf(Math.random()).substring(2, 17));
        meta.putAll(JSONUtil.json2Map(xmlTojson(res, "/error").toJSONString()));
        Map<String, String> resq = new HashMap<>();
        resq.put("Skey", meta.get("skey").toString());
        resq.put("Sid", meta.get("wxsid").toString());
        resq.put("Uin", meta.get("wxuin").toString());
        resq.put("DeviceID", meta.get("pass_ticket").toString());
        Map<String, Object> baseReq = new HashMap<>();
        baseReq.put("BaseRequest", resq);
        meta.put("BaseRequest", baseReq);
        return meta.containsKey("skey") && meta.containsKey("wxsid") && meta.containsKey("wxuin") && meta.containsKey("pass_ticket");
    }

    public String get_QRuuid() {
        String url = String.format("%s/jslogin", ConfigProperties.GetProperties("BASE_URL"));
        Map<String, Object> params = new HashMap<>();
        params.put("appid", "wx782c26e4c19acffb");
        params.put("fun", "new");
        String res = HttpRequestBus.getInstance().sendGet(url, params, true, headers);
        String code = UrlRegularUtils.getCode(res);
        String uid = UrlRegularUtils.getUUID(res);
        System.out.println(JSONUtil.map2json(HttpRequestBus.getInstance().getCookieDict()));
        if (code.equals("200") && !uid.equals("")) {
            uuid = uid;
        }
        return uid;
    }

    public String getQR() {
        return GenQR(String.format("%s/l/", ConfigProperties.GetProperties("BASE_URL")) + uuid);
    }

    private String GenQR(String url) {
        QRCode qrCode = QRCode.from(url);
        // 设置字符集，支持中文
        qrCode.withCharset("utf-8");
        // 设置生成的二维码图片大小
        qrCode.withSize(260, 260);
        ByteArrayOutputStream out = qrCode.to(ImageType.PNG).stream();
        BASE64Encoder base64Encoder = new BASE64Encoder();
        return base64Encoder.encode(out.toByteArray());
    }

    public void web_init() {
        String url = String.format("%s/webwxinit", meta.get("url").toString());
        Map<String, Object> params = new HashMap<>();
        long localTime = Long.parseLong(String.valueOf(System.currentTimeMillis()).substring(0, 10));
        params.put("r", (-localTime / 1579));
        params.put("pass_ticket", meta.get("pass_ticket"));
        headers.put("ContentType", "application/json; charset=UTF-8");
        String res = HttpRequestBus.getInstance().sendPost(url, params, true, headers, (Map<String, Object>) meta.get("BaseRequest"));
        System.out.println(res);
        if (!res.equals("")) {
            JSONObject jo_res = JSONObject.parseObject(res);
            meta.put("InviteStartCount", jo_res.getIntValue("InviteStartCount"));
            meta.put("User", jo_res.getIntValue("InviteStartCount"));
            meta.put("InviteStartCount", jo_res.getIntValue("InviteStartCount"));
            meta.put("InviteStartCount", jo_res.getIntValue("InviteStartCount"));
        }
    }

    private JSONObject xmlTojson(String xml, String path) {
        JSONObject jo = new JSONObject();
        SAXReader sax = new SAXReader();// 创建一个SAXReader对象
        InputStream in = new ByteArrayInputStream(xml.getBytes());
        Document doc = null;
        try {
            doc = sax.read(in);
            Node node = doc.selectSingleNode(path);
            jo.put("skey", node.selectSingleNode("skey").getText());
            jo.put("wxsid", node.selectSingleNode("wxsid").getText());
            jo.put("wxuin", node.selectSingleNode("wxuin").getText());
            jo.put("pass_ticket", node.selectSingleNode("pass_ticket").getText());
            in.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        return jo;
    }
}
