package com.kang.netty.rpc.registry.loadbalance;

import com.kang.netty.rpc.registry.ServiceInfo;
import org.apache.curator.x.discovery.ServiceInstance;

import java.util.List;

/**
 * @author weikang.di
 * @date 2022/5/16 3:32 PM
 */
public abstract class AbstractLoadBalance implements LoadBalance<ServiceInstance<ServiceInfo>> {

    @Override
    public ServiceInstance<ServiceInfo> select(List<ServiceInstance<ServiceInfo>> servers) {
        if (servers == null || servers.size() == 0) {
            return null;
        }
        if (servers.size() == 1) {
            return servers.get(0);
        }
        return doSelect(servers);
    }


    protected abstract ServiceInstance<ServiceInfo> doSelect(List<ServiceInstance<ServiceInfo>> servers);
}
