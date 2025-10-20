package com.charles.netty.client;

import com.alibaba.fastjson.JSONObject;
import com.charles.netty.Constant.Constants;
import com.charles.netty.util.Response;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TcpClient {
    static final Bootstrap bootstrap = new Bootstrap();
    static ChannelFuture future = null;
    static{
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new NettyClientInitializer())
                    .option(ChannelOption.SO_KEEPALIVE, true);
            future = bootstrap.connect(Constants.HOST, Constants.PORT).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //notice: every requests all are same connect,this can case question of 并发
    //So,every request should have a unique id_code.
    public static Response send(ClientRequest request) throws Exception {
        future.channel().writeAndFlush(JSONObject.toJSONString(request));
        future.channel().writeAndFlush("\r\n");
        DefaultFuture df = new DefaultFuture(request);
        return df.get();
    }
}
