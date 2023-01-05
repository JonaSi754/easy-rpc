package com.sijinghua.easyrpc.server.registry;

import com.sijinghua.easyrpc.common.ServiceInterfaceInfo;

import java.util.HashMap;
import java.util.Map;

public class DefaultServiceRegistry implements ServiceRegistry {
    private final Map<String, ServiceInterfaceInfo> localMap = new HashMap<>();
    protected String protocol;
    protected Integer port;

    @Override
    public void register(ServiceInterfaceInfo serviceInterfaceInfo) throws Exception {
        if (serviceInterfaceInfo == null) {
            throw new IllegalArgumentException("param.invalid");
        }

        String serviceName = serviceInterfaceInfo.getServiceName();
        localMap.put(serviceName, serviceInterfaceInfo);
    }

    @Override
    public ServiceInterfaceInfo getRegisteredObj(String serviceName) throws Exception {
        return localMap.get(serviceName);
    }
}
