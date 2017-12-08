package com.mobine.vnews;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;


/**
 * @author Create by xuantang
 * @date on 12/8/17
 */
public class ServerIniterHandler extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline =  ch.pipeline();
        pipeline.addLast("decode", new StringDecoder());
        pipeline.addLast("encode", new StringEncoder());
        pipeline.addLast("chat", new ChatServerHandler());

    }
}
