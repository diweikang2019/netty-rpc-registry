package com.kang.netty.rpc.protocol.spring.service;

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
        return new SpringRpcProviderBean(rpcServerProperties.getServiceAddress(), rpcServerProperties.getServicePort());
    }
}
