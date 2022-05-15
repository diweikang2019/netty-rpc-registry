package com.kang.netty.rpc.provider.service;

import com.kang.netty.rpc.api.UserService;
import com.kang.netty.rpc.protocol.annotation.RpcService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author weikang.di
 * @date 2022/5/12 0:10
 */
@Slf4j
@RpcService
public class UserServiceImpl implements UserService {

    @Override
    public String saveUser(String name) {
        log.info("begin save user:{}", name);
        return "save user success: " + name;
    }
}
