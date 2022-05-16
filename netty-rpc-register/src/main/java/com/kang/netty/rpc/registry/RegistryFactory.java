package com.kang.netty.rpc.registry;

import com.kang.netty.rpc.registry.zookeeper.ZookeeperRegistryService;

/**
 * @author weikang.di
 * @date 2022/5/16 3:24 PM
 */
public class RegistryFactory {

    public static RegistryService createRegistryService(String address, RegistryType registryType) {
        RegistryService registryService = null;
        try {
            switch (registryType) {
                case ZOOKEEPER:
                    registryService = new ZookeeperRegistryService(address);
                    break;
                case EUREKA:
                    // TODO
                    break;
                default:
                    registryService = new ZookeeperRegistryService(address);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return registryService;
    }
}
