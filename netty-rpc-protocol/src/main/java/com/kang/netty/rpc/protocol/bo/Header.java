package com.kang.netty.rpc.protocol.bo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author weikang.di
 * @date 2022/5/11 23:02
 */
@Data
@AllArgsConstructor
public class Header implements Serializable {

    /**
     * 魔数  2个字节
     */
    private short magic;

    /**
     * 序列化方式  1个字节
     */
    private byte serialType;

    /**
     * 消息类型  1个字节
     */
    private byte reqType;

    /**
     * 请求id  8个字节
     */
    private long requestId;

    /**
     * 消息体长度，4个字节
     */
    private int length;
}
