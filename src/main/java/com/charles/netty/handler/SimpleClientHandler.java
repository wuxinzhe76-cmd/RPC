package com.charles.netty.handler;

import com.alibaba.fastjson.JSONObject;
import com.charles.netty.client.DefaultFuture;
import com.charles.netty.util.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import static com.charles.netty.Constant.Constants.CLIENT_KEY;

public class SimpleClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if("ping".equals(msg.toString())){
            ctx.channel().writeAndFlush("pong\r\n");
            return;
        }
        ctx.channel().attr(CLIENT_KEY).set((String) msg);
        Response response = JSONObject.parseObject((String) msg, Response.class);
        DefaultFuture.receive(response);
    }
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    }
}
