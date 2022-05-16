package com.kang.netty.rpc.registry.zookeeper;

import com.kang.netty.rpc.registry.RegistryService;
import com.kang.netty.rpc.registry.ServiceInfo;
import com.kang.netty.rpc.registry.loadbalance.LoadBalance;
import com.kang.netty.rpc.registry.loadbalance.RandomLoadBalance;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;

import java.util.Collection;
import java.util.List;

/**
 * @author weikang.di
 * @date 2022/5/16 3:26 PM
 */
@Slf4j
public class ZookeeperRegistryService implements RegistryService {

    private static final String REGISTRY_PATH = "/registry";

    /**
     * curator中提供的服务注册与发现的封装
     */
    private final ServiceDiscovery<ServiceInfo> serviceDiscovery;

    private LoadBalance<ServiceInstance<ServiceInfo>> loadBalance;

    public ZookeeperRegistryService(String registryAddress) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory
                .newClient(registryAddress, new ExponentialBackoffRetry(1000, 3));
        client.start();
        JsonInstanceSerializer<ServiceInfo> serializer = new JsonInstanceSerializer<>(ServiceInfo.class);
        this.serviceDiscovery = ServiceDiscoveryBuilder.builder(ServiceInfo.class)
                .client(client)
                .serializer(serializer)
                .basePath(REGISTRY_PATH)
                .build();
        this.serviceDiscovery.start();
        this.loadBalance = new RandomLoadBalance();
    }

    @Override
    public void register(ServiceInfo serviceInfo) throws Exception {
        log.info("begin registry serviceInfo into Zookeeper server");
        ServiceInstance<ServiceInfo> serviceInstance = ServiceInstance.<ServiceInfo>builder()
                .name(serviceInfo.getServiceName())
                .address(serviceInfo.getServiceAddress())
                .port(serviceInfo.getServicePort())
                .payload(serviceInfo)
                .build();
        this.serviceDiscovery.registerService(serviceInstance);
    }

    @Override
    public ServiceInfo discovery(String serviceName) throws Exception {
        log.info("begin discovery serviceInfo from Zookeeper server");
        Collection<ServiceInstance<ServiceInfo>> serviceInstances =
                this.serviceDiscovery.queryForInstances(serviceName);
        //动态路由
        ServiceInstance<ServiceInfo> serviceInstance = this.loadBalance.select((List<ServiceInstance<ServiceInfo>>) serviceInstances);
        if (serviceInstance != null) {
            return serviceInstance.getPayload();
        }
        return null;
    }
}
