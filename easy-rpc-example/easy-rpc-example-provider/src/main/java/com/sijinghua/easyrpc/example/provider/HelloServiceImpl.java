package com.sijinghua.easyrpc.example.provider;

import com.sijinghua.easyrpc.annotation.ServiceExpose;
import com.sijinghua.easyrpc.example.provider.api.HelloService;

@ServiceExpose
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return "A greeting from sijinghua: Hello, " + name + "! Here is sijinghua's rpc!";
    }
}
