package com.kang.netty.rpc.protocol.codec;

import com.kang.netty.rpc.protocol.bo.Header;
import com.kang.netty.rpc.protocol.bo.RpcProtocol;
import com.kang.netty.rpc.protocol.serializer.Serializer;
import com.kang.netty.rpc.protocol.serializer.SerializerManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author weikang.di
 * @date 2022/5/11 23:12
 */
@Slf4j
public class RpcEncoder extends MessageToByteEncoder<RpcProtocol<Object>> {

    @Override
    protected void encode(ChannelHandlerContext ctx, RpcProtocol<Object> msg, ByteBuf out) throws Exception {
        log.info("============begin RpcEncoder=========");

        Header header = msg.getHeader();
        out.writeShort(header.getMagic());
        out.writeByte(header.getSerialType());
        out.writeByte(header.getReqType());
        out.writeLong(header.getRequestId());
        Serializer serializer = SerializerManager.getSerializer(header.getSerialType());
        byte[] data = serializer.serialize(msg.getBody());
        out.writeInt(data.length);
        out.writeBytes(data);
    }
}
