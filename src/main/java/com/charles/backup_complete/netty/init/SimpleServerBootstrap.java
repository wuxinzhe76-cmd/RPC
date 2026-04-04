package com.charles.netty.init;

import com.charles.netty.handler.SimpleServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * 简化版 Netty 服务端启动类。
 * 目标：监听 8080 端口，能接收字符串并回复。
 */
public class SimpleServerBootstrap {

    public static void main(String[] args) throws InterruptedException {
        // bossGroup：负责接收客户端连接（只负责"牵手"）
        // workerGroup：负责处理 IO 读写（真正"干活"的）
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    // 绑定 NioServerSocketChannel（NIO 的服务端通道）
                    .channel(NioServerSocketChannel.class)
                    // 配置管道初始化逻辑
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    // 添加解码器：把字节 → 字符串
                                    .addLast(new StringDecoder())
                                    // 添加编码器：把字符串 → 字节
                                    .addLast(new StringEncoder())
                                    // 添加我们的业务处理器
                                    .addLast(new SimpleServerHandler());
                        }
                    })
                    // 设置全连接队列大小
                    .option(ChannelOption.SO_BACKLOG, 128)
                    // 保持长连接
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // 绑定端口 8080，同步等待绑定成功
            ChannelFuture future = bootstrap.bind(8080).sync();
            
            System.out.println("=== 服务端启动成功，监听 8080 端口 ===");

            // 等待服务端通道关闭（实际上会一直阻塞）
            future.channel().closeFuture().sync();
        } finally {
            // 优雅关闭线程组
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
