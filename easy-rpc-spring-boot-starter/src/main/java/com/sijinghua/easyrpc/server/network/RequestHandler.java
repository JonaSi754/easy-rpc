package com.sijinghua.easyrpc.server.network;

import com.sijinghua.easyrpc.common.ServiceInterfaceInfo;
import com.sijinghua.easyrpc.serialization.MessageProtocol;
import com.sijinghua.easyrpc.serialization.RpcRequest;
import com.sijinghua.easyrpc.serialization.RpcResponse;
import com.sijinghua.easyrpc.server.registry.ServiceRegistry;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RequestHandler {
    private final MessageProtocol protocol;

    private final ServiceRegistry serviceRegistry;

    public RequestHandler(MessageProtocol protocol, ServiceRegistry serviceRegistry) {
        this.protocol = protocol;
        this.serviceRegistry = serviceRegistry;
    }

    public byte[] handleRequest(byte[] data) throws Exception {
        // Unmarshalling request message
        RpcRequest rpcRequest = protocol.unmarshallingReqMessage(data);
        String serviceName = rpcRequest.getServiceName();
        ServiceInterfaceInfo serviceInterfaceInfo = serviceRegistry.getRegisteredObj(serviceName);

        RpcResponse rpcResponse = new RpcResponse();
        if (serviceInterfaceInfo == null) {
            rpcResponse.setStatus("Not Found");
            return protocol.marshallingRespMessage(rpcResponse);
        }

        try {
            // Call the target method through reflecting
            final Method method = serviceInterfaceInfo.getClazz().getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
            final Object retValue = method.invoke(serviceInterfaceInfo.getObj(), rpcRequest.getParameters());
            rpcResponse.setStatus("Success");
            rpcResponse.setRetValue(retValue);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            rpcResponse.setStatus("Fail");
            rpcResponse.setException(e);
        }
        return protocol.marshallingRespMessage(rpcResponse);
    }
}
