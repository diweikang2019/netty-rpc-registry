package com.kang.netty.rpc.protocol.spring.service;

import com.kang.netty.rpc.protocol.annotation.RpcService;
import com.kang.netty.rpc.protocol.protocol.NettyServer;
import com.kang.netty.rpc.registry.RegistryService;
import com.kang.netty.rpc.registry.ServiceInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Method;

/**
 * @author weikang.di
 * @date 2022/5/13 23:15
 */
@Slf4j
public class SpringRpcProviderBean implements InitializingBean, BeanPostProcessor {

    private String serverAddress;
    private int serverPort;

    /**
     * 服务注册中心
     */
    private RegistryService registryService;

    public SpringRpcProviderBean(String serverAddress, int serverPort, RegistryService registryService) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.registryService = registryService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("begin deploy Netty Server to host {},on port {}", this.serverAddress, this.serverPort);
        new Thread(() -> {
            new NettyServer(this.serverAddress, this.serverPort).startNettyServer();
        }).start();
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 只要bean声明了GpRemoteService注解，则需要把该服务发布到网络上
        if (bean.getClass().isAnnotationPresent(RpcService.class)) {
            Method[] methods = bean.getClass().getDeclaredMethods();
            for (Method method : methods) {
                String serviceName = bean.getClass().getInterfaces()[0].getName();
                String key = serviceName + "." + method.getName();
                BeanMethod beanMethod = new BeanMethod();
                beanMethod.setBean(bean);
                beanMethod.setMethod(method);
                Mediator.beanMethodMap.put(key, beanMethod);

                ServiceInfo serviceInfo = new ServiceInfo();
                serviceInfo.setServiceAddress(this.serverAddress);
                serviceInfo.setServicePort(this.serverPort);
                serviceInfo.setServiceName(serviceName);
                try {
                    // 注册服务
                    registryService.register(serviceInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("register service {} failed", serviceName, e);
                }
            }
        }
        return bean;
    }
}
