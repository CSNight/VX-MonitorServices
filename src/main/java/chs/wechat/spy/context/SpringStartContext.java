package chs.wechat.spy.context;

import chs.wechat.spy.utils.ConfigProperties;
import chs.wechat.spy.websocket.WebSocketClient;
import chs.wechat.spy.websocket.WebSocketServerSingleton;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

public class SpringStartContext implements ApplicationListener<ApplicationStartingEvent> {
    @Override
    public void onApplicationEvent(ApplicationStartingEvent springStartContext) {
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
    }
}
