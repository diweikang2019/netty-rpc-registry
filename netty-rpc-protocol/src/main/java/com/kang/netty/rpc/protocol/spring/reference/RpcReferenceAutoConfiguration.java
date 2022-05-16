package com.kang.netty.rpc.protocol.spring.reference;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author weikang.di
 * @date 2022/5/14 0:23
 */
@Configuration
public class RpcReferenceAutoConfiguration implements EnvironmentAware {

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public SpringRpcReferencePostProcessor postProcessor() {
        RpcClientProperties rc = new RpcClientProperties();
        rc.setRegistryAddress(this.environment.getProperty("netty.rpc.registryAddress"));
        rc.setRegistryType(Byte.parseByte(this.environment.getProperty("netty.rpc.registryType")));
        return new SpringRpcReferencePostProcessor(rc);
    }
}
