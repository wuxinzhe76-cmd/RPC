package com.charles.rpc.core;

import java.io.Serializable;
import java.util.UUID;

/**
 * RPC 请求体：告诉服务端“调用哪个方法 + 参数是什么”。
 *
 * 注意：这里我们用 parameterTypeNames（类型名字符串）而不是 Class<?>，
 * 目的：后面序列化/反序列化跨进程时，Class 对象不友好。
 */
public class RpcRequest implements Serializable {

    private String requestId;            // 用于客户端-服务端一一对应响应
    private String interfaceName;       // 目标接口/服务名（下一阶段动态代理会用到）
    private String methodName;          // 目标方法名
    private String[] parameterTypeNames;// 参数类型名数组，例如 ["java.lang.Integer","java.lang.String"]
    private Object[] arguments;        // 参数值数组
    private long timestamp;             // 方便调试/审计（可选）

    public RpcRequest() {
        // 反序列化需要无参构造
    }

    public static RpcRequest newRequest(String interfaceName,
                                         String methodName,
                                         String[] parameterTypeNames,
                                         Object[] arguments) {
        RpcRequest req = new RpcRequest();
        req.requestId = UUID.randomUUID().toString();
        req.interfaceName = interfaceName;
        req.methodName = methodName;
        req.parameterTypeNames = parameterTypeNames;
        req.arguments = arguments;
        req.timestamp = System.currentTimeMillis();
        return req;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String[] getParameterTypeNames() {
        return parameterTypeNames;
    }

    public void setParameterTypeNames(String[] parameterTypeNames) {
        this.parameterTypeNames = parameterTypeNames;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}