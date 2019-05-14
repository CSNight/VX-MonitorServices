package chs.wechat.spy.context;

import chs.wechat.spy.db.redis.RedisConnManager;
import chs.wechat.spy.utils.ConfigProperties;
import chs.wechat.spy.utils.DownloadPool;
import chs.wechat.spy.websocket.WebSocketClient;
import chs.wechat.spy.websocket.WebSocketServerSingleton;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

public class SpringContextEvent implements ApplicationListener {
    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof ApplicationStartedEvent) {
            RedisConnManager rcm = RedisConnManager.getInstance();
            rcm.setServerIp(ConfigProperties.GetProperties("status_redis_server.host"));
            rcm.setPort(Integer.parseInt(ConfigProperties.GetProperties("status_redis_server.port")));
            rcm.BuildJedisPool();
            String client_host = ConfigProperties.GetProperties("api_websocket_server.host");
            String client_port = ConfigProperties.GetProperties("api_websocket_server.port");
            WebSocketClient ws = WebSocketClient.getInstance();
            ws.setHost(client_host);
            ws.setPort(Integer.parseInt(client_port));
            String server_host = ConfigProperties.GetProperties("monitor_websocket_server.host");
            String server_port = ConfigProperties.GetProperties("monitor_websocket_server.port");
            WebSocketServerSingleton wss = WebSocketServerSingleton.getInstance();
            wss.setHost(server_host);
            wss.setPort(Integer.parseInt(server_port));
            try {
                wss.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
            DownloadPool downloadPool = DownloadPool.getInstance();
            downloadPool.StartDownloadPool();
            System.out.println("Start Complete!");
        } else if (applicationEvent instanceof ContextClosedEvent) {
            WebSocketServerSingleton.getInstance().shutdown();
            WebSocketClient.getInstance().close();
            RedisConnManager.getInstance().shutdown();
        }
    }
}
