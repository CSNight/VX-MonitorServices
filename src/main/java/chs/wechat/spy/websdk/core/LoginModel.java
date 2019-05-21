package chs.wechat.spy.websdk.core;

import chs.wechat.spy.utils.ConfigProperties;
import chs.wechat.spy.utils.UrlRegularUtils;
import com.alibaba.fastjson.JSONObject;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class LoginModel {
    private static LoginModel ourInstance;
    private String uuid;

    private LoginModel() {
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

    public String push_login() {
        Map<String, String> cookiesDict = HttpRequestBus.getInstance().getCookieDict();
        if (cookiesDict.containsKey("wxuin")) {
            String url = String.format("%s/cgi-bin/mmwebwx-bin/webwxpushloginurl?uin=%s",
                    ConfigProperties.GetProperties("BASE_URL"), cookiesDict.get("wxuin"));
            Map<String, String> headers = new HashMap<>();
            headers.put("User-Agent", ConfigProperties.GetProperties("USER_AGENT"));
            String res = HttpRequestBus.getInstance().sendGet(url, new HashMap<>(), true, headers);
            JSONObject jo = JSONObject.parseObject(res);
            ConfigProperties.SetProperties("app_uid", jo.getString("uuid"));
        }
        return "false";
    }

    public String get_QRuuid() {
        String url = String.format("%s/jslogin", ConfigProperties.GetProperties("BASE_URL"));
        Map<String, String> headers = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        params.put("appid", "wx782c26e4c19acffb");
        params.put("fun", "new");
        headers.put("User-Agent", ConfigProperties.GetProperties("USER_AGENT"));
        String res = HttpRequestBus.getInstance().sendGet(url, params, true, headers);
        String code = UrlRegularUtils.getCode(res);
        String uid = UrlRegularUtils.getUUID(res);
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
}
