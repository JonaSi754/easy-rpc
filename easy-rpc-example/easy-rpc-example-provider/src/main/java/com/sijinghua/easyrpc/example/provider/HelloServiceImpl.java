package com.sijinghua.easyrpc.example.provider;

import com.sijinghua.easyrpc.annotation.ServiceExpose;
import com.sijinghua.easyrpc.example.provider.api.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ServiceExpose
public class HelloServiceImpl implements HelloService {
    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);

    @Override
    public String sayHello(String name) {
        return "A greeting from sijinghua: Hello, " + name + "! Here is sijinghua's rpc!";
    }
}
