package chs.wechat.spy.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlRegularUtils {
    public static String getCode(String source) {
        String pattern = "window.QRLogin.code = (\\d+); window.QRLogin.uuid = \"(\\S+?)\"";
        Pattern p = Pattern.compile(pattern);
        Matcher matcher = p.matcher(source);
        if (matcher.matches()) {
            return matcher.group(1);
        }
        return "";
    }

    public static String getUUID(String source) {
        String pattern = "window.QRLogin.code = (\\d+); window.QRLogin.uuid = \"(\\S+?)\"";
        Pattern p = Pattern.compile(pattern);
        Matcher matcher = p.matcher(source);
        if (matcher.matches()) {
            return matcher.group(2);
        }
        return "";
    }

    public static String getCodeCheck(String source) {
        String pattern = "window.code=(\\d+)";
        Pattern p = Pattern.compile(pattern);
        Matcher matcher = p.matcher(source);
        if (matcher.matches()) {
            return matcher.group(1);
        }
        return "";

    }
}
