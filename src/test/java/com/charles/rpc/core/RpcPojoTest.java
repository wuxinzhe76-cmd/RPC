package com.charles.rpc.core;

import org.junit.Assert;
import org.junit.Test;

/**
 * 阶段 1：验证 RpcRequest / RpcResponse POJO 行为。
 */
public class RpcPojoTest {

    @Test
    public void testRequestAndResponsePojo() {
        String[] typeNames = new String[]{"java.lang.Integer"};
        Object[] args = new Object[]{123};

        RpcRequest request = RpcRequest.newRequest(
                "com.demo.UserService",
                "save",
                typeNames,
                args
        );

        Assert.assertNotNull(request.getRequestId());
        Assert.assertEquals("com.demo.UserService", request.getInterfaceName());
        Assert.assertEquals("save", request.getMethodName());

        RpcResponse response = RpcResponse.success(request.getRequestId(), "OK");

        Assert.assertEquals(request.getRequestId(), response.getRequestId());
        Assert.assertTrue(response.isSuccess());
        Assert.assertEquals("OK", response.getResult());
    }
}
