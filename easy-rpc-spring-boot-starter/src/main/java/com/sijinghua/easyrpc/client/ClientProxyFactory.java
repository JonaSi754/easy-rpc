package com.sijinghua.easyrpc.client;

import com.sijinghua.easyrpc.client.discovery.ServiceDiscovery;
import com.sijinghua.easyrpc.client.network.RpcClient;
import com.sijinghua.easyrpc.common.ServiceInterfaceInfo;
import com.sijinghua.easyrpc.exception.RpcException;
import com.sijinghua.easyrpc.serialization.MessageProtocol;
import com.sijinghua.easyrpc.serialization.RpcRequest;
import com.sijinghua.easyrpc.serialization.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Interface variable marked by @ServiceReference will inject a proxy object automatically
 * When call a function, the invoke function of proxy object will be automatically called
 */
public class ClientProxyFactory {
    private static final Logger logger = LoggerFactory.getLogger(ClientProxyFactory.class);

    private ServiceDiscovery serviceDiscovery;

    private MessageProtocol messageProtocol;

    private RpcClient rpcClient;

    public ClientProxyFactory(ServiceDiscovery serviceDiscovery, MessageProtocol messageProtocol, RpcClient rpcClient) {
        this.serviceDiscovery = serviceDiscovery;
        this.messageProtocol = messageProtocol;
        this.rpcClient = rpcClient;
    }

    /**
     * Get proxy object and bind invoke function
     *
     * @param clazz the class object of interface
     * @param <T> type
     * @return proxy object
     */
    @SuppressWarnings("unchecked")
    public <T> T getProxyInstance(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // Step 1: Choose a exposed service of provider through service discovery
                String serviceName = clazz.getName();
                ServiceInterfaceInfo serviceInterfaceInfo = serviceDiscovery.selectOneInstance(serviceName);
                logger.info("Rpc server instance list: {}", serviceInterfaceInfo);
                if (serviceInterfaceInfo == null) {
                    throw new RpcException("No Rpc servers found.");
                }

                // Step 2: Construct a rpc request object
                final RpcRequest rpcRequest = new RpcRequest();
                rpcRequest.setServiceName(serviceName);
                rpcRequest.setMethodName(method.getName());
                rpcRequest.setParameterTypes(method.getParameterTypes());
                rpcRequest.setParameters(args);

                // Step 3: Marshall request message
                // ps: Multi marshalling methods can be set here
                byte[] data = messageProtocol.marshallingReqMessage(rpcRequest);

                // Step 4: Call rpc client to send message
                byte[] byteResponse = rpcClient.sendMessage(data, serviceInterfaceInfo);

                // Step 5: Unmarshall response message
                final RpcResponse rpcResponse = messageProtocol.unmarshallingRespMessage(byteResponse);

                // Step 6: Resolve the response and process it
                if (rpcResponse.getException() != null) {
                    throw rpcResponse.getException();
                }
                return rpcResponse.getRetValue();
            }
        });
    }
}
