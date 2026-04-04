package com.charles.rpc.core;

import java.io.Serializable;

/**
 * RPC 错误信息：让客户端能“看懂失败原因”，并做统一处理。
 */
public class RpcError implements Serializable {
    private String code;            // 业务/系统错误码（可按需扩展）
    private String message;        // 错误描述
    private String exceptionClass; // 异常类名
    private String stackTrace;     // 堆栈（调试用，生产可选择裁剪/关闭）

    public RpcError() {}

    public RpcError(String code, String message, String exceptionClass, String stackTrace) {
        this.code = code;
        this.message = message;
        this.exceptionClass = exceptionClass;
        this.stackTrace = stackTrace;
    }

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    public String getExceptionClass() { return exceptionClass; }

    public void setExceptionClass(String exceptionClass) { this.exceptionClass = exceptionClass; }

    public String getStackTrace() { return stackTrace; }

    public void setStackTrace(String stackTrace) { this.stackTrace = stackTrace; }
}