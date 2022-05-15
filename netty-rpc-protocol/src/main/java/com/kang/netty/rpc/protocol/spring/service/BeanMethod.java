package com.kang.netty.rpc.protocol.spring.service;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * @author weikang.di
 * @date 2022/5/13 23:15
 */
@Data
public class BeanMethod {

    private Object bean;

    private Method method;
}
