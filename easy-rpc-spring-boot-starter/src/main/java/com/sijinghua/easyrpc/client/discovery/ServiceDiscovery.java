package com.sijinghua.easyrpc.client.discovery;

import com.sijinghua.easyrpc.common.ServiceInterfaceInfo;

public interface ServiceDiscovery {
    /**
     * randomly select a healthy instance
     * @param serviceName name of service
     * @return instance
     */
    ServiceInterfaceInfo selectOneInstance(String serviceName);

}
