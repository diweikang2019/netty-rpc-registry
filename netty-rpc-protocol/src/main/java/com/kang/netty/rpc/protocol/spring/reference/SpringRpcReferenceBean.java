package com.kang.netty.rpc.protocol.spring.reference;

import com.kang.netty.rpc.registry.RegistryFactory;
import com.kang.netty.rpc.registry.RegistryService;
import com.kang.netty.rpc.registry.RegistryType;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

/**
 * @author weikang.di
 * @date 2022/5/13 23:54
 */
public class SpringRpcReferenceBean implements FactoryBean<Object> {

    private Object object;
    private String registryAddress;
    private byte registryType;
    private Class<?> interfaceClass;

    public void init() {
        RegistryService registryService = RegistryFactory.createRegistryService(this.registryAddress, RegistryType.findByCode(this.registryType));
        this.object = Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass}, new RpcInvokerProxy(registryService));
    }

    @Override
    public Object getObject() throws Exception {
        return this.object;
    }

    @Override
    public Class<?> getObjectType() {
        return this.interfaceClass;
    }

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public void setRegistryAddress(String registryAddress) {
        this.registryAddress = registryAddress;
    }

    public void setRegistryType(byte registryType) {
        this.registryType = registryType;
    }
}
