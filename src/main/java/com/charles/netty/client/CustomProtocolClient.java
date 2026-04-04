package com.charles.netty.client;

import com.charles.netty.codec.RpcProtocolFullCodec;
import com.charles.netty.handler.CustomProtocolClientHandler;
import com.charles.netty.protocol.RpcProtocol;
import com.charles.netty.protocol.RpcProtocolHeader;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 使用自定义协议的 Netty 客户端启动类。
 * 发送带协议头的完整数据包。
 */
public class CustomProtocolClient {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    // 添加自定义协议编解码器
                                    .addLast(new RpcProtocolFullCodec())
                                    // 添加业务处理器
                                    .addLast(new CustomProtocolClientHandler());
                        }
                    });

            ChannelFuture future = bootstrap.connect("127.0.0.1", 8080).sync();
            System.out.println("=== 自定义协议客户端连接成功 ===");

            // 创建并发送第一个请求
            RpcProtocol request1 = createRequest("你好，这是第一条消息！");
            future.channel().writeAndFlush(request1);

            // 等待 1 秒发送第二个请求
            Thread.sleep(1000);
            RpcProtocol request2 = createRequest("这是第二条消息，测试粘包处理！");
            future.channel().writeAndFlush(request2);

            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    /**
     * 创建请求协议包
     */
    private static RpcProtocol createRequest(String message) {
        byte[] data = message.getBytes();
        RpcProtocolHeader header = RpcProtocolHeader.createRequestHeader(
                data.length,
                (byte) 0x01  // 使用 Kryo 序列化
        );
        return new RpcProtocol(header, data);
    }
}