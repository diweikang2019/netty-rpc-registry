package com.kang.netty.rpc.protocol.codec;

import com.kang.netty.rpc.protocol.bo.Header;
import com.kang.netty.rpc.protocol.bo.RpcProtocol;
import com.kang.netty.rpc.protocol.bo.RpcRequest;
import com.kang.netty.rpc.protocol.bo.RpcResponse;
import com.kang.netty.rpc.protocol.constants.ReqType;
import com.kang.netty.rpc.protocol.constants.RpcConstant;
import com.kang.netty.rpc.protocol.serializer.Serializer;
import com.kang.netty.rpc.protocol.serializer.SerializerManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author weikang.di
 * @date 2022/5/11 23:25
 */
@Slf4j
public class RpcDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        log.info("========begin RpcDecoder==========");

        if (in.readableBytes() < RpcConstant.HEAD_TOTAL_LEN) {
            return;
        }

        in.markReaderIndex(); // 标记读取开始索引
        short magic = in.readShort(); // 读取2个字节的magic
        if (magic != RpcConstant.MAGIC) {
            throw new IllegalArgumentException("Illegal request parameter 'magic':" + magic);
        }

        byte serialType = in.readByte(); // 读取一个字节的序列化方式
        byte reqType = in.readByte(); // 读取一个字节的消息类型
        long requestId = in.readLong(); // 读取请求id
        int dataLength = in.readInt(); // 读取数据报文长度

        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }

        //读取消息体的内容
        byte[] content = new byte[dataLength];
        in.readBytes(content);

        Header header = new Header(magic, serialType, reqType, requestId, dataLength);
        Serializer serializer = SerializerManager.getSerializer(serialType);
        ReqType rt = ReqType.findByCode(reqType);
        switch (rt) {
            case REQUEST:
                RpcRequest request = serializer.deserialize(content, RpcRequest.class);
                RpcProtocol<RpcRequest> reqProtocol = new RpcProtocol<>();
                reqProtocol.setHeader(header);
                reqProtocol.setBody(request);
                out.add(reqProtocol);
                break;
            case RESPONSE:
                RpcResponse response = serializer.deserialize(content, RpcResponse.class);
                RpcProtocol<RpcResponse> resProtocol = new RpcProtocol<>();
                resProtocol.setHeader(header);
                resProtocol.setBody(response);
                out.add(resProtocol);
                break;
            case HEARTBEAT:
                // TODO
                break;
            default:
                break;
        }
    }
}
