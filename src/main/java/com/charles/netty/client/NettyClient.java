package com.charles.netty.client;

import com.charles.netty.Constant.Constants;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

public class NettyClient {
    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new NettyClientInitializer())
                    .option(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = bootstrap.connect(Constants.HOST, Constants.PORT).sync();
            future.channel().writeAndFlush("hello server");
            future.channel().writeAndFlush("\r\n");
            future.channel().closeFuture().sync();
            Object result = future.channel().attr(Constants.CLIENT_KEY).get();
            System.out.println("get information from Server === "+ result.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            workerGroup.shutdownGracefully();
        }
    }
}
