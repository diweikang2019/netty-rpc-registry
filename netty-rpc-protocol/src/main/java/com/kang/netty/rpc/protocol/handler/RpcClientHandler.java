package com.kang.netty.rpc.protocol.handler;

import com.kang.netty.rpc.protocol.bo.RequestHolder;
import com.kang.netty.rpc.protocol.bo.RpcFuture;
import com.kang.netty.rpc.protocol.bo.RpcProtocol;
import com.kang.netty.rpc.protocol.bo.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author weikang.di
 * @date 2022/5/11 23:55
 */
@Slf4j
public class RpcClientHandler extends SimpleChannelInboundHandler<RpcProtocol<RpcResponse>> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcProtocol<RpcResponse> msg) throws Exception {
        log.info("Receive rpc server result");
        long requestId = msg.getHeader().getRequestId();
        RpcFuture<RpcResponse> future = RequestHolder.REQUEST_MAP.remove(requestId);
        // 返回结果
        future.getPromise().setSuccess(msg.getBody());
    }
}
