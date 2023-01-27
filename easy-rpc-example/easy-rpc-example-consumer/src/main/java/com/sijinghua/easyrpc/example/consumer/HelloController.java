package com.sijinghua.easyrpc.example.consumer;

import com.sijinghua.easyrpc.annotation.ServiceReference;
import com.sijinghua.easyrpc.example.provider.api.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    // Inject remote service by custom annotation
    @ServiceReference
    private HelloService helloService;

    @GetMapping("/hello/{name}")
    public String hello(@PathVariable String name) {
        // Here we call the interface of remote service just like local
        final String rsp = helloService.sayHello(name);
        logger.info("Receive message from rpc server, message: {}", rsp);
        return rsp;
    }
}
