package com.sijinghua.easyrpc.server.registry;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.sijinghua.easyrpc.common.ServiceInterfaceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NacosServiceRegistry extends DefaultServiceRegistry {
    private static final Logger logger = LoggerFactory.getLogger(NacosServiceRegistry.class);
    private NamingService naming;

    public NacosServiceRegistry(String serverList) {
        // create Registry Center with Factory class,
        // parameter is the IP of Nacos Server
        try {
            naming = NamingFactory.createNamingService(serverList);
        } catch (NacosException e) {
            logger.error("Nacos init error", e);
        }
        // print status of Nacos Server
        logger.info("Nacos server status: {}", naming.getServerStatus());
    }

    @Override
    public void register(ServiceInterfaceInfo serviceInterfaceInfo) throws Exception {
        super.register(serviceInterfaceInfo);
        // register current service instance
        naming.registerInstance(serviceInterfaceInfo.getServiceName(), buildInstance(serviceInterfaceInfo));
    }

    private Instance buildInstance(ServiceInterfaceInfo serviceInterfaceInfo) {
        // register instance in Nacos center
        Instance instance = new Instance();
        instance.setIp(serviceInterfaceInfo.getIp());
        instance.setPort(serviceInterfaceInfo.getPort());
        // TODO add more metadata
        return instance;
    }
}
