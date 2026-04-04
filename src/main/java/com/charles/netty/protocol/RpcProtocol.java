package com.charles.netty.protocol;

/**
 * RPC 完整协议包：协议头 + 数据体（序列化后的字节数组）。
 */
public class RpcProtocol {

    private RpcProtocolHeader header;
    private byte[] data;

    public RpcProtocol() {}

    public RpcProtocol(RpcProtocolHeader header, byte[] data) {
        this.header = header;
        this.data = data;
    }

    public RpcProtocolHeader getHeader() {
        return header;
    }

    public void setHeader(RpcProtocolHeader header) {
        this.header = header;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RpcProtocol{" +
                "header=" + header +
                ", data.length=" + (data != null ? data.length : 0) + '}';
    }
}