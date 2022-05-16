package com.kang.netty.rpc.registry;

/**
 * @author weikang.di
 * @date 2022/5/16 3:18 PM
 */
public interface RegistryService {

    /**
     * 服务注册
     *
     * @param serviceInfo
     * @throws Exception
     */
    void register(ServiceInfo serviceInfo) throws Exception;

    /**
     * 服务发现
     *
     * @param serviceName
     * @return
     * @throws Exception
     */
    ServiceInfo discovery(String serviceName) throws Exception;
}
