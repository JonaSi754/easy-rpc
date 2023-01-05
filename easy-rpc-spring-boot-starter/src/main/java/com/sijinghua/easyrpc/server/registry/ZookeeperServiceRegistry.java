package com.sijinghua.easyrpc.server.registry;

import com.alibaba.fastjson.JSON;
import com.sijinghua.easyrpc.common.ServiceInterfaceInfo;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ZookeeperServiceRegistry extends DefaultServiceRegistry {
    private static final Logger logger = LoggerFactory.getLogger(ZookeeperServiceRegistry.class);
    private ZkClient zkClient;

    public ZookeeperServiceRegistry(String zkAddress) {
        init(zkAddress);
    }

    private void init(String zkAddress) {
        // Initialization, establish connection with server
        zkClient = new ZkClient(zkAddress);
        // Set serialize and deserialize machine
        zkClient.setZkSerializer(new ZkSerializer() {
            @Override
            public byte[] serialize(Object o) throws ZkMarshallingError {
                return String.valueOf(o).getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public Object deserialize(byte[] bytes) throws ZkMarshallingError {
                return new String(bytes, StandardCharsets.UTF_8);
            }
        });
    }

    public void register(ServiceInterfaceInfo serviceInterfaceInfo) throws Exception {
        logger.info("Registering Service: {}", serviceInterfaceInfo);

        super.register(serviceInterfaceInfo);

        // create persistent ZK node (Service Node)
        String serviceName = serviceInterfaceInfo.getServiceName();
        String servicePath = "/com/sijinghua/easyrpc/service/" + serviceName;
        if (!zkClient.exists(servicePath)) {
            zkClient.createPersistent(servicePath, true);
            logger.info("Created node: {}", servicePath);
        }

        // create ephemeral ZK node (Instance Node)
        String uri = JSON.toJSONString(serviceInterfaceInfo);
        uri = URLEncoder.encode(uri, "UTF-8");
        String uriPath = servicePath + "/" + uri;
        if (zkClient.exists(uriPath)) {
            zkClient.delete(uriPath);
        }
        zkClient.createEphemeral(uriPath);
        logger.info("Created ephemeral node: {}", uriPath);
    }
}
