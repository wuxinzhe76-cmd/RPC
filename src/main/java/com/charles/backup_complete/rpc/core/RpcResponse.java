package com.charles.rpc.core;

import java.io.Serializable;

/**
 * RPC 响应体：一定要包含 requestId，才能让客户端把响应归还给对应调用。
 * 成功：success=true + result
 * 失败：success=false + error
 */
public class RpcResponse implements Serializable {

    private String requestId;
    private boolean success;
    private Object result;
    private RpcError error;

    public RpcResponse() {}

    public static RpcResponse success(String requestId, Object result) {
        RpcResponse resp = new RpcResponse();
        resp.requestId = requestId;
        resp.success = true;
        resp.result = result;
        return resp;
    }

    public static RpcResponse fail(String requestId, RpcError error) {
        RpcResponse resp = new RpcResponse();
        resp.requestId = requestId;
        resp.success = false;
        resp.error = error;
        return resp;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public RpcError getError() {
        return error;
    }

    public void setError(RpcError error) {
        this.error = error;
    }
}