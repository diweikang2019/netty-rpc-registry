package com.kang.netty.rpc.protocol.constants;

/**
 * @author weikang.di
 * @date 2022/5/11 23:12
 */
public enum ReqType {

    REQUEST((byte) 1),
    RESPONSE((byte) 2),
    HEARTBEAT((byte) 3);

    private byte code;

    ReqType(byte code) {
        this.code = code;
    }

    public byte code() {
        return this.code;
    }

    public static ReqType findByCode(int code) {
        for (ReqType reqType : ReqType.values()) {
            if (reqType.code == code) {
                return reqType;
            }
        }
        return null;
    }
}
