package com.charles.netty.init;

import com.charles.netty.handler.ServerHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class NettyServerInitializer extends ChannelInitializer {

    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline().addLast(new DelimiterBasedFrameDecoder(65535, Delimiters.lineDelimiter()[0]));
        channel.pipeline().addLast(new IdleStateHandler(60,45,20, TimeUnit.SECONDS));
        channel.pipeline().addLast(new StringDecoder());
        channel.pipeline().addLast(new ServerHandler());
        channel.pipeline().addLast(new StringEncoder());

    }
}
