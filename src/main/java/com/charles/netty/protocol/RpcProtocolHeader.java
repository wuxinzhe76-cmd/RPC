package com.charles.netty.protocol;

/**
 * RPC 协议头：固定 11 字节，描述后续数据体的元信息。
 */
public class RpcProtocolHeader {

    private int magicNumber;      // 魔数 4 字节
    private byte version;         // 版本号 1 字节
    private byte serializerType;  // 序列化类型 1 字节
    private byte messageType;     // 消息类型 1 字节
    private int dataLength;       // 数据体长度 4 字节

    public RpcProtocolHeader() {}

    public static RpcProtocolHeader createRequestHeader(int dataLength, byte serializerType) {
        RpcProtocolHeader header = new RpcProtocolHeader();
        header.magicNumber = RpcProtocolConstants.MAGIC_NUMBER;
        header.version = RpcProtocolConstants.VERSION;
        header.serializerType = serializerType;
        header.messageType = RpcProtocolConstants.MSG_TYPE_REQUEST;
        header.dataLength = dataLength;
        return header;
    }

    public static RpcProtocolHeader createResponseHeader(int dataLength, byte serializerType) {
        RpcProtocolHeader header = new RpcProtocolHeader();
        header.magicNumber = RpcProtocolConstants.MAGIC_NUMBER;
        header.version = RpcProtocolConstants.VERSION;
        header.serializerType = serializerType;
        header.messageType = RpcProtocolConstants.MSG_TYPE_RESPONSE;
        header.dataLength = dataLength;
        return header;
    }

    // Getters and Setters
    public int getMagicNumber() { return magicNumber; }
    public void setMagicNumber(int magicNumber) { this.magicNumber = magicNumber; }
    public byte getVersion() { return version; }
    public void setVersion(byte version) { this.version = version; }
    public byte getSerializerType() { return serializerType; }
    public void setSerializerType(byte serializerType) { this.serializerType = serializerType; }
    public byte getMessageType() { return messageType; }
    public void setMessageType(byte messageType) { this.messageType = messageType; }
    public int getDataLength() { return dataLength; }
    public void setDataLength(int dataLength) { this.dataLength = dataLength; }

    @Override
    public String toString() {
        return "RpcProtocolHeader{" +
                "magicNumber=" + magicNumber +
                ", version=" + version +
                ", serializerType=" + serializerType +
                ", messageType=" + messageType +
                ", dataLength=" + dataLength + '}';
    }
}