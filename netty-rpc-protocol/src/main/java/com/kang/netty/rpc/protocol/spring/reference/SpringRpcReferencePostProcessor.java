package com.kang.netty.rpc.protocol.spring.reference;

import com.kang.netty.rpc.protocol.annotation.RpcReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author weikang.di
 * @date 2022/5/14 0:04
 */
@Slf4j
public class SpringRpcReferencePostProcessor implements ApplicationContextAware, BeanClassLoaderAware, BeanFactoryPostProcessor {

    private ApplicationContext context;
    private ClassLoader classLoader;
    private RpcClientProperties rpcClientProperties;

    /**
     * 保存发布的引用bean的信息
     */
    private final Map<String, BeanDefinition> rpcRefBeanDefinition = new ConcurrentHashMap<>();

    public SpringRpcReferencePostProcessor(RpcClientProperties rpcClientProperties) {
        this.rpcClientProperties = rpcClientProperties;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    /**
     * spring容器加载了bean的定义文件之后， 在bean实例化之前执行
     *
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        for (String beanDefinitionname : beanFactory.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionname);
            String beanClassName = beanDefinition.getBeanClassName();
            if (beanClassName != null) {
                Class<?> clazz = ClassUtils.resolveClassName(beanClassName, this.classLoader);
                ReflectionUtils.doWithFields(clazz, this::parseRpcReference);
            }
        }
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
        this.rpcRefBeanDefinition.forEach((beanName, beanDefinition) -> {
            if (context.containsBean(beanName)) {
                log.warn("SpringContext already register bean {}", beanName);
                return;
            }
            registry.registerBeanDefinition(beanName, beanDefinition);
            log.info("registered RpcReferenceBean {} success", beanName);
        });
    }

    private void parseRpcReference(Field field) {
        RpcReference rpcReference = AnnotationUtils.getAnnotation(field, RpcReference.class);
        if (rpcReference != null) {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.
                    genericBeanDefinition(SpringRpcReferenceBean.class);
            builder.setInitMethodName("init");
            builder.addPropertyValue("interfaceClass", field.getType());
            builder.addPropertyValue("serviceAddress", rpcClientProperties.getServiceAddress());
            builder.addPropertyValue("servicePort", rpcClientProperties.getServicePort());

            BeanDefinition beanDefinition = builder.getBeanDefinition();
            rpcRefBeanDefinition.put(field.getName(), beanDefinition);
        }
    }
}
