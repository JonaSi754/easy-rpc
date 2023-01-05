package com.sijinghua.easyrpc.listener;

import com.sijinghua.easyrpc.annotation.ServiceExpose;
import com.sijinghua.easyrpc.annotation.ServiceReference;
import com.sijinghua.easyrpc.client.ClientProxyFactory;
import com.sijinghua.easyrpc.common.ServiceInterfaceInfo;
import com.sijinghua.easyrpc.property.RpcProperties;
import com.sijinghua.easyrpc.server.network.RpcServer;
import com.sijinghua.easyrpc.server.registry.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * Definite a listener
 * to initialize server or client in RPC
 */
public class DefaultRpcListener implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(DefaultRpcListener.class);

    private final ServiceRegistry serviceRegistry;

    private final RpcServer rpcServer;

    private final ClientProxyFactory clientProxyFactory;

    private RpcProperties rpcProperties;

    public DefaultRpcListener(ServiceRegistry serviceRegistry, RpcServer rpcServer,
                              ClientProxyFactory clientProxyFactory, RpcProperties rpcProperties) {
        this.serviceRegistry = serviceRegistry;
        this.rpcServer = rpcServer;
        this.clientProxyFactory = clientProxyFactory;
        this.rpcProperties = rpcProperties;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        final ApplicationContext applicationContext = event.getApplicationContext();
        // execute if get root application context
        if (applicationContext.getParent() == null) {
            // Initialize rpc server
            initRpcServer(applicationContext);
            // Initialize rpc client
            initRpcClient(applicationContext);
        }
    }

    private void initRpcServer(ApplicationContext applicationContext) {
        // 1.1 Scan @ServiceExpose annotation at server, and register service interface info at register center
        final Map<String, Object> beans = applicationContext.getBeansWithAnnotation(ServiceExpose.class);

        if (beans.size() == 0) {
            // No annotations found
            return;
        }

        for (Object beanObj: beans.values()) {
            // Register interface of service instance
            registerInstanceInterfaceInfo(beanObj);
        }

        // 1.2 Start web server and begin listening to given ports
        rpcServer.start();
    }

    private void registerInstanceInterfaceInfo(Object beanObj) {
        final Class<?>[] interfaces = beanObj.getClass().getInterfaces();
        if (interfaces.length == 0) {
            // Annotation class doesn't implement an interface
            return;
        }

        // Just single-interface scenario is considered here
        Class<?> interfaceClazz = interfaces[0];
        String serviceName = interfaceClazz.getName();
        String ip = getLocalAddress();
        Integer port = rpcProperties.getExposePort();

        try {
            // Register service
            serviceRegistry.register(new ServiceInterfaceInfo(serviceName, ip, port, interfaceClazz, beanObj));
        } catch (Exception e) {
            logger.error("Fail to register service: {}", e.getMessage());
        }
    }

    private String getLocalAddress() {
        String ip = "127.0.0.1";
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
        }
        return ip;
    }

    private void initRpcClient(ApplicationContext applicationContext) {
        // Iterate all bean in container
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        for (String beanName: beanNames) {
            Class<?> clazz = applicationContext.getType(beanName);
            if (clazz == null) {
                continue;
            }

            // Iterate member properties of each bean
            // Bean relies on remote interface if the property is annotated by @ServiceReference
            Field[] fields = clazz.getDeclaredFields();
            for (Field field: fields) {
                final ServiceReference annotation = field.getAnnotation(ServiceReference.class);
                if (annotation == null) {
                    // If this member property is not marked with the annotation, continue to find
                    continue;
                }

                // A member property with the annotation is found!
                Object beanObject = applicationContext.getBean(beanName);
                Class<?> fieldClass = field.getType();
                try {
                    // Inject proxy object
                    field.setAccessible(true);
                    field.set(beanObject, clientProxyFactory.getProxyInstance(fieldClass));
                } catch (IllegalAccessException e) {
                    logger.error("Fail to inject service, bean.name: {}, error.msg: {}", beanName, e.getMessage());
                }
            }
        }
    }
}
