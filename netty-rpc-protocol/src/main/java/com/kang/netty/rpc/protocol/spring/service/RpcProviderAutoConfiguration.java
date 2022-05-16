package com.kang.netty.rpc.protocol.spring.service;

import com.kang.netty.rpc.registry.RegistryFactory;
import com.kang.netty.rpc.registry.RegistryService;
import com.kang.netty.rpc.registry.RegistryType;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author weikang.di
 * @date 2022/5/13 23:15
 */
@Configuration
@EnableConfigurationProperties(RpcServerProperties.class)
public class RpcProviderAutoConfiguration {

    @Bean
    public SpringRpcProviderBean springRpcProviderBean(RpcServerProperties rpcServerProperties) {
        RegistryService registryService = RegistryFactory.createRegistryService(rpcServerProperties.getRegistryAddress(), RegistryType.findByCode(rpcServerProperties.getRegistryType()));
        return new SpringRpcProviderBean(rpcServerProperties.getServiceAddress(), rpcServerProperties.getServicePort(), registryService);
    }
}
