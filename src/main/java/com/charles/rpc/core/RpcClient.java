package com.charles.rpc.core;

/**
 * 客户端抽象：把一次“调用”封装成 invoke(request) -> response 的过程。
 * 后面接 Netty 后，这个接口实现会变，但上层调用不变。
 */
public interface RpcClient {
    RpcResponse invoke(RpcRequest request);
}