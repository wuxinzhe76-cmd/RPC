package com.charles.netty.init;

import com.charles.netty.Constant.Constants;
import com.charles.netty.factory.ZookeeperFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
@Component
public class NettyInitial implements ApplicationListener<ContextRefreshedEvent> {
    public void start() {
        NioEventLoopGroup boosGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup(16);
        try {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boosGroup, workGroup)
                .option(ChannelOption.SO_BACKLOG, 128)
                //writing Keepalive Packet by myself.
                //question: why not use the default Keepalive Packet?
                .childOption(ChannelOption.SO_KEEPALIVE, false)
                .channel(NioServerSocketChannel.class)//bind NioServerSocketChannel.
                .childHandler(new NettyServerInitializer());
            ChannelFuture future = bootstrap.bind(8080).sync();
            CuratorFramework client = ZookeeperFactory.create();
            InetAddress inetAddress = InetAddress.getLocalHost();
            try {
                // 先尝试删除已存在的节点
                client.delete().forPath(Constants.ZK_PATH + inetAddress.getHostAddress());
            } catch (Exception deleteException) {
                // 如果节点不存在，忽略异常
            }
            client.create().withMode(CreateMode.EPHEMERAL).forPath(Constants.ZK_PATH+inetAddress.getHostAddress());
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
            workGroup.shutdownGracefully();
            boosGroup.shutdownGracefully();
        }

    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.start();
    }
}
