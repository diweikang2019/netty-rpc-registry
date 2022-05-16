package com.kang.netty.rpc.protocol.spring.reference;

import com.kang.netty.rpc.protocol.bo.Header;
import com.kang.netty.rpc.protocol.bo.RequestHolder;
import com.kang.netty.rpc.protocol.bo.RpcFuture;
import com.kang.netty.rpc.protocol.bo.RpcProtocol;
import com.kang.netty.rpc.protocol.bo.RpcRequest;
import com.kang.netty.rpc.protocol.bo.RpcResponse;
import com.kang.netty.rpc.protocol.constants.ReqType;
import com.kang.netty.rpc.protocol.constants.RpcConstant;
import com.kang.netty.rpc.protocol.constants.SerialType;
import com.kang.netty.rpc.protocol.protocol.NettyClient;
import com.kang.netty.rpc.registry.RegistryService;
import io.netty.channel.DefaultEventLoop;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author weikang.di
 * @date 2022/5/12 0:15
 */
@Slf4j
public class RpcInvokerProxy implements InvocationHandler {

    private RegistryService registryService;

    public RpcInvokerProxy(RegistryService registryService) {
        this.registryService = registryService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("begin invoke target server");

        long requestId = RequestHolder.REQUEST_ID.incrementAndGet();
        Header header = new Header(RpcConstant.MAGIC, SerialType.JSON_SERIAL.code(), ReqType.REQUEST.code(), requestId, 0);

        RpcRequest request = new RpcRequest();
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParams(args);

        RpcProtocol<RpcRequest> reqProtocol = new RpcProtocol<>();
        reqProtocol.setHeader(header);
        reqProtocol.setBody(request);

        NettyClient nettyClient = new NettyClient();
        RpcFuture<RpcResponse> future = new RpcFuture<>(new DefaultPromise<>(new DefaultEventLoop()));
        RequestHolder.REQUEST_MAP.put(requestId, future);
        nettyClient.sendRequest(reqProtocol, registryService);
        return future.getPromise().get().getData();
    }
}
