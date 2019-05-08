package chs.wechat.spy.websocket;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.EmptyHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.concurrent.TimeUnit;

/**
 * @author Stephen Mallette (http://stephen.genoprime.com)
 */
public class WebSocketClient {
    private static WebSocketClient ourInstance;
    private int port = 0;
    private String host = "127.0.0.1";
    private static URI uri;
    private Channel ch;
    private static final EventLoopGroup group = new NioEventLoopGroup(4);
    private static final Logger logger = LoggerFactory.getLogger(WebSocketClient.class);
    private Bootstrap b;

    public static WebSocketClient getInstance() {
        if (ourInstance == null) {
            synchronized (WebSocketClient.class) {
                if (ourInstance == null) {
                    ourInstance = new WebSocketClient();
                }
            }
        }
        return ourInstance;
    }

    private WebSocketClient() {
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setUri(String uuid) {
        WebSocketClient.uri = URI.create("ws://" + host + ":" + port + "?action=scan&uuid=" + uuid + "&devicename=xzy-ipad&isreset=true");
    }

    public URI getUri() {
        return uri;
    }

    public void open() {
        try {
            b = new Bootstrap();
            String protocol = uri.getScheme();
            if (!"ws".equals(protocol)) {
                logger.error("Unsupported protocol: " + protocol);
                throw new IllegalArgumentException("Unsupported protocol: " + protocol);
            }
            // Connect with V13 (RFC 6455 aka HyBi-17). You can change it to V08 or V00.
            // If you change it to V00, ping is not supported and remember to change
            // HttpResponseDecoder to WebSocketHttpResponseDecoder in the pipeline.

            b.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) {
                    final WebSocketClientHandler handler = new WebSocketClientHandler(
                            WebSocketClientHandshakerFactory.newHandshaker(uri, WebSocketVersion.V13, null, false, EmptyHttpHeaders.INSTANCE, 67108864), WebSocketClient.this);
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast("http-codec", new HttpClientCodec());
                    pipeline.addLast("aggregator", new HttpObjectAggregator(67108864));
                    pipeline.addLast("ws-handler", handler);
                }
            });
            logger.info("WebSocket Client connecting");
            doConnect();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            group.shutdownGracefully();
        }
    }

    public void close() {
        // WebSocketClientHandler will close the connection when the server
        // responds to the CloseWebSocketFrame.
        try {
            logger.info("WebSocket Client sending close");
            if (ch != null) {
                ch.writeAndFlush(new CloseWebSocketFrame());
                ch.pipeline().close();
            }
            group.shutdownGracefully().await();
        } catch (Exception e) {
            logger.error(e.getMessage());
            group.shutdownGracefully();
            e.printStackTrace();
        }
    }

    public void commander(String text) {
        ch.writeAndFlush(new TextWebSocketFrame(text));
    }

    void doConnect() {
        if (ch != null && ch.isActive()) {
            return;
        }
        try {
            ChannelFuture future = b.connect(uri.getHost(), uri.getPort());
            future.addListener((ChannelFutureListener) futureListener -> {
                if (futureListener.isSuccess()) {
                    ch = futureListener.channel();
                    System.out.println("Connect to server successfully!");
                } else {
                    System.out.println("Failed to connect to server, try connect after 5s");
                    futureListener.channel().eventLoop().schedule(this::doConnect, 5, TimeUnit.SECONDS);
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
