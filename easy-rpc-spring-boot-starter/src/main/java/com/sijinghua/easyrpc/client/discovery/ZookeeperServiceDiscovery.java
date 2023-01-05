package com.sijinghua.easyrpc.client.discovery;

import com.alibaba.fastjson.JSON;
import com.sijinghua.easyrpc.common.ServiceInterfaceInfo;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ZookeeperServiceDiscovery implements ServiceDiscovery {
    private static final Logger logger = LoggerFactory.getLogger(ZookeeperServiceDiscovery.class);

    private final ZkClient zkClient;

    public ZookeeperServiceDiscovery(String zkAddress) {
        zkClient = new ZkClient(zkAddress);
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

    @Override
    public ServiceInterfaceInfo selectOneInstance(String serviceName) {
        String servicePath = "/com/sijinghua/easyrpc/service/" + serviceName;
        final List<String> childrenNodes = zkClient.getChildren(servicePath);

        return Optional.ofNullable(childrenNodes)
                .orElse(new ArrayList<>())
                .stream()
                .map(node -> {
                    // deserialize the service info to object after URL decoding
                    String serviceInstanceJson = URLDecoder.decode(node, StandardCharsets.UTF_8);
                    return JSON.parseObject(serviceInstanceJson, ServiceInterfaceInfo.class);
                }).filter(Objects::nonNull).findAny().get();
    }
}
