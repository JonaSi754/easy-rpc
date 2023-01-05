package com.sijinghua.easyrpc.client.discovery;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.sijinghua.easyrpc.common.ServiceInterfaceInfo;
import com.sijinghua.easyrpc.server.registry.NacosServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NacosServiceDiscovery implements ServiceDiscovery {
    private static final Logger logger = LoggerFactory.getLogger(NacosServiceRegistry.class);

    private NamingService namingService;

    public NacosServiceDiscovery(String serverList) {
        // create Registry Center Object through Factory class,
        // param is the ip of Nacos Server
        try {
            namingService = NamingFactory.createNamingService(serverList);
        } catch (NacosException e) {
            logger.error("Nacos client init error", e);
        }
        // print the status of Nacos Server
        logger.info("Nacos server status: {}", namingService.getServerStatus());
    }

    @Override
    public ServiceInterfaceInfo selectOneInstance(String serviceName) {
        Instance instance;
        try {
            instance = namingService.selectOneHealthyInstance(serviceName);
        } catch (NacosException e) {
            logger.error("Nacos exception", e);
            return null;
        }

        // pack the instance object and return
        ServiceInterfaceInfo serviceInterfaceInfo = new ServiceInterfaceInfo();
        serviceInterfaceInfo.setServiceName(instance.getServiceName());
        serviceInterfaceInfo.setIp(instance.getIp());
        serviceInterfaceInfo.setPort(instance.getPort());
        return serviceInterfaceInfo;
    }
}
