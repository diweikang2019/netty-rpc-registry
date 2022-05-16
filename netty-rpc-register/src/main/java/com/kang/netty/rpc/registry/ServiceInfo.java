package com.kang.netty.rpc.registry;

import lombok.Data;

/**
 * @author weikang.di
 * @date 2022/5/16 3:16 PM
 */
@Data
public class ServiceInfo {

    private String serviceName;

    private String serviceAddress;

    private int servicePort;
}
