package com.kang.netty.rpc.consumer.controller;

import com.kang.netty.rpc.api.UserService;
import com.kang.netty.rpc.protocol.annotation.RpcReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author weikang.di
 * @date 2022/5/13 23:49
 */
@RestController
public class HelloController {

    @RpcReference
    private UserService userService;

    @GetMapping("/saveUser")
    public String saveUser() {
        return userService.saveUser("diweikang");
    }
}
