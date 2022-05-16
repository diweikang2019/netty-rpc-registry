package com.kang.netty.rpc.registry.loadbalance;

import java.util.List;

/**
 * @author weikang.di
 * @date 2022/5/16 3:31 PM
 */
public interface LoadBalance<T> {

    T select(List<T> servers);
}
