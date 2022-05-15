package com.kang.netty.rpc.protocol.bo;

import io.netty.util.concurrent.Promise;
import lombok.Data;

/**
 * @author weikang.di
 * @date 2022/5/11 23:57
 */
@Data
public class RpcFuture<T> {

    private Promise<T> promise;

    public RpcFuture(Promise<T> promise) {
        this.promise = promise;
    }
}
