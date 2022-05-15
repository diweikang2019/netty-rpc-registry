package com.kang.netty.rpc.api;

/**
 * @author weikang.di
 * @date 2022/5/11 22:58
 */
public interface UserService {

    /**
     * 保存用户
     *
     * @param name
     * @return
     */
    String saveUser(String name);
}
