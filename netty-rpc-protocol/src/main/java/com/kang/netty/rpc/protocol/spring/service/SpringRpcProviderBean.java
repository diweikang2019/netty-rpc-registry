package com.kang.netty.rpc.protocol.spring.service;

import com.kang.netty.rpc.protocol.annotation.RpcService;
import com.kang.netty.rpc.protocol.protocol.NettyServer;
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

    public SpringRpcProviderBean(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
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
            }
        }
        return bean;
    }
}
