package com.charles.rpc.serialization;

import com.charles.rpc.core.RpcError;
import com.charles.rpc.core.RpcRequest;
import com.charles.rpc.core.RpcResponse;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.util.Pool;

/**
 * 基于 Kryo 的序列化实现。Kryo 非线程安全，使用 Pool 从池中借还实例。
 */
public class KryoRpcSerializer implements RpcSerializer {

    private static final int DEFAULT_BUFFER_SIZE = 4096;

    private final Pool<Kryo> kryoPool = new Pool<Kryo>(true, false, 16) {
        @Override
        protected Kryo create() {
            Kryo kryo = new Kryo();
            kryo.setRegistrationRequired(false);
            kryo.register(RpcRequest.class);
            kryo.register(RpcResponse.class);
            kryo.register(RpcError.class);
            kryo.register(Object[].class);
            kryo.register(String[].class);
            return kryo;
        }
    };

    @Override
    public byte[] serialize(Object obj) {
        Kryo kryo = kryoPool.obtain();
        try {
            Output output = new Output(DEFAULT_BUFFER_SIZE, -1);
            kryo.writeClassAndObject(output, obj);
            return output.toBytes();
        } finally {
            kryoPool.free(kryo);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T deserialize(byte[] bytes, Class<T> type) {
        Kryo kryo = kryoPool.obtain();
        try {
            Input input = new Input(bytes);
            Object obj = kryo.readClassAndObject(input);
            return (T) obj;
        } finally {
            kryoPool.free(kryo);
        }
    }
}
