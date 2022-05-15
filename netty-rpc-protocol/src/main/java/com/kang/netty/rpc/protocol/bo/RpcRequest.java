package com.kang.netty.rpc.protocol.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author weikang.di
 * @date 2022/5/11 23:07
 */
@Data
public class RpcRequest implements Serializable {

    /**
     * 类名
     */
    private String className;

    /**
     * 请求目标方法
     */
    private String methodName;

    /**
     * 请求参数
     */
    private Object[] params;

    /**
     * 参数类型
     */
    private Class<?>[] parameterTypes;
}
