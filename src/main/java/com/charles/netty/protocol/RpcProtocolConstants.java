package com.charles.netty.protocol;

/**
 * RPC 自定义协议的常量定义。
 */
public class RpcProtocolConstants {

    // 魔数：用于标识这是 RPC 协议的数据包，防止误收其他协议的数据
    public static final int MAGIC_NUMBER = 0x12345678;

    // 协议版本号
    public static final byte VERSION = 0x01;

    // 序列化类型标识
    public static final byte SERIALIZER_KRYO = 0x01;  // Kryo 序列化
    public static final byte SERIALIZER_JSON = 0x02;  // JSON 序列化

    // 消息类型标识
    public static final byte MSG_TYPE_REQUEST = 0x01;  // RPC 请求
    public static final byte MSG_TYPE_RESPONSE = 0x02; // RPC 响应

    // 消息头固定长度：魔数 (4) + 版本 (1) + 序列化类型 (1) + 消息类型 (1) + 数据长度 (4) = 11 字节
    public static final int HEADER_LENGTH = 11;
}