package com.kang.netty.rpc.protocol.spring.reference;

import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

/**
 * @author weikang.di
 * @date 2022/5/13 23:54
 */
public class SpringRpcReferenceBean implements FactoryBean<Object> {

    private Object object;
    private String serviceAddress;
    private int servicePort;
    private Class<?> interfaceClass;

    public void init() {
        this.object = Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass}, new RpcInvokerProxy(serviceAddress, servicePort));
    }

    @Override
    public Object getObject() throws Exception {
        return this.object;
    }

    @Override
    public Class<?> getObjectType() {
        return this.interfaceClass;
    }

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    public void setServicePort(int servicePort) {
        this.servicePort = servicePort;
    }

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }
}
