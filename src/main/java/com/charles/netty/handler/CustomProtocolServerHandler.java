package com.charles.netty.handler;

import com.charles.netty.protocol.RpcProtocol;
import com.charles.netty.protocol.RpcProtocolConstants;
import com.charles.netty.protocol.RpcProtocolHeader;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 基于自定义协议的服务端处理器。
 * 处理 RpcProtocol 消息，提取数据体并处理业务逻辑。
 */
public class CustomProtocolServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 接收的是完整的 RpcProtocol 对象
        if (!(msg instanceof RpcProtocol)) {
            System.err.println("收到未知协议类型：" + msg.getClass().getName());
            return;
        }

        RpcProtocol protocol = (RpcProtocol) msg;
        RpcProtocolHeader header = protocol.getHeader();
        byte[] data = protocol.getData();

        // 打印协议头信息（调试用）
        System.out.println("【服务端】收到协议包：" + header);
        System.out.println("【服务端】数据体长度：" + (data != null ? data.length : 0));

        // 校验消息类型
        if (header.getMessageType() != RpcProtocolConstants.MSG_TYPE_REQUEST) {
            System.err.println("服务端只处理请求，收到消息类型：" + header.getMessageType());
            return;
        }

        // TODO：下一阶段在这里反序列化 data，得到 RpcRequest，然后反射调用方法
        // 现在先简单回复
        ctx.writeAndFlush(createResponse(protocol, "服务端已收到请求"));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 创建响应协议包
     */
    private RpcProtocol createResponse(RpcProtocol request, String result) {
        RpcProtocolHeader responseHeader = RpcProtocolHeader.createResponseHeader(
                result.getBytes().length,
                request.getHeader().getSerializerType()
        );
        return new RpcProtocol(responseHeader, result.getBytes());
    }
}