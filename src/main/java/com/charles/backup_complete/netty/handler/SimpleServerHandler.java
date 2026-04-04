package com.charles.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 最简单的服务端处理器：收到什么消息，就回复什么。
 * 这是为了让你理解 Netty 通信的最基本流程。
 */
public class SimpleServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 当客户端发送数据时，这个方法会被调用
        String message = (String) msg;
        System.out.println("【服务端】收到消息：" + message);
        
        // 原样回复给客户端（echo）
        ctx.writeAndFlush("服务端已收到：" + message + "\r\n");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 处理异常，关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}
