package com.kang.netty.rpc.protocol.protocol;

import com.kang.netty.rpc.protocol.bo.RpcProtocol;
import com.kang.netty.rpc.protocol.bo.RpcRequest;
import com.kang.netty.rpc.protocol.handler.RpcClientInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author weikang.di
 * @date 2022/5/12 0:04
 */
@Slf4j
public class NettyClient {

    private final Bootstrap bootstrap;
    private final EventLoopGroup eventLoopGroup;
    private String serviceAddress;
    private int servicePort;

    public NettyClient(String serviceAddress, int servicePort) {
        log.info("Begin Init Netty Client, {}, {}", serviceAddress, servicePort);
        eventLoopGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();

        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new RpcClientInitializer());

        this.serviceAddress = serviceAddress;
        this.servicePort = servicePort;
    }

    public void sendRequest(RpcProtocol<RpcRequest> protocol) throws InterruptedException {
        final ChannelFuture future = bootstrap.connect(this.serviceAddress, this.servicePort).sync();
        future.addListener(listener -> {
            if (future.isSuccess()) {
                log.info("Connect rpc server {} success.", this.serviceAddress);
            } else {
                log.info("Connect rpc server {} failed.", this.serviceAddress);
                future.cause().printStackTrace();
                eventLoopGroup.shutdownGracefully();
            }
        });
        log.info("Begin transfer data");
        future.channel().writeAndFlush(protocol);
    }
}
