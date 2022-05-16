package com.kang.netty.rpc.protocol.spring.reference;

import lombok.Data;

/**
 * @author weikang.di
 * @date 2022/5/14 0:06
 */
@Data
// @ConfigurationProperties(prefix = "netty.rpc")
public class RpcClientProperties {

    /**
     * 注册中心的地址
     */
    private String registryAddress;

    /**
     * 注册中心的类型
     */
    private byte registryType;
}
