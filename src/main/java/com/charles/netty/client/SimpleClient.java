package com.charles.netty.client;

import com.charles.netty.handler.SimpleClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * 简化版 Netty 客户端。
 * 目标：连接服务端，发送字符串，接收回复。
 */
public class SimpleClient {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    // 绑定 NioSocketChannel（NIO 的客户端通道）
                    .channel(NioSocketChannel.class)
                    // 配置管道初始化逻辑
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    // 添加解码器：把字节 → 字符串
                                    .addLast(new StringDecoder())
                                    // 添加编码器：把字符串 → 字节
                                    .addLast(new StringEncoder())
                                    // 添加我们的业务处理器
                                    .addLast(new SimpleClientHandler());
                        }
                    });

            // 连接服务端（127.0.0.1:8080），同步等待连接成功
            ChannelFuture future = bootstrap.connect("127.0.0.1", 8080).sync();
            
            System.out.println("=== 客户端连接成功 ===");
            
            // 发送消息给服务端（注意要加换行符，因为 StringEncoder 需要）
            future.channel().writeAndFlush("你好，服务端！\r\n");
            
            // 等待 1 秒再发一条
            Thread.sleep(1000);
            future.channel().writeAndFlush("这是第二条消息\r\n");

            // 等待客户端通道关闭
            future.channel().closeFuture().sync();
        } finally {
            // 优雅关闭线程组
            group.shutdownGracefully();
        }
    }
}
