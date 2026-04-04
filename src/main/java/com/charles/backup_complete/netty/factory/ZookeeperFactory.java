package com.charles.netty.factory;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class ZookeeperFactory {
    private static CuratorFramework client;
    public static CuratorFramework create() {
        if(client==null){
            RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
            client = CuratorFrameworkFactory.newClient("localhost:2181", retryPolicy);
            client.start();
        }
        return client;
    }

//    public static void main(String[] args) {
//        CuratorFramework client = create();
////        静态方法属于类本身
////        因为 add 是 static 的，它属于 MathUtils 类，而不是某个实例。
////        在类的内部，你可以省略类名，直接通过方法名调用静态方法，编译器会自动解析为 MathUtils.add(...)。
//
//        try {
//            client.create().forPath("/netty");
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
}
