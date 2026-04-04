package com.charles.netty.codec;

import com.charles.netty.protocol.RpcProtocol;
import com.charles.netty.protocol.RpcProtocolConstants;
import com.charles.netty.protocol.RpcProtocolHeader;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.util.List;

/**
 * RPC 自定义协议的完整编解码器。
 * 编码：RpcProtocol → 字节流
 * 解码：字节流 → RpcProtocol
 */
public class RpcProtocolFullCodec extends ByteToMessageCodec<RpcProtocol> {

    @Override
    protected void encode(ChannelHandlerContext ctx, RpcProtocol protocol, ByteBuf out) throws Exception {
        // 写入协议头
        RpcProtocolHeader header = protocol.getHeader();
        out.writeInt(header.getMagicNumber());        // 4 字节：魔数
        out.writeByte(header.getVersion());           // 1 字节：版本号
        out.writeByte(header.getSerializerType());    // 1 字节：序列化类型
        out.writeByte(header.getMessageType());       // 1 字节：消息类型
        out.writeInt(header.getDataLength());         // 4 字节：数据体长度

        // 写入数据体
        if (protocol.getData() != null) {
            out.writeBytes(protocol.getData());
        }
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 可读字节数不足 11 字节时，等待下一次读取（解决粘包/半包）
        if (in.readableBytes() < RpcProtocolConstants.HEADER_LENGTH) {
            return;
        }

        // 标记当前读指针，方便校验失败时重置
        in.markReaderIndex();

        // 读取魔数并校验
        int magicNumber = in.readInt();
        if (magicNumber != RpcProtocolConstants.MAGIC_NUMBER) {
            throw new IllegalArgumentException("Invalid magic number: " + magicNumber);
        }

        // 读取版本号并校验
        byte version = in.readByte();
        if (version != RpcProtocolConstants.VERSION) {
            throw new IllegalArgumentException("Unsupported version: " + version);
        }

        // 读取序列化类型
        byte serializerType = in.readByte();
        
        // 读取消息类型
        byte messageType = in.readByte();

        // 读取数据长度
        int dataLength = in.readInt();

        // 如果可读字节数不足数据体长度，重置读指针，等待下一次读取（解决粘包/半包）
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }

        // 读取数据体
        byte[] data = new byte[dataLength];
        in.readBytes(data);

        // 创建协议头
        RpcProtocolHeader header = new RpcProtocolHeader();
        header.setMagicNumber(magicNumber);
        header.setVersion(version);
        header.setSerializerType(serializerType);
        header.setMessageType(messageType);
        header.setDataLength(dataLength);

        // 创建完整的协议包
        RpcProtocol protocol = new RpcProtocol(header, data);
        out.add(protocol);
    }
}