package com.kang.netty.rpc.protocol.constants;

/**
 * @author weikang.di
 * @date 2022/5/11 23:12
 */
public enum SerialType {

    JSON_SERIAL((byte) 1),
    JAVA_SERIAL((byte) 2);

    private byte code;

    SerialType(byte code) {
        this.code = code;
    }

    public byte code() {
        return this.code;
    }
}
