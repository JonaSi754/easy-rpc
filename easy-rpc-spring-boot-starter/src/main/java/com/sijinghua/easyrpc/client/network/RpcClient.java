package com.sijinghua.easyrpc.client.network;

import com.sijinghua.easyrpc.common.ServiceInterfaceInfo;

public interface RpcClient {
    /**
     * send message
     *
     * @param data message to be sent
     * @param serviceInterfaceInfo message receiver
     * @return message which has been sent
     */
    byte[] sendMessage(byte[] data, ServiceInterfaceInfo serviceInterfaceInfo) throws InterruptedException;
}
