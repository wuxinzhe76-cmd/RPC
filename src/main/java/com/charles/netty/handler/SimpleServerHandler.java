package com.charles.netty.handler;

import com.alibaba.fastjson.JSONObject;
import com.charles.netty.util.Response;
import com.charles.netty.handler.param.ServerRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

public class SimpleServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ctx.channel().writeAndFlush("is ok\r\n");
        ServerRequest request = JSONObject.parseObject(msg.toString(), ServerRequest.class);
        Response response = new Response();
        response.setId(request.getId());
        response.setResult("is ok");
        ctx.channel().writeAndFlush(JSONObject.toJSONString(response));
        ctx.channel().writeAndFlush("\r\n");



    }
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            switch (event.state()){
                case READER_IDLE:
                    System.out.println("读超时");
                    break;
                case WRITER_IDLE:
                    System.out.println("写超时");
                    break;
                case ALL_IDLE:
                    System.out.println("读写超时");
                    ctx.channel().writeAndFlush("ping\r\n");
                    break;
            }
        }

    }

}
