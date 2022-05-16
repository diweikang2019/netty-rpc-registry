package com.kang.netty.rpc.protocol.spring.service;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author weikang.di
 * @date 2022/5/13 23:15
 */
@Data
@ConfigurationProperties(prefix = "netty.rpc")
public class RpcServerProperties {

    private String serviceAddress;

    private Integer servicePort;

    /**
     * 注册中心的地址
     */
    private String registryAddress;

    /**
     * 注册中心的类型
     */
    private byte registryType;
}
