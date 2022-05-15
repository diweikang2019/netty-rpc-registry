package com.kang.netty.rpc.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {
        "com.kang.netty.rpc.protocol.spring.reference",
        "com.kang.netty.rpc.consumer.controller",
        "com.kang.netty.rpc.protocol.annotation"})
@SpringBootApplication
public class NettyRpcConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyRpcConsumerApplication.class, args);
    }

}
