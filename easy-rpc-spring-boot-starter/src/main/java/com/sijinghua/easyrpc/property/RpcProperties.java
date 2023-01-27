package com.sijinghua.easyrpc.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sijinghua.easy.rpc")
@Data
public class RpcProperties {
    private Integer exposePort = 6666;

    private String register;

    private String registerAddress;

    private String protocol = "java";
}
