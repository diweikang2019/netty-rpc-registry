package com.kang.netty.rpc.protocol.handler;

import com.kang.netty.rpc.protocol.codec.RpcDecoder;
import com.kang.netty.rpc.protocol.codec.RpcEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @author weikang.di
 * @date 2022/5/11 23:54
 */
public class RpcServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().
                addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,
                        12,
                        4,
                        0,
                        0))
                .addLast(new RpcDecoder())
                .addLast(new RpcEncoder())
                .addLast(new RpcServerHandler());
    }

}
