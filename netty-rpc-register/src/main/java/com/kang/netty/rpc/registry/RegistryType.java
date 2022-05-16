package com.kang.netty.rpc.registry;

/**
 * @author weikang.di
 * @date 2022/5/16 3:23 PM
 */
public enum RegistryType {

    ZOOKEEPER((byte) 0),
    EUREKA((byte) 1);
    private byte code;

    RegistryType(byte code) {
        this.code = code;
    }

    public static RegistryType findByCode(int code) {
        for (RegistryType registryType : RegistryType.values()) {
            if (registryType.code == code) {
                return registryType;
            }
        }
        return null;
    }
}
