package com.kang.netty.rpc.protocol.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author weikang.di
 * @date 2022/5/11 23:05
 */
@Data
public class RpcProtocol<T> implements Serializable {

    /**
     * 消息头
     */
    private Header header;

    /**
     * 消息体
     */
    private T body;
}
