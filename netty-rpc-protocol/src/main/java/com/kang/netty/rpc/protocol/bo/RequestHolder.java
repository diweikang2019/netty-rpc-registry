package com.kang.netty.rpc.protocol.bo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author weikang.di
 * @date 2022/5/11 23:56
 */
public class RequestHolder {

    public static final AtomicLong REQUEST_ID = new AtomicLong();

    public static final Map<Long, RpcFuture> REQUEST_MAP = new ConcurrentHashMap<>();
}
