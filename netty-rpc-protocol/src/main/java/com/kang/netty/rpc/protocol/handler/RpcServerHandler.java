package com.kang.netty.rpc.protocol.handler;

import com.kang.netty.rpc.protocol.bo.Header;
import com.kang.netty.rpc.protocol.bo.RpcProtocol;
import com.kang.netty.rpc.protocol.bo.RpcRequest;
import com.kang.netty.rpc.protocol.bo.RpcResponse;
import com.kang.netty.rpc.protocol.constants.ReqType;
import com.kang.netty.rpc.protocol.spring.service.Mediator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author weikang.di
 * @date 2022/5/11 23:49
 */
@Slf4j
public class RpcServerHandler extends SimpleChannelInboundHandler<RpcProtocol<RpcRequest>> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcProtocol<RpcRequest> msg) throws Exception {
        log.info("Invoke rpc client request");

        Object result = Mediator.getInstance().processor(msg.getBody());

        RpcResponse response = new RpcResponse();
        response.setCode(0);
        response.setMessage("success");
        response.setData(result);

        Header header = msg.getHeader();
        header.setReqType(ReqType.RESPONSE.code());

        RpcProtocol<RpcResponse> resProtocol = new RpcProtocol();
        resProtocol.setHeader(header);
        resProtocol.setBody(response);

        ctx.writeAndFlush(resProtocol);
    }

}
