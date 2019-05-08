package chs.wechat.spy.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

public class WebSocketInitializer extends ChannelInitializer<SocketChannel> {
    private ChannelGroup channels;

    public WebSocketInitializer(ChannelGroup channelGroup) {
        channels = channelGroup;
    }

    @Override
    public void initChannel(final SocketChannel ch) throws Exception {
        final ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("http-request-decoder", new HttpRequestDecoder());
        pipeline.addLast("aggregator", new HttpObjectAggregator(67108864));
        pipeline.addLast("http-response-encoder", new HttpResponseEncoder());
        pipeline.addLast("request-handler", new WebSocketServerProtocolHandler("/websocket"));
        pipeline.addLast("handler", new WebSocketServerHandler(channels));
    }
}
