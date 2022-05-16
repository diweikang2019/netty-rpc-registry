package com.kang.netty.rpc.registry.loadbalance;

import com.kang.netty.rpc.registry.ServiceInfo;
import org.apache.curator.x.discovery.ServiceInstance;

import java.util.List;
import java.util.Random;

/**
 * @author weikang.di
 * @date 2022/5/16 3:33 PM
 */
public class RandomLoadBalance extends AbstractLoadBalance {

    @Override
    protected ServiceInstance<ServiceInfo> doSelect(List<ServiceInstance<ServiceInfo>> servers) {
        Random random = new Random();
        return servers.get(random.nextInt(servers.size()));
    }
}
