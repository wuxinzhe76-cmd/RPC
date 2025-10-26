package com.charles.netty.constant;

import io.netty.util.AttributeKey;

public class Constants {
    public static final int PORT = 8080;
    public static final String HOST = "127.0.0.1";
    public static final String ZK_CONNECT = "192.168.1.100:2181";
    public static final String  ZK_PATH = "/netty/";
    public static final AttributeKey<String> CLIENT_KEY = AttributeKey.newInstance("sssss");
}

