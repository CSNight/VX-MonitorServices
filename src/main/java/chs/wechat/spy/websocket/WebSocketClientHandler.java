package chs.wechat.spy.websocket;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.*;
import io.netty.handler.codec.http.EmptyHttpHeaders;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author Stephen Mallette (http://stephen.genoprime.com)
 */
public class WebSocketClientHandler extends SimpleChannelInboundHandler<Object> {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketClientHandler.class);
    private WebSocketClientHandshaker hand_shaker;
    private ChannelPromise handshakeFuture;
    private WebSocketClient client;

    public WebSocketClientHandler(final WebSocketClientHandshaker handshaker, WebSocketClient clientInstance) {
        this.hand_shaker = handshaker;
        this.client = clientInstance;
    }

    public ChannelFuture handshakeFuture() {
        return handshakeFuture;
    }

    @Override
    public void handlerAdded(final ChannelHandlerContext ctx) throws Exception {
        handshakeFuture = ctx.newPromise();
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        hand_shaker.handshake(ctx.channel());
    }

    @Override
    public void channelInactive(final ChannelHandlerContext ctx) throws Exception {
        System.out.println("WebSocket Client disconnected!");
        this.hand_shaker = WebSocketClientHandshakerFactory.newHandshaker(
                client.getUri(), WebSocketVersion.V13, null, false, EmptyHttpHeaders.INSTANCE, 67108864);
        final EventLoop eventLoop = ctx.channel().eventLoop();
        eventLoop.schedule(() -> client.doConnect(), 5, TimeUnit.SECONDS);
        super.channelInactive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        final Channel ch = ctx.channel();
        if (!hand_shaker.isHandshakeComplete()) {
            // web socket client connected
            hand_shaker.finishHandshake(ch, (FullHttpResponse) msg);
            handshakeFuture.setSuccess();
            return;
        }
        if (msg instanceof FullHttpResponse) {
            final FullHttpResponse response = (FullHttpResponse) msg;
            logger.error("Unexpected FullHttpResponse (getStatus=" + response.status() + ", content="
                    + response.content().toString(CharsetUtil.UTF_8) + ')');
            throw new Exception("Unexpected FullHttpResponse (getStatus=" + response.status() + ", content="
                    + response.content().toString(CharsetUtil.UTF_8) + ')');
        }

        final WebSocketFrame frame = (WebSocketFrame) msg;
        if (frame instanceof TextWebSocketFrame) {
            TextWebSocketFrame textFrame = (TextWebSocketFrame) frame;
            try {
                JSONObject evt = JSONObject.parseObject(textFrame.text());
                switch (evt.getString("action")) {
                    case "log"://log信息
                        break;
                    case "qrcode"://返回二维码数据
                        WebSocketServerSingleton wss = WebSocketServerSingleton.getInstance();
                        wss.sendAll("data:image/jpeg;base64," + evt.getString("context"));
                        break;
                    case "getcontact"://获取联系人信息。会多次传输
                        break;
                    case "getgroup"://获取群组信息。会多次传输
                        break;
                    case "getgzh"://获取公众号信息。会多次传输
                        break;
                    case "msgcallback"://微信消息回调事件
                        break;
                }

            } catch (Exception ex) {
                logger.error(ex.getMessage());
            }
            logger.info(textFrame.text());
        } else if (frame instanceof PongWebSocketFrame) {
            PongWebSocketFrame pongFrame = (PongWebSocketFrame) frame;
            ch.writeAndFlush(pongFrame.content().retain());
        } else if (frame instanceof CloseWebSocketFrame) {
            ch.close();
        } else if (frame instanceof BinaryWebSocketFrame) {
            // uncomment to print request
            logger.info(frame.content().toString());
        }

    }

    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) throws Exception {
        cause.printStackTrace();
        if (!handshakeFuture.isDone()) {
            handshakeFuture.setFailure(cause);
        }
        ctx.close();
    }
}