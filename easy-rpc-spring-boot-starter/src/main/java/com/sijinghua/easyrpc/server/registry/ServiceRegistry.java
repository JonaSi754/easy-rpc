package com.sijinghua.easyrpc.server.registry;

import com.sijinghua.easyrpc.common.ServiceInterfaceInfo;

public interface ServiceRegistry {
    /**
     * Register server info
     *
     * @param serviceInterfaceInfo service (interface) info to be registered
     * @throws Exception throws basic exception
     */
    void register(ServiceInterfaceInfo serviceInterfaceInfo) throws Exception;

    /**
     * Get registered object according to service name
     *
     * @param serviceName name of service
     * @return object been registered
     * @throws Exception throws basic exception
     */
    ServiceInterfaceInfo getRegisteredObj(String serviceName) throws Exception;

}
