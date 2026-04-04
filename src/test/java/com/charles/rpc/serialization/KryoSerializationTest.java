package com.charles.rpc.serialization;

import com.charles.rpc.core.RpcRequest;
import com.charles.rpc.core.RpcResponse;
import org.junit.Assert;
import org.junit.Test;

/**
 * 阶段 2：Kryo 往返序列化验证。
 */
public class KryoSerializationTest {

    private final KryoRpcSerializer serializer = new KryoRpcSerializer();

    @Test
    public void roundTripRpcRequest() {
        RpcRequest original = RpcRequest.newRequest(
                "com.demo.UserService",
                "save",
                new String[]{"java.lang.Integer"},
                new Object[]{42}
        );

        byte[] bytes = serializer.serialize(original);
        RpcRequest copy = serializer.deserialize(bytes, RpcRequest.class);

        Assert.assertEquals(original.getRequestId(), copy.getRequestId());
        Assert.assertEquals(original.getInterfaceName(), copy.getInterfaceName());
        Assert.assertEquals(original.getMethodName(), copy.getMethodName());
        Assert.assertArrayEquals(original.getParameterTypeNames(), copy.getParameterTypeNames());
        Assert.assertArrayEquals(original.getArguments(), copy.getArguments());
    }

    @Test
    public void roundTripRpcResponse() {
        RpcResponse original = RpcResponse.success("req-1", "OK");

        byte[] bytes = serializer.serialize(original);
        RpcResponse copy = serializer.deserialize(bytes, RpcResponse.class);

        Assert.assertEquals(original.getRequestId(), copy.getRequestId());
        Assert.assertEquals(original.isSuccess(), copy.isSuccess());
        Assert.assertEquals(original.getResult(), copy.getResult());
    }
}
