package com.kang.netty.rpc.protocol.serializer;

import com.alibaba.fastjson.JSON;
import com.kang.netty.rpc.protocol.constants.SerialType;

/**
 * @author weikang.di
 * @date 2022/5/11 23:31
 */
public class JsonSerializer implements Serializer {

    @Override
    public <T> byte[] serialize(T obj) {
        return JSON.toJSONString(obj).getBytes();
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        return JSON.parseObject(new String(data), clazz);
    }

    @Override
    public byte getType() {
        return SerialType.JSON_SERIAL.code();
    }

}
