package com.kang.netty.rpc.protocol.spring.reference;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author weikang.di
 * @date 2022/5/14 0:06
 */
@Data
//@ConfigurationProperties(prefix = "netty.rpc")
public class RpcClientProperties {

    private String serviceAddress;

    private Integer servicePort;
}
