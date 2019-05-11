package chs.wechat.spy.websocket;

import chs.wechat.spy.db.redis.RedisClientOperation;
import chs.wechat.spy.db.redis.RedisConnManager;
import chs.wechat.spy.utils.ConfigProperties;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;


public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {
    private WebSocketServerHandshaker handshaker;
    private ChannelPromise handshakeFuture;
    private static final Logger logger = LoggerFactory.getLogger(WebSocketServerHandler.class);
    private ChannelGroup channels;

    public WebSocketServerHandler(ChannelGroup channelGroup) {
        channels = channelGroup;
    }

    public ChannelFuture handshakeFuture() {
        return handshakeFuture;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object Frame) {
        if (Frame instanceof FullHttpRequest) {
            handleHttpRequest(ctx, (FullHttpRequest) Frame);
        } else if (Frame instanceof WebSocketFrame) {
            handleWebSocketFrame(ctx, (WebSocketFrame) Frame);
        }
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        channels.add(ctx.channel());
    }

    @Override
    public void channelInactive(final ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        System.out.println("Remote Client disconnected!");
        channels.remove(ctx.channel());
        RedisClientOperation rco = new RedisClientOperation();
        rco.setJedisClient(RedisConnManager.getInstance().getJedis(rco.getJedis_id()));
        rco.deleteKey(ConfigProperties.GetProperties("app_uid"));
        RedisConnManager.getInstance().close(rco.getJedis_id());
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
        // 如果HTTP解码失败，返回HTTP异常
        if (!req.decoderResult().isSuccess() || (!"websocket".equals(req.headers().get("Upgrade")))) {
            sendHttpResponse(ctx, req,
                    new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }//获取url后置参数
        HttpMethod method = req.method();
        String uri = req.uri();
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(uri);
        Map<String, List<String>> parameters = queryStringDecoder.parameters();
        // 构造握手响应返回，本机测试
        // Handshake
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
                getWebSocketLocation(req, uri), null, false);
        handshaker = wsFactory.newHandshaker(req);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            handshaker.handshake(ctx.channel(), req);
        }
    }

    @SuppressWarnings("deprecation")
    private String getWebSocketLocation(FullHttpRequest req, String uri) {
        return "ws://" + req.headers().get(HttpHeaders.Names.HOST) + uri;
    }

    private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, DefaultFullHttpResponse res) {
        // 返回应答给客户端
        if (res.status().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
        }
        // 如果是非Keep-Alive，关闭连接
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!HttpHeaders.isKeepAlive(req) || res.status().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        try {
            if (frame instanceof TextWebSocketFrame) {
                String clientMsg = ((TextWebSocketFrame) frame).text();
            } else if (frame instanceof BinaryWebSocketFrame) {
                logger.info("Binary msg received");
                ctx.channel().writeAndFlush(new PongWebSocketFrame(frame.isFinalFragment(), frame.rsv(), frame.copy().content()));
            } else if (frame instanceof PingWebSocketFrame) {
                logger.info("Ping msg received");
                ctx.channel().writeAndFlush(new PongWebSocketFrame(frame.isFinalFragment(), frame.rsv(), frame.copy().content()));
            } else if (frame instanceof CloseWebSocketFrame) {
                try {
                    ctx.channel().closeFuture().sync();
                } catch (InterruptedException e) {
                    logger.error(e.getMessage());
                    e.printStackTrace();
                }
                logger.info("Ping msg received");
            } else if (frame instanceof PongWebSocketFrame) {
                logger.info("Pong msg received");
                ctx.channel().writeAndFlush(new PingWebSocketFrame(frame.isFinalFragment(), frame.rsv(), frame.copy().content()));
            } else {
                logger.error(String.format("%s frame types not supported", frame.getClass().getName()));
                throw new UnsupportedOperationException(String.format("%s frame types not supported", frame.getClass().getName()));
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        channels.remove(ctx.channel());
        ctx.channel().close();
        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
    }

}
