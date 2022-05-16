package com.kang.netty.rpc.protocol.protocol;

import com.kang.netty.rpc.protocol.bo.RpcProtocol;
import com.kang.netty.rpc.protocol.bo.RpcRequest;
import com.kang.netty.rpc.protocol.handler.RpcClientInitializer;
import com.kang.netty.rpc.registry.RegistryService;
import com.kang.netty.rpc.registry.ServiceInfo;
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

    public NettyClient() {
        log.info("Begin Init Netty Client");
        eventLoopGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();

        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new RpcClientInitializer());
    }

    public void sendRequest(RpcProtocol<RpcRequest> protocol, RegistryService registryService) throws Exception {
        ServiceInfo serviceInfo = registryService.discovery(protocol.getBody().getClassName());
        final ChannelFuture future = bootstrap.connect(serviceInfo.getServiceAddress(), serviceInfo.getServicePort()).sync();
        future.addListener(listener -> {
            if (future.isSuccess()) {
                log.info("Connect rpc server {} success.", serviceInfo.getServiceAddress());
            } else {
                log.info("Connect rpc server {} failed.", serviceInfo.getServiceAddress());
                future.cause().printStackTrace();
                eventLoopGroup.shutdownGracefully();
            }
        });
        log.info("Begin transfer data");
        future.channel().writeAndFlush(protocol);
    }
}
