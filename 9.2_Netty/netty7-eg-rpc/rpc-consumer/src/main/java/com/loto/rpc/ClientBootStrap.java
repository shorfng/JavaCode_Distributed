package com.loto.rpc;

import com.loto.rpc.api.IUserService;
import com.loto.rpc.pojo.User;
import com.loto.rpc.proxy.RpcClientProxy;

/**
 * 测试类
 */
public class ClientBootStrap {
    public static void main(String[] args) {
        IUserService userService = (IUserService) RpcClientProxy.createProxy(IUserService.class);
        User user = userService.getById(1);
        System.out.println(user);
    }
}
