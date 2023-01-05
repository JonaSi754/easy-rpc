package com.sijinghua.easyrpc.common;

import lombok.Data;

import java.util.UUID;

/**
 * Information exposed to the public by the service provider
 *
 * NOTE: Each externally exposed interface in the service is considered as a service capability,
 * which is a simple abstraction of the interface capability
 */

@Data
public class ServiceInterfaceInfo {
    // Service Name
    private String serviceName;

    // Instance id, varies from instances
    private String instanceId;

    // Service instance ip, varies from instances
    private String ip;

    // Service port, keep the same
    private Integer port;

    // The class object corresponding to the bean object
    // This is used for reflection calls
    private Class<?> clazz;

    // The bean object to implement this interface
    // This is also used for reflection calls
    private Object obj;

    public ServiceInterfaceInfo(){}

    public ServiceInterfaceInfo(String serviceName, String ip, Integer port, Class<?> clazz, Object obj) {
        this.serviceName = serviceName;
        this.ip = ip;
        this.port = port;
        this.clazz = clazz;
        this.obj = obj;
        this.instanceId = UUID.randomUUID().toString();
    }
}
