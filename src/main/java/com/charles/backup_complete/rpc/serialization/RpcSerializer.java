package com.charles.rpc.serialization;

/**
 * RPC 序列化抽象：将对象与字节数组互转，供网络层使用。
 */
public interface RpcSerializer {

    byte[] serialize(Object obj);

    <T> T deserialize(byte[] bytes, Class<T> type);
}
