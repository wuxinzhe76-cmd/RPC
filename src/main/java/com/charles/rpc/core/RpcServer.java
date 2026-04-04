package com.charles.rpc.core;

/**
 * 服务端抽象：注册服务对象、启动监听端口、处理请求等。
 * 后面接注册中心(Zookeeper)也不会改这个抽象接口的核心意义。
 */
public interface RpcServer {
    void registerService(String interfaceName, Object service);
    void start();
    void stop();
}