package com.sijinghua.easyrpc.config;

import com.sijinghua.easyrpc.client.ClientProxyFactory;
import com.sijinghua.easyrpc.client.discovery.NacosServiceDiscovery;
import com.sijinghua.easyrpc.client.discovery.ServiceDiscovery;
import com.sijinghua.easyrpc.client.discovery.ZookeeperServiceDiscovery;
import com.sijinghua.easyrpc.client.network.NettyRpcClient;
import com.sijinghua.easyrpc.listener.DefaultRpcListener;
import com.sijinghua.easyrpc.property.RpcProperties;
import com.sijinghua.easyrpc.serialization.DefaultMessageProtocol;
import com.sijinghua.easyrpc.serialization.MessageProtocol;
import com.sijinghua.easyrpc.server.network.NettyRpcServer;
import com.sijinghua.easyrpc.server.network.RequestHandler;
import com.sijinghua.easyrpc.server.network.RpcServer;
import com.sijinghua.easyrpc.server.registry.DefaultServiceRegistry;
import com.sijinghua.easyrpc.server.registry.NacosServiceRegistry;
import com.sijinghua.easyrpc.server.registry.ServiceRegistry;
import com.sijinghua.easyrpc.server.registry.ZookeeperServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutoConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(AutoConfiguration.class);

    // listener
    @Bean
    public DefaultRpcListener defaultRpcListener(@Autowired ServiceRegistry serviceRegistry,
                                                 @Autowired RpcServer rpcServer,
                                                 @Autowired ClientProxyFactory clientProxyFactory,
                                                 @Autowired RpcProperties rpcProperties) {
        return new DefaultRpcListener(serviceRegistry, rpcServer, clientProxyFactory, rpcProperties);
    }

    // properties
    @Bean
    public RpcProperties rpcProperties() {
        return new RpcProperties();
    }

    // client
    @Bean
    public ServiceDiscovery serviceDiscovery(@Autowired RpcProperties rpcProperties) {
        final String register = rpcProperties.getRegister();
        if ("nacos".equalsIgnoreCase(register)) {
            logger.info("Nacos discovery active.");
            return new NacosServiceDiscovery(rpcProperties().getRegisterAddress());
        } else if ("zookeeper".equalsIgnoreCase(register)) {
            logger.info("Zookeeper discovery active.");
            return new ZookeeperServiceDiscovery(rpcProperties().getRegisterAddress());
        }
        return null;
    }

    @Bean
    public ClientProxyFactory clientProxyFactory(@Autowired ServiceDiscovery serviceDiscovery) {
        return new ClientProxyFactory(serviceDiscovery, new DefaultMessageProtocol(), new NettyRpcClient());
    }

    // server
    @Bean
    public ServiceRegistry serviceRegistry(@Autowired RpcProperties rpcProperties) {
        // Choose registry center automatically according to parameters config
        final String register = rpcProperties.getRegister();
        if ("nacos".equalsIgnoreCase(register)) {
            logger.info("Nacos register active.");
            return new NacosServiceRegistry(rpcProperties.getRegisterAddress());
        } else if ("zookeeper".equalsIgnoreCase(register)) {
            logger.info("Zookeeper register active.");
            return new ZookeeperServiceRegistry(rpcProperties.getRegisterAddress());
        } else {
            logger.info("Default register active");
            return new DefaultServiceRegistry();
        }
    }

    @Bean
    public RequestHandler requestHandler(@Autowired RpcProperties rpcProperties,
                                         @Autowired ServiceRegistry serviceRegistry) {
        final String protocol = rpcProperties.getProtocol();

        MessageProtocol messageProtocol = new DefaultMessageProtocol();

        // TODO
        // Only native serialization method is supported here (fastjson, Kryo, Protobuf and et al.)
        if ("java".equalsIgnoreCase(protocol)) {
            messageProtocol = new DefaultMessageProtocol();
        }

        return new RequestHandler(messageProtocol, serviceRegistry);
    }

    @Bean
    public RpcServer rpcServer(@Autowired RpcProperties rpcProperties,
                               @Autowired RequestHandler requestHandler) {
        return new NettyRpcServer(rpcProperties.getExposePort(), requestHandler);
    }
}
