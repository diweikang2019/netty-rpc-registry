package com.kang.netty.rpc.protocol.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author weikang.di
 * @date 2022/5/11 23:09
 */
@Data
public class RpcResponse implements Serializable {

    private int code;

    private String message;

    private Object data;
}
