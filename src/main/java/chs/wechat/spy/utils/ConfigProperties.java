package chs.wechat.spy.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Properties;

public class ConfigProperties {

    public static JSONObject GetXmlConfig() {
        JSONObject jsonObject = null;
        try {
            String url = Objects.requireNonNull(ConfigProperties.class.getClassLoader().getResource("")).getPath();
            SAXReader sax = new SAXReader();// 创建一个SAXReader对象
            File xmlFile = new File(url + "sql-mapping.xml");// 根据指定的路径创建file对象
            Document doc = sax.read(xmlFile);// 获取document对象,如果文档无节点，则会抛出Exception提前结束
            jsonObject = JSONObject.parseObject(JSONUtil.xml2json(doc.asXML()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static JSONObject GetSearchClause(JSONObject conf) {
        JSONObject info = new JSONObject();
        JSONObject meta = conf.getJSONArray("SQL-META").getJSONObject(0);
        JSONObject root = conf.getJSONArray("SEARCH-CLAUSE").getJSONObject(0);
        JSONObject enums = conf.getJSONArray("ENUMS").getJSONObject(0);
        String[] clause_parts = root.getString("SEARCH").split(" ");
        StringBuilder sql = new StringBuilder();
        for (int i = 0; i < clause_parts.length; i++) {
            if (clause_parts[i].contains(":")) {
                clause_parts[i] = meta.getString(clause_parts[i].replaceAll("[:;\"\\\\]", ""));
            }
            sql.append(clause_parts[i]).append(" ");
        }
        JSONObject joClause = new JSONObject();
        JSONObject wheres = root.getJSONArray("WHERE-FOR").getJSONObject(0);
        String where_key = root.getString("WHERE-KEY");
        if (wheres.keySet().size() == 0) {
            joClause.put("only", sql.toString().trim().replace("#", "1=1"));
        }
        for (String where_t : wheres.keySet()) {
            joClause.put(where_t, sql.toString().trim().replace("#", wheres.getString(where_t)));
        }
        info.put("sql", joClause);
        info.put("enums", enums.getJSONArray("KEYS").getJSONObject(0));
        info.put("key_pattern", enums.getString("KEY_PATTERN"));
        info.put("meta", meta);
        info.put("where_key", where_key);
        return info;
    }

    public static void addLibraryDir(String libraryPath) throws IOException {
        try {
            Field field = ClassLoader.class.getDeclaredField("usr_paths");
            field.setAccessible(true);
            String[] paths = (String[]) field.get(null);
            for (String path : paths) {
                if (libraryPath.equals(path)) {
                    return;
                }
            }
            String[] tmp = new String[paths.length + 1];
            System.arraycopy(paths, 0, tmp, 0, paths.length);
            tmp[paths.length] = libraryPath;
            field.set(null, tmp);
        } catch (IllegalAccessException e) {
            throw new IOException(
                    "Failed to get permissions to set library path");
        } catch (NoSuchFieldException e) {
            throw new IOException(
                    "Failed to get field handle to set library path");
        }
    }

    public static Object GetConfig(String key) {
        String Path = ConfigProperties.class.getClassLoader().getResource("").getPath()
                + "config.json";

        try {
            String input = FileUtils.readFileToString(new File(Path), "UTF-8");
            JSONObject jsonObject = JSONObject.parseObject(input);
            if (jsonObject != null && jsonObject.containsKey(key)) {
                return jsonObject.get(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String GetProperties(String key) {
        Properties properties = new Properties();
        String res = "";
        // 使用ClassLoader加载properties配置文件生成对应的输入流
        InputStream in = ConfigProperties.class.getClassLoader().getResourceAsStream("config.properties");
        // 使用properties对象加载输入流
        try {
            properties.load(in);
            res = properties.getProperty(key);
            Objects.requireNonNull(in).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
        //获取key对应的value值
    }
}
