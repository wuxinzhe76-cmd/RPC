package com.charles.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 最简单的客户端处理器：打印收到的回复。
 */
public class SimpleClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 当服务端回复数据时，这个方法会被调用
        String message = (String) msg;
        System.out.println("【客户端】收到回复：" + message);
        
        // 如果是第一条消息的回复，可以关闭连接
        if (message.contains("你好")) {
            System.out.println("测试完成，准备关闭连接...");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
