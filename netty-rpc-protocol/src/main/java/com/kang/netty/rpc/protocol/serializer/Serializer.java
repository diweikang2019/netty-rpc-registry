package com.kang.netty.rpc.protocol.serializer;

/**
 * @author weikang.di
 * @date 2022/5/11 23:27
 */
public interface Serializer {

    /**
     * 序列化
     *
     * @param obj
     * @param <T>
     * @return
     */
    <T> byte[] serialize(T obj);

    /**
     * 反序列化
     *
     * @param data
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T deserialize(byte[] data, Class<T> clazz);

    /**
     * 序列化的类型
     *
     * @return
     */
    byte getType();
}
