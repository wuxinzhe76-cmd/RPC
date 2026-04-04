package com.charles.netty.handler;

import com.charles.netty.protocol.RpcProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 基于自定义协议的客户端处理器。
 * 处理服务端返回的 RpcProtocol 消息。
 */
public class CustomProtocolClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 接收的是完整的 RpcProtocol 对象
        if (!(msg instanceof RpcProtocol)) {
            System.err.println("收到未知协议类型：" + msg.getClass().getName());
            return;
        }

        RpcProtocol protocol = (RpcProtocol) msg;
        System.out.println("【客户端】收到响应：" + protocol.getHeader());
        System.out.println("【客户端】响应数据：" + new String(protocol.getData()));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}