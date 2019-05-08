package chs.wechat.spy.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.ImmediateEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebSocketServer {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);
    private final EventLoopGroup workerGroup = new NioEventLoopGroup(2);
    private final ChannelGroup channelGroup = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
    private Channel chs;
    private String host;
    private int port;

    public WebSocketServer(String HOST, int PORT) {
        this.host = HOST;
        this.port = PORT;
    }

    WebSocketServer() {
    }

    public void run() throws Exception {
        try {
            final ServerBootstrap b = new ServerBootstrap();
            b.group(workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new WebSocketInitializer(channelGroup));
            chs = b.bind(host, port).sync().channel();
            logger.info("WebSocketServer has started");
            chs.closeFuture().sync().addListener(ChannelFutureListener.CLOSE);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    public void sendAll(String text) {
        for (Channel ch : channelGroup) {
            if (ch != null && ch.isOpen()) {
                ch.writeAndFlush(new TextWebSocketFrame(text));
            }
        }
    }

    public void send(String text, String id) {
        for (Channel ch : channelGroup) {
            if (ch != null && ch.isOpen() && ch.id().toString().equals(id)) {
                ch.writeAndFlush(new TextWebSocketFrame(text));
            }
        }
    }

    public void shutdown() {
        try {
            channelGroup.writeAndFlush(new CloseWebSocketFrame());
            chs.closeFuture();
            channelGroup.close();
            logger.info("WebSocketServer has stopped");
            workerGroup.shutdownGracefully().await();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }
}
