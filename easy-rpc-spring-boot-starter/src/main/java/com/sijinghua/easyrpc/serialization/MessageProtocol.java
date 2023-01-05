package com.sijinghua.easyrpc.serialization;

public interface MessageProtocol {
    // server >>>>>>>>>>

    /**
     * Unmarshall request message
     *
     * @param data byte array of request message from client
     * @return rpc request object from client
     * @throws Exception exception
     */
    RpcRequest unmarshallingReqMessage(byte[] data) throws Exception;

    /**
     * Marshall response message
     *
     * @param response object at server to response
     * @return byte array of response message after marshalling
     * @throws Exception exception
     */
    byte[] marshallingRespMessage(RpcResponse response) throws Exception;

    // client >>>>>>>>>

    /**
     * Unmarshall response message
     *
     * @param data byte array of response message from server
     * @return rpc response object from server
     * @throws Exception exception
     */
    RpcResponse unmarshallingRespMessage(byte[] data) throws Exception;

    /**
     * Marshall request message
     *
     * @param request request object from client
     * @return byte array of request message after marshalling
     * @throws Exception exception
     */
    byte[] marshallingReqMessage(RpcRequest request) throws Exception;
}
