package chs.wechat.spy.websdk.core;


import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequestBus {
    private static HttpRequestBus ourInstance;
    private CookieStore cookieStore;
    private Map<String, String> cookieDict = new HashMap<>();

    private HttpRequestBus() {
    }

    public static HttpRequestBus getInstance() {
        if (ourInstance == null) {
            synchronized (HttpRequestBus.class) {
                if (ourInstance == null) {
                    ourInstance = new HttpRequestBus();
                }
            }
        }
        return ourInstance;
    }

    public Map<String, String> getCookieDict() {
        return cookieDict;
    }

    public void setCookieDict(Map<String, String> cookieDict) {
        this.cookieDict = cookieDict;
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public String sendGet(String url, Map<String, Object> param, boolean save_cookies, Map<String, String> headers) {
        StringBuilder result = new StringBuilder();
        DefaultHttpClient httpClient = new DefaultHttpClient();
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            for (String key : param.keySet()) {
                uriBuilder.setParameter(key, param.get(key).toString());
            }
            URI urls = uriBuilder.build();
            HttpGet httpGet = new HttpGet(urls);
            if (headers != null) {
                for (String key : headers.keySet()) {
                    uriBuilder.setParameter(key, headers.get(key));
                }
            }
            InputStream inputStream = null;
            CloseableHttpResponse httpResponse = null;
            try {
                httpResponse = httpClient.execute(httpGet);
                if (save_cookies) {
                    cookieStore = httpClient.getCookieStore();
                    List<Cookie> cookies = cookieStore.getCookies();
                    for (Cookie cookie : cookies) {
                        cookieDict.put(cookie.getName(), cookie.getValue());
                    }
                }
                HttpEntity entity = httpResponse.getEntity();  //获取响应的实体
                inputStream = entity.getContent();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                //关闭InputStream和response
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (httpResponse != null) {
                    try {
                        httpResponse.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                httpClient.close();
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public String sendPost(String url, Map<String, Object> param, boolean save_cookies, Map<String, String> headers) {
        StringBuilder result = new StringBuilder();
        DefaultHttpClient httpClient = new DefaultHttpClient();
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            for (String key : param.keySet()) {
                uriBuilder.setParameter(key, param.get(key).toString());
            }
            URI urls = uriBuilder.build();
            HttpPost httpPost = new HttpPost(urls);
            if (headers != null) {
                for (String key : headers.keySet()) {
                    uriBuilder.setParameter(key, headers.get(key));
                }
            }
            InputStream inputStream = null;
            CloseableHttpResponse httpResponse = null;
            try {
                httpResponse = httpClient.execute(httpPost);
                if (save_cookies) {
                    cookieStore = httpClient.getCookieStore();
                    List<Cookie> cookies = cookieStore.getCookies();
                    for (Cookie cookie : cookies) {
                        cookieDict.put(cookie.getName(), cookie.getValue());
                    }
                }
                HttpEntity entity = httpResponse.getEntity();  //获取响应的实体
                inputStream = entity.getContent();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                //关闭InputStream和response
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (httpResponse != null) {
                    try {
                        httpResponse.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                httpClient.close();
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
